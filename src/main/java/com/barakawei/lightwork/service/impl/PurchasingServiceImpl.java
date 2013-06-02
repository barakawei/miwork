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
        p.setContractTime(purchasing.getContractTime());
        p.setOrderDate(purchasing.getOrderDate());
        purchasingDao.save(p);


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
        Pageable pageable = new PageRequest(sf.getPageNum(), sf.getNumPerPage());
        Page<Purchasing> p = purchasingDao.findAll(getWhereClause(sf), pageable);

        if (!CollectionUtils.isEmpty(p.getContent())) {
            for(Purchasing pp : p.getContent()){
                boolean kecaijian = true;
                boolean keshengchan = true;
                boolean mian = false;
                boolean fu = false;
                Date mianDate = null;
                Date fuDate = null;
                if(pp.getPds().size()==0){
                    kecaijian = false;
                    keshengchan = false;
                }
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
                            if(aet == null){
                                kecaijian = false;
                                keshengchan = false;
                            }else{
                                if(mianDate == null || aet.compareTo(mianDate) == 1){
                                    mianDate =aet;
                                }
                            }
                        }else if(!StringUtils.isBlank(pd.getGoods().getType())&&pd.getGoods().getType().contains("辅料")){
                            Date aet = pd.getActualEntryTime();
                            if(aet == null){
                                keshengchan = false;
                            }else{
                                if(fuDate == null || aet.compareTo(fuDate) == 1){
                                    fuDate =aet;
                                }
                            }
                        }


                }
                if(!mian){
                    kecaijian = false;
                    keshengchan = false;
                    mianDate = null;
                }
                if(!fu){
                    keshengchan = false;
                    fuDate = null;
                }

                if(keshengchan){//待生产
                    pp.setMianDate(mianDate);
                    pp.setFuDate(fuDate);
                    pp.setComplete(2);
                }else if(kecaijian){//待裁剪
                    pp.setMianDate(mianDate);
                    pp.setComplete(1);
                }else if(pp.getContractDate() != null && new Date().compareTo(pp.getContractDate()) == 1 && !kecaijian){//逾期
                    pp.setComplete(-1);
                }else{
                    pp.setComplete(0);
                }

                if(pp.getPlanUnderlineDate() != null){//生产中
                    pp.setComplete(3);
                }

                if(pp.getHou() ==1 ){//锁钉
                    pp.setComplete(4);
                }else if(pp.getHou() == 2){//水洗
                    pp.setComplete(5);
                }else if(pp.getHou()==3){//后道包装
                    pp.setComplete(6);
                }

                if(pp.getFinshDate() != null){//待发货
                    pp.setComplete(7);
                }

            }
        }

        return p;
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
                       _p.setCountDetailList(models);
                       }
                   }
                    _zipper.setPurchasing(purchasing);
                    zipperDao.save(_zipper);
                    dataDictService.saveDataDictByZipper(_zipper);
                }
            }
            _p.setCutGroup(purchasing.getCutGroup());
            _p.setFinshCut(purchasing.getFinshCut());
            purchasingDao.save(_p);

        }else if(StringUtils.equals(Role.ROLE_DIRECTOR,role)){
            _p.setProductGroup(purchasing.getProductGroup());
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

            purchasingDetailDao.save(pd);
        } else if (StringUtils.equals(role, Role.ROLE_QUALITY)) {
            if(StringUtils.isNotEmpty(_pd.getShrinkage())&&pd.getActualEntryTime() == null){
                pd.setActualEntryTime(new Date());
            }
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
        purchasing.setPending(pending);
        purchasing.setComplete(complete);
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
                        }else{
                           v =false;
                        }
                        predicate.getExpressions().add(cb.equal(root.<String>get(key),v));
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
