package com.barakawei.lightwork.service.impl;

import com.barakawei.lightwork.dao.*;
import com.barakawei.lightwork.domain.*;
import com.barakawei.lightwork.service.DataDictService;
import com.barakawei.lightwork.service.WorkflowProcessDefinitionService;
import com.barakawei.lightwork.util.UserContextUtil;
import org.activiti.engine.*;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
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
import org.springframework.util.Assert;

import javax.persistence.criteria.*;
import java.util.*;

/**
 * @author qianxiaowei
 * @date 2013-1-9
 */

@Service
public class PurchasingServiceImpl implements PurchasingService {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    protected TaskService taskService;

    @Autowired
    protected HistoryService historyService;

    @Autowired
    protected RepositoryService repositoryService;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private PurchasingDao purchasingDao;

    @Autowired
    private ZipperDao zipperDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private DataDictService dataDictService;

    @Autowired
    private PurchasingDetailDao purchasingDetailDao;

    @Autowired
    protected WorkflowProcessDefinitionService wpdService;

    @Override
    public List<Purchasing> findAll() {
        Sort s = new Sort(Sort.Direction.DESC,"startTime");
        return purchasingDao.findAll(s);
    }

    @Override
    public void createPurchasing(Purchasing purchasing) {
        if(CollectionUtils.isEmpty(purchasing.getPds())){
            return;
        }
        purchasing.setPlanningUser(UserContextUtil.getCurrentUser());
        purchasing.setStartTime(new Date());
        purchasing.setOngoing(true);
        purchasingDao.save(purchasing);
        for (PurchasingDetail pd : purchasing.getPds()) {
            String businessKey = pd.getId();
            identityService.setAuthenticatedUserId(purchasing.getPlanningUser().getId());
            ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(Purchasing.FLOW, businessKey);
            String processInstanceId = processInstance.getId();
            pd.setProcessInstanceId(processInstanceId);
            pd.setPurchasing(purchasing);
            purchasingDetailDao.save(pd);
        }
    }

    /* (non-Javadoc)
     * @see com.barakawei.lightwork.service.PurchasingService#findPurchasingById(java.lang.String)
     */
    @Override
    public Purchasing findPurchasingById(String id) {
        return purchasingDao.findOne(id);
    }

    /* (non-Javadoc)
     * @see com.barakawei.lightwork.service.PurchasingService#updatePurchasing(com.barakawei.lightwork.domain.Purchasing)
     */
    @Override
    public void updatePurchasing(Purchasing purchasing) {
        Purchasing p =purchasingDao.findOne(purchasing.getId());
        List<PurchasingDetail> pds = p.getPds();
        List<PurchasingDetail> newPds = purchasing.getPds();
        for (PurchasingDetail pd : pds) {
            if (!newPds.contains(pd)) {
                purchasingDetailDao.delete(pd);
            }
        }
        for (PurchasingDetail pd : newPds) {
            if (!pds.contains(pd)) {
                pd.setPurchasing(p);
                purchasingDetailDao.save(pd);
                String businessKey = pd.getId();
                identityService.setAuthenticatedUserId(p.getPlanningUser().getId());
                ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(Purchasing.FLOW, businessKey);
                pd.setProcessInstanceId(processInstance.getId());
                purchasingDetailDao.save(pd);
            }
        }
        p.setOrderNumber(purchasing.getOrderNumber());
        p.setOrderName(purchasing.getOrderName());
        p.setOrderCount(purchasing.getOrderCount());
        p.setSerialNumber(purchasing.getSerialNumber());
        p.setApplyTime(purchasing.getApplyTime());
        p.setConfirmTime(purchasing.getConfirmTime());
        p.setDischargeRecognition(purchasing.getDischargeRecognition());
        purchasingDao.save(p);


    }

    /* (non-Javadoc)
     * @see com.barakawei.lightwork.service.PurchasingService#deletePurchasingById(java.lang.String)
     */
    @Override
    public void deletePurchasingById(String id) {
        purchasingDao.delete(id);

    }

    /* (non-Javadoc)
     * @see com.barakawei.lightwork.service.PurchasingService#findPurchasings(org.springframework.data.domain.Pageable)
     */
    @Override
    public Page<Purchasing> findPurchasings(SearchForm sf) {
        Pageable pageable = new PageRequest(sf.getPageNum(), sf.getNumPerPage());
        return purchasingDao.findAll(getWhereClause(sf), pageable);
    }

