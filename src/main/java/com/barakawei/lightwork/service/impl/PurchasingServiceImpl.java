package com.barakawei.lightwork.service.impl;

import com.barakawei.lightwork.dao.*;
import com.barakawei.lightwork.domain.*;
import com.barakawei.lightwork.service.DataDictService;
import com.barakawei.lightwork.util.UserContextUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.barakawei.lightwork.service.PurchasingService;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.*;

/**
 * @author qianxiaowei
 * @date 2013-1-9
 */

@Service
public class PurchasingServiceImpl implements PurchasingService {

    @Autowired
    private PurchasingDao purchasingDao;

    @Autowired
    private ZipperDao zipperDao;

    @Autowired
    private DataDictService dataDictService;

    @Autowired
    private PurchasingDetailDao purchasingDetailDao;

    @Override
    public List<Purchasing> findAll() {
        Sort s = new Sort(Sort.Direction.DESC, "complete");
        return purchasingDao.findAll(s);
    }

    @Override
    public void createPurchasing(Purchasing purchasing) {
        if (CollectionUtils.isEmpty(purchasing.getPds())) {
            return;
        }
        purchasing.setPlanningUser(UserContextUtil.getCurrentUser());
        purchasing.setStartTime(new Date());
        purchasing.setOngoing(true);
        purchasingDao.save(purchasing);
        for (PurchasingDetail pd : purchasing.getPds()) {
            pd.setPurchasing(purchasing);
            purchasingDetailDao.save(pd);
        }
        //updateStatus(purchasing);
    }

    /* (non-Javadoc)
     * @see com.barakawei.lightwork.service.PurchasingService#findPurchasingById(java.lang.String)
     */
    @Override
    public Purchasing findPurchasingById(String id) {
        Purchasing purchasing = purchasingDao.findOne(id);
        purchasing.sort();
        purchasing.sortZipper();
        return purchasing;
    }

    /* (non-Javadoc)
     * @see com.barakawei.lightwork.service.PurchasingService#updatePurchasing(com.barakawei.lightwork.domain.Purchasing)
     */
    @Override
    public void updatePurchasing(Purchasing purchasing) {
        Purchasing p = purchasingDao.findOne(purchasing.getId());
        List<PurchasingDetail> pds = p.getPds();
        List<PurchasingDetail> newPds = purchasing.getPds();
        List removeList = new ArrayList();
        for (PurchasingDetail pd : pds) {
            if (!newPds.contains(pd)) {
                pd.setPurchasing(null);
                removeList.add(pd);
                purchasingDetailDao.delete(pd);
            }
        }
        pds.removeAll(removeList);
        for (PurchasingDetail pd : newPds) {
            if (!pds.contains(pd)) {

                pd.setPurchasing(p);
                purchasingDetailDao.save(pd);

                //purchasingDetailDao.save(pd);
            }else{
                PurchasingDetail oldPd = purchasingDetailDao.findOne(pd.getId());
                oldPd.getGoods().setName(pd.getGoods().getName());
                oldPd.getGoods().setType(pd.getGoods().getType());
                oldPd.getGoods().setSpecification(pd.getGoods().getSpecification());
                oldPd.getGoods().setComposition(pd.getGoods().getComposition());
                oldPd.getGoods().setWidth(pd.getGoods().getWidth());
                oldPd.getGoods().setOrderCount(pd.getGoods().getOrderCount());
                oldPd.getGoods().setColor(pd.getGoods().getColor());
                oldPd.getGoods().setLoss(pd.getGoods().getLoss());
                oldPd.getGoods().setUnit(pd.getGoods().getUnit());
                oldPd.getGoods().setActualLoss(pd.getGoods().getActualLoss());
                oldPd.getGoods().setDescription(pd.getGoods().getDescription());
                oldPd.getGoods().setConsume(pd.getGoods().getConsume());
                oldPd.getGoods().setActualConsume(pd.getGoods().getActualConsume());
                oldPd.setExpectedArrivalTime(pd.getExpectedArrivalTime());
                purchasingDetailDao.save(oldPd);
            }
        }
        p.setOrderNumber(purchasing.getOrderNumber());
        p.setOrderName(purchasing.getOrderName());
        p.setOrderCount(purchasing.getOrderCount());
        p.setSerialNumber(purchasing.getSerialNumber());
        p.setApplyTime(purchasing.getApplyTime());
        p.setPlanDate(purchasing.getPlanDate());
        p.setConfirmDate(purchasing.getConfirmDate());
        p.setConfirmTime(purchasing.getConfirmTime());
        p.setConfirmName(purchasing.getConfirmName());
        p.setTypeNumber(purchasing.getTypeNumber());
        p.setPlanningUserName(purchasing.getPlanningUserName());
        purchasingDao.save(p);
        //updateStatus(p);

    }