    @Override
    public void complete(Purchasing purchasing) {
        String role = UserContextUtil.getCurrentRole().getName();
        Purchasing _p =  this.findPurchasingById(purchasing.getId());
        if (StringUtils.equals(Role.ROLE_TECHNOLOG, role)) {
            _p.setActualShrinkage(purchasing.getActualShrinkage());
            _p.setZipperShrinkage(purchasing.getZipperShrinkage());
            purchasingDao.save(_p);
            List<Zipper> _zippers = purchasingDao.findOne(purchasing.getId()).getZippers();
            List<Zipper> zippers = purchasing.getZippers();
            if (CollectionUtils.isNotEmpty(zippers)) {
                for (Zipper _z : _zippers) {
                    if (!zippers.contains(_z)) {
                        zipperDao.delete(_z);
                    }
                }
                for (Zipper _zipper : zippers) {
                    _zipper.setPurchasing(purchasing);
                    zipperDao.save(_zipper);
                    dataDictService.saveDataDictByZipper(_zipper);
                }
            }

        } else if (StringUtils.equals(Role.ROLE_PRODUCT, role)) {
            List<Zipper> zippers = purchasing.getZippers();
            if (CollectionUtils.isNotEmpty(zippers)) {
                for (Zipper _z : zippers) {
                    Zipper z = zipperDao.findOne(_z.getId());
                    z.setNumber(_z.getNumber());
                    zipperDao.save(z);
                }
            }
        }

        for (PurchasingDetail _pd : purchasing.getPds()) {
            PurchasingDetail pd = purchasingDetailDao.findOne(_pd.getId());
            Assert.notNull(pd, "error");
            this.completeByRole(role, pd, _pd,purchasing);
        }

        Purchasing pp =  this.findPurchasingById(purchasing.getId());
        boolean end = true;
        if(CollectionUtils.isEmpty(pp.getPds())){
            end = false;
        }
        for(PurchasingDetail pd:pp.getPds()){
            if(null == pd.getEndTime()){
                end = false;
                break;
            }
        }
        if(end){
            pp.setEndTime(new Date());
            pp.setOngoing(false);
            purchasingDao.save(pp);
        }

    }

    private void completeByRole(String role, PurchasingDetail pd, PurchasingDetail _pd,Purchasing p) {
        if (StringUtils.equals(role, Role.ROLE_PURCHASING)) {
            if (null != _pd.getExpectedArrivalTime()) {
                pd.setExpectedArrivalTime(_pd.getExpectedArrivalTime());
                purchasingDetailDao.save(pd);
                if (StringUtils.isNotBlank(_pd.getTaskId())) {
                    Map<String, Object> variables = new HashMap<String, Object>();
                    variables.put("expectedArrivalTime", _pd.getExpectedArrivalTime());
                    variables.put("purchasingDealTime", new Date());
                    variables.put("purchasingRemark", "");
                    taskService.complete(_pd.getTaskId(), variables);
                }
            }
        } else if (StringUtils.equals(role, Role.ROLE_WAREHOUSE)) {
            if (null != _pd.getPlanEntryTime() && null != _pd.getPlanEntryCount()) {
                pd.setPlanEntryTime(_pd.getPlanEntryTime());
                pd.setPlanEntryCount(_pd.getPlanEntryCount());
                purchasingDetailDao.save(pd);
                if (StringUtils.isNotBlank(_pd.getTaskId())) {
                    Map<String, Object> variables = new HashMap<String, Object>();
                    variables.put("planEntryTime", _pd.getPlanEntryTime());
                    variables.put("planEntryCount", _pd.getPlanEntryCount());
                    variables.put("planWarehouseDealTime", new Date());
                    variables.put("planWarehouseRemark", "");
                    taskService.complete(_pd.getTaskId(), variables);
                }
            }
            if(null != _pd.getActualEntryCount() && null!=_pd.getActualEntryTime()){
                pd.setActualEntryTime(_pd.getActualEntryTime());
                pd.setActualEntryCount(_pd.getActualEntryCount());
                pd.setEndTime(new Date());
                purchasingDetailDao.save(pd);
                if (StringUtils.isNotBlank(_pd.getTaskId())) {
                    Map<String, Object> variables = new HashMap<String, Object>();
                    variables.put("actualEntryTime", _pd.getActualEntryTime());
                    variables.put("actualEntryCount", _pd.getActualEntryCount());
                    variables.put("actualWarehouseDealTime", new Date());
                    variables.put("actualWarehouseRemark", "");
                    taskService.complete(_pd.getTaskId(), variables);
                }

            }
        } else if (StringUtils.equals(role, Role.ROLE_QUALITY)) {
            if (null != _pd.getQualified()) {
                pd.setQualified(_pd.getQualified());
                pd.setShrinkage(_pd.getShrinkage());
                purchasingDetailDao.save(pd);
                if (StringUtils.isNotBlank(_pd.getTaskId())) {
                    Map<String, Object> variables = new HashMap<String, Object>();
                    variables.put("qualified", _pd.getQualified());
                    variables.put("shrinkage", _pd.getShrinkage());
                    variables.put("review", pd.getReview() == null ? false : pd.getReview());
                    variables.put("hasShrinkage", _pd.getHasShrinkage());
                    variables.put("qualityTime", new Date());
                    variables.put("qualityRemark",_pd.getQualityRemark());
                    taskService.complete(_pd.getTaskId(), variables);
                }
            }
        } else if (StringUtils.equals(role, Role.ROLE_LEADER)) {
            if (StringUtils.isNotBlank(_pd.getReason())) {
                pd.setReview(true);
                pd.setReason(_pd.getReason());
                purchasingDetailDao.save(pd);
                if (StringUtils.isNotBlank(_pd.getTaskId())) {
                    Map<String, Object> variables = new HashMap<String, Object>();
                    variables.put("review", pd.getReview());
                    variables.put("reason", _pd.getReason());
                    variables.put("reviewTime", new Date());
                    taskService.complete(_pd.getTaskId(), variables);
                }
            }
        } else if (StringUtils.equals(role, Role.ROLE_TECHNOLOG)) {
            if (StringUtils.isNotBlank(_pd.getTaskId()) && CollectionUtils.isNotEmpty(p.getZippers())) {
                Map<String, Object> variables = new HashMap<String, Object>();
                variables.put("technologTime", new Date());
                taskService.complete(_pd.getTaskId(), variables);
            }
        } else if (StringUtils.equals(role, Role.ROLE_PRODUCT)) {
            if (StringUtils.isNotBlank(_pd.getTaskId()) && CollectionUtils.isNotEmpty(p.getZippers())) {
                Map<String, Object> variables = new HashMap<String, Object>();
                variables.put("productTime", new Date());
                taskService.complete(_pd.getTaskId(), variables);
            }

        }

    }