    private void updateStatus(Purchasing purchasing){
        Purchasing pStatus = this.findPurchasingById(purchasing.getId());
        boolean kecaijian = true;
        boolean keshengchan = true;
        boolean yiqi = false;
        boolean mianTime = true;
        boolean fuTime = true;
        boolean mian = false;
        boolean fu = false;
        Date mianDate = null;
        Date fuDate = null;
        if(pStatus.getPds().size()==0){
            kecaijian = false;
            keshengchan = false;
        }
        Date dd = pStatus.getDataDate();
        int cut = pStatus.getFinshCut();
        for(PurchasingDetail pd:pStatus.getPds()){
            if(!StringUtils.isBlank(pd.getGoods().getType())&&pd.getGoods().getType().contains("面里料")){
                mian = true;
            }
            if(!StringUtils.isBlank(pd.getGoods().getType())&&pd.getGoods().getType().contains("辅料")){
                fu = true;
            }
            float count;
            if(StringUtils.isBlank(pd.getActualPurchasingCount())){
                count = 0;
            }else{
                count = Float.valueOf(pd.getActualPurchasingCount());
            }
            if(!StringUtils.isBlank(pd.getGoods().getType())&&pd.getGoods().getType().contains("面里料") ){
                Date aet = pd.getActualEntryTime();
                if((aet == null && count > 0)|| StringUtils.isBlank(pd.getOrderCount())){
                    mianTime = false;
                    kecaijian = false;
                    //keshengchan = false;
                }else{
                    if(mianDate == null || (aet!=null&&aet.compareTo(mianDate) == 1)){
                        mianDate =aet;
                    }
                }

            }else if(!StringUtils.isBlank(pd.getGoods().getType())&&pd.getGoods().getType().contains("辅料")){
                Date aet = pd.getActualEntryTime();
                if((aet == null && count > 0)||StringUtils.isBlank(pd.getOrderCount())){
                    keshengchan = false;
                    fuTime = false;
                }else{
                    if(fuDate == null || (aet!=null&&aet.compareTo(fuDate) == 1)){
                        fuDate =aet;
                    }
                }
            }


        }
        if(!mian){
            kecaijian = false;
            //keshengchan = false;
            mianDate = null;
        }
        if(!fu){
            keshengchan = false;
            fuDate = null;
        }
        if(mianTime){
            if(mianDate == null){
                mianDate = pStatus.getStartTime();
            }
            pStatus.setMianDate(mianDate);
        }
        if(fuTime){
            if(fuDate==null){
                fuDate = pStatus.getStartTime();
            }
            pStatus.setFuDate(fuDate);
        }
        if(keshengchan && kecaijian ){
            yiqi = true;
        }
        if(dd!=null&&cut==1&&yiqi){
            pStatus.setComplete(4);//待生产;
        }
        else if(yiqi){//已齐
            pStatus.setComplete(3);
        }else if(kecaijian){//待裁剪
            pStatus.setComplete(2);
        }else if(pStatus.getContractDate() != null && new Date().compareTo(pStatus.getContractDate()) == 1 && !kecaijian){//逾期
            pStatus.setComplete(0);
        }else{
            //待料
            pStatus.setComplete(1);
        }

        if(pStatus.getPlanUnderlineDate() != null||pStatus.getLineDate() !=null){//生产中
            pStatus.setComplete(5);
        }

        if(pStatus.getPlanUnderlineDate() != null && new Date().compareTo(pStatus.getPlanUnderlineDate())==1){//空白
            pStatus.setComplete(6);
        }

        if(pStatus.getHou() ==1 ){//锁钉
            pStatus.setComplete(7);
        }else if(pStatus.getHou() == 2){//水洗
            pStatus.setComplete(8);
        }else if(pStatus.getHou()==3){//后道包装
            pStatus.setComplete(9);
        }

        if(pStatus.getFinshDate() != null){//待发货
            pStatus.setComplete(10);
        }
        if(!pStatus.getOngoing()){//已完成
            pStatus.setComplete(11);
        }
        purchasingDao.save(pStatus);
    }
    /* (non-Javadoc)
     * @see com.barakawei.lightwork.service.PurchasingService#deletePurchasingById(java.lang.String)
     */
    @Override
    public void deletePurchasingById(String id) {
        Purchasing p = purchasingDao.findOne(id);
        List<PurchasingDetail> pds = p.getPds();
        purchasingDao.delete(id);

    }

    /* (non-Javadoc)
     * @see com.barakawei.lightwork.service.PurchasingService#findPurchasings(org.springframework.data.domain.Pageable)
     */
    @Override
    public Page<Purchasing> findPurchasings(SearchForm sf) {

        if(StringUtils.isBlank(sf.getOrderField())){
            sf.setOrderField("complete");
            sf.setOrderDirection("asc");
        }else if(sf.getOrderField().equals("cutGroup")||sf.getOrderField().equals("finshCut")||sf.getOrderField().equals("productGroup")||sf.getOrderField().equals("finshZipper")){
            sf.setOrderDirection("desc");

        }

        Pageable pageable = new PageRequest(sf.getPageNum(), sf.getNumPerPage(),sf.getDirection(),sf.getOrderField());
        Page<Purchasing> p = purchasingDao.findAll(getWhereClause(sf), pageable);

        if (!CollectionUtils.isEmpty(p.getContent())) {
            for(Purchasing pp : p.getContent()){
                boolean kecaijian = true;
                boolean keshengchan = true;
                boolean yiqi = false;
                boolean mianTime = true;
                boolean fuTime = true;
                boolean mian = false;
                boolean fu = false;
                Date mianDate = null;
                Date fuDate = null;
                if(pp.getPds().size()==0){
                    kecaijian = false;
                    keshengchan = false;
                }
                Date dd = pp.getDataDate();
                int cut = pp.getFinshCut();
                for(PurchasingDetail pd:pp.getPds()){
                        if(!StringUtils.isBlank(pd.getGoods().getType())&&pd.getGoods().getType().contains("面里料")){
                            mian = true;
                        }
                        if(!StringUtils.isBlank(pd.getGoods().getType())&&pd.getGoods().getType().contains("辅料")){
                            fu = true;
                        }
                        float count;
                        if(StringUtils.isBlank(pd.getActualPurchasingCount())){
                            count = 0;
                        }else{
                            count = Float.valueOf(pd.getActualPurchasingCount());
                        }
                        if(!StringUtils.isBlank(pd.getGoods().getType())&&pd.getGoods().getType().contains("面里料") ){
                            Date aet = pd.getActualEntryTime();
                            if((aet == null && count > 0)){
                                mianTime = false;
                                kecaijian = false;
                                //keshengchan = false;
                            }else{
                                if(mianDate == null || (aet!=null&&aet.compareTo(mianDate) == 1)){
                                    mianDate =aet;
                                }
                            }

                        }else if(!StringUtils.isBlank(pd.getGoods().getType())&&pd.getGoods().getType().contains("辅料")){
                            Date aet = pd.getActualEntryTime();
                            if((aet == null && count > 0)){
                                keshengchan = false;
                                fuTime = false;
                            }else{
                                if(fuDate == null || (aet!=null&&aet.compareTo(fuDate) == 1)){
                                    fuDate =aet;
                                }
                            }
                        }


                }
                if(!mian){
                    kecaijian = true;
                    //keshengchan = false;
                    mianDate = null;
                }
                if(!fu){
                    keshengchan = true;
                    fuDate = null;
                }
                if(mianTime){
                    if(mianDate == null){
                        mianDate = pp.getStartTime();
                    }
                    pp.setMianDate(mianDate);
                }
                if(fuTime){
                    if(fuDate==null){
                        fuDate = pp.getStartTime();
                    }
                    pp.setFuDate(fuDate);
                }
                if(keshengchan && kecaijian ){
                    yiqi = true;
                }
                if(dd!=null&&cut==1&&yiqi){
                    pp.setComplete(4);//待生产;
                }
                else if(yiqi){//已齐
                    pp.setComplete(3);
                }else if(kecaijian){//待裁剪
                    pp.setComplete(2);
                }else if(pp.getContractDate() != null && new Date().compareTo(pp.getContractDate()) == 1 && !kecaijian){//逾期
                    pp.setComplete(0);
                }else{
                    //待料
                    pp.setComplete(1);
                }

                if(pp.getPlanUnderlineDate() != null||pp.getLineDate() !=null){//生产中
                    pp.setComplete(5);
                }

                if(pp.getPlanUnderlineDate() != null && new Date().compareTo(pp.getPlanUnderlineDate())==1){//空白
                    pp.setComplete(6);
                }

                if(pp.getHou() ==1 ){//锁钉
                    pp.setComplete(7);
                }else if(pp.getHou() == 2){//水洗
                    pp.setComplete(8);
                }else if(pp.getHou()==3){//后道包装
                    pp.setComplete(9);
                }

                if(pp.getFinshDate() != null){//待发货
                    pp.setComplete(10);
                }
                if(!pp.getOngoing()){//已完成
                    pp.setComplete(11);
                }
                purchasingDao.save(pp);

            }
        }

        Page<Purchasing> pdata = purchasingDao.findAll(getWhereClause(sf), pageable);
        return pdata;
    }