    private void claim(){
        String role = UserContextUtil.getCurrentRole().getName();
        List<Task> tasks = taskService.createTaskQuery().processDefinitionKey(Purchasing.FLOW).taskCandidateGroup(role).active().orderByTaskPriority().desc()
                .orderByTaskCreateTime().desc().list();
        for (Task _task : tasks) {
            taskService.claim(_task.getId(), UserContextUtil.getCurrentUser().getId());
        }
    }

    @Override
    public Purchasing findTaskByCurrentUser(String id) {
        this.claim();
        User user = UserContextUtil.getCurrentUser();
        Purchasing purchasing = purchasingDao.findOne(id);
        List<Task> tasks = taskService.createTaskQuery().processDefinitionKey(Purchasing.FLOW).taskAssignee(user.getId()).active().orderByTaskPriority().desc()
                .orderByTaskCreateTime().desc().list();
        List<PurchasingDetail> toDoList = new ArrayList<PurchasingDetail>();
        for (Task _task : tasks) {
            String processInstanceId = _task.getProcessInstanceId();
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).active()
                    .singleResult();
            String businessKey = processInstance.getBusinessKey();
            PurchasingDetail pd = purchasingDetailDao.findOne(businessKey);
            if (null != pd && purchasing.equals(pd.getPurchasing())) {
                pd.setTask(_task);
                pd.setTaskId(_task.getId());
                if(_task.getAssignee() == null){
                    pd.setCurrentUserName("未签收");
                }else {
                    pd.setCurrentUserName(userDao.findOne(_task.getAssignee()).getName());
                }
                toDoList.add(pd);
            }
        }
        purchasing.setToDoList(toDoList);
        Collection<PurchasingDetail> otherList = CollectionUtils.subtract(purchasing.getPds(), toDoList);
        List<PurchasingDetail> completed= new ArrayList<PurchasingDetail>();
        List<PurchasingDetail> pending= new ArrayList<PurchasingDetail>();

        for(PurchasingDetail _pd : otherList){
           if(_pd.getEndTime() != null){
               completed.add(_pd);
           }else{
               pending.add(_pd);
           }
            ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(_pd.getProcessInstanceId()).active().singleResult();
            if(null == pi){
                _pd.setCurrentUserName("");
                continue;
            }
            _pd.setTask(wpdService.getCurrentTask(pi));
            Task _task = _pd.getTask();
            if(_task.getAssignee() == null){
                _pd.setCurrentUserName("未签收");
            }else {
                _pd.setCurrentUserName(userDao.findOne(_task.getAssignee()).getName());
            }
        }
        purchasing.getPds().clear();
        purchasing.getPds().addAll(toDoList);
        purchasing.getPds().addAll(pending);
        purchasing.getPds().addAll(completed);
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
                    if (StringUtils.isNotBlank(value)) {
                        predicate.getExpressions().add(cb.like(root.<String>get(key), "%" + value + "%"));

                    }
                }
                query.where(predicate);
                return null;
            }
        };
    }
}