    @Override
    public void complete(Purchasing purchasing) {
        String role = UserContextUtil.getCurrentRole().getName();
        Purchasing _p = this.findPurchasingById(purchasing.getId());
        if (StringUtils.equals(Role.ROLE_TECHNOLOG, role)) {
            int i=0;
            for(PurchasingDetail _pd : _p.getPds()){
                _pd.getGoods().setConsume(purchasing.getPds().get(i).getGoods().getConsume());
                i++;
            }
            _p.setActualShrinkage(purchasing.getActualShrinkage());
            _p.setZipperShrinkage(purchasing.getZipperShrinkage());
            List<Zipper> _zippers = _p.getZippers();
            List<String> types = new ArrayList<String>();
            for(Zipper z:_zippers){
                types.add(z.getPosition());
            }
            List<Zipper> zippers = purchasing.getZippers();
            if (CollectionUtils.isNotEmpty(zippers)) {
                List<Zipper> remove = new ArrayList<Zipper>();
                for (Zipper _z : _zippers) {
                    if (!zippers.contains(_z)) {
                        zipperDao.delete(_z);
                        remove.add(_z);
                    }
                }
                _zippers.removeAll(remove);
                for (Zipper _zipper : zippers) {
                   if(!types.contains(_zipper.getPosition())){
                       List<Model> models= _p.getCountDetailList();
                       String type = _zipper.getPosition();
                       if(StringUtils.isNotBlank(type)){
                       models.add(new Model("145", "model_145", "", type));
                       models.add(new Model("150", "model_150", "", type));
                       models.add(new Model("155", "model_155", "", type));
                       models.add(new Model("160", "model_160", "", type));
                       models.add(new Model("165", "model_165", "", type));
                       models.add(new Model("170", "model_170", "", type));
                       models.add(new Model("175", "model_175", "", type));
                       models.add(new Model("180", "model_180", "", type));
                       models.add(new Model("185", "model_185", "", type));
                       models.add(new Model("190", "model_190", "", type));
                       models.add(new Model("195", "model_195", "", type));
                       models.add(new Model("200", "model_200", "", type));
                       models.add(new Model("205", "model_205", "", type));
                       _p.setCountDetailList(models);
                       }
                   }
                    _zipper.setPurchasing(purchasing);
                    zipperDao.save(_zipper);
                    dataDictService.saveDataDictByZipper(_zipper);
                }
            }
            _p.setPlanUnderlineDate(purchasing.getPlanUnderlineDate());
            _p.setCutGroup(purchasing.getCutGroup());
            _p.setFinshCut(purchasing.getFinshCut());
            _p.setFinshZipper(purchasing.getFinshZipper());
            _p.setZipperData(purchasing.getZipperData());
            _p.setTypeNumber(purchasing.getTypeNumber());
            purchasingDao.save(_p);

        }else if(StringUtils.equals(Role.ROLE_DIRECTOR,role)){
            _p.setProductGroup(purchasing.getProductGroup());
            if(StringUtils.isNotBlank(_p.getProductGroup()) && _p.getLineDate() == null){
                _p.setLineDate(new Date());
            }else{
                _p.setLineDate(null);
            }
            _p.setPlanUnderlineDate(purchasing.getPlanUnderlineDate());
            _p.setRemark(purchasing.getRemark());
            _p.setHou(purchasing.getHou());
            purchasingDao.save(_p);
        }
        else if (StringUtils.equals(Role.ROLE_PRODUCT, role)) {
            int i=0;
            for(PurchasingDetail _pd : _p.getPds()){
                _pd.getGoods().setOrderCount(purchasing.getPds().get(i).getGoods().getOrderCount());
                i++;
            }
            List<Zipper> zippers = purchasing.getZippers();
            if (CollectionUtils.isNotEmpty(zippers)) {
                _p.setCountDetail(purchasing.getCountDetail());
            }
            _p.setDataDate(purchasing.getDataDate());
            purchasingDao.save(_p);
        }
        else if (StringUtils.equals(Role.ROLE_PURCHASING, role)) {
            _p.setContractDate(purchasing.getContractDate());
            purchasingDao.save(_p);

        }
        else if (StringUtils.equals(Role.ROLE_WAREHOUSE, role)) {
            _p.setFinshDate(purchasing.getFinshDate());
            purchasingDao.save(_p);
        }

        boolean ok = true;
        for (PurchasingDetail _pd : purchasing.getPds()) {
            PurchasingDetail pd = purchasingDetailDao.findOne(_pd.getId());
            //Assert.notNull(pd, "error");
            this.completeByRole(role, pd, _pd, purchasing);
        }

     if (StringUtils.equals(role, Role.ROLE_WAREHOUSE)) {
         Purchasing pp = this.findPurchasingById(purchasing.getId());
        boolean end = true;
        if (CollectionUtils.isEmpty(_p.getPds())) {
            end = false;
        }
        for (PurchasingDetail pd : _p.getPds()) {
            if (StringUtils.isBlank(pd.getGoods().getActualUse())) {
                end = false;
            }

        }
        if (end) {
            pp.setEndTime(new Date());
            pp.setOngoing(false);
        }else{
            pp.setOngoing(true);

        }

         purchasingDao.save(pp);
     }
     //updateStatus(purchasing);
    }

    private void completeByRole(String role, PurchasingDetail pd, PurchasingDetail _pd, Purchasing p) {

        if (StringUtils.equals(role, Role.ROLE_PURCHASING)) {
            pd.setExpectedArrivalTime(_pd.getExpectedArrivalTime());
            pd.getGoods().setOriPrice(_pd.getGoods().getOriPrice());
            pd.getGoods().setPrice(_pd.getGoods().getPrice());
            //pd.setActualPurchasingCount(_pd.getActualPurchasingCount());
            //pd.setWarehouseCount(_pd.getWarehouseCount());
            purchasingDetailDao.save(pd);
        } else if (StringUtils.equals(role, Role.ROLE_WAREHOUSE)) {

            pd.setWarehouseCount(_pd.getWarehouseCount());
            pd.setPlanEntryTime(_pd.getPlanEntryTime());
            pd.setPlanEntryCount(_pd.getPlanEntryCount());
            pd.setActualEntryTime(_pd.getActualEntryTime());
            pd.getGoods().setActualUse(_pd.getGoods().getActualUse());
            pd.getGoods().setOriPrice(_pd.getGoods().getOriPrice());
            pd.getGoods().setPrice(_pd.getGoods().getPrice());

            purchasingDetailDao.save(pd);
        } else if (StringUtils.equals(role, Role.ROLE_QUALITY)) {
            pd.setShrinkage(_pd.getShrinkage());
            purchasingDetailDao.save(pd);
        } else if (StringUtils.equals(role, Role.ROLE_LEADER)) {
        } else if (StringUtils.equals(role, Role.ROLE_TECHNOLOG)) {


            pd.getGoods().setDischargeSpec(_pd.getGoods().getDischargeSpec());
            pd.getGoods().setActualWidth(_pd.getGoods().getActualWidth());
            pd.getGoods().setActualConsume((_pd.getGoods().getActualConsume()));

            purchasingDetailDao.save(pd);
        } else if (StringUtils.equals(role, Role.ROLE_PRODUCT)) {

        }

    }

    @Override
    public Purchasing findTaskByCurrentUser(String id) {
        Purchasing purchasing = purchasingDao.findOne(id);
        int complete = 0;
        int pending = 0;

        for (PurchasingDetail _pd : purchasing.getPds()) {
            _pd.getGoods().setPlanEntryCount(_pd.getPlanEntryCount());
            _pd.getGoods().setWarehouseCount(_pd.getWarehouseCount());

        }
        purchasing.getCountDetailList();
        purchasing.sort();
        purchasing.sortZipper();
        return purchasing;
    }

    private Specification<Purchasing> getWhereClause(final SearchForm sf) {
        return new Specification<Purchasing>() {
            @Override
            public Predicate toPredicate(Root<Purchasing> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                Map<String, String> search = sf.getSearchMap();
                for (String key : search.keySet()) {
                    String value = search.get(key);
                    if("ongoing".equals(key)){
                        boolean v = false;
                        if("1".equals(value)){
                            v =true;
                            predicate.getExpressions().add(cb.equal(root.<String>get(key),v));
                            String str = "预下";
                            predicate.getExpressions().add(cb.notLike(root.<String>get("orderNumber"), "%" + str + "%"));
                        }else if("0".equals(value)){
                           v =false;
                            predicate.getExpressions().add(cb.equal(root.<String>get(key),v));
                        }else if("2".equals(value)){//预下
                            value ="预下";
                            predicate.getExpressions().add(cb.like(root.<String>get("orderNumber"), "%" + value + "%"));

                        }

                    }else{
                        if (StringUtils.isNotBlank(value)) {
                            predicate.getExpressions().add(cb.like(root.<String>get(key), "%" + value + "%"));

                        }
                    }
                }
                query.where(predicate);
                return null;
            }
        };
    }
}
