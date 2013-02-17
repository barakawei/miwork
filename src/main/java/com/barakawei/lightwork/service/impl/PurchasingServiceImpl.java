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
        this.claim();
        Sort s = new Sort(Sort.Direction.DESC, "startTime");
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
        Purchasing p = purchasingDao.findOne(purchasing.getId());
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
            }else{
                pd.setPurchasing(p);
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
        Purchasing p = purchasingDao.findOne(id);
        List<PurchasingDetail> pds = p.getPds();
        for(PurchasingDetail pd : pds){
            List<Task> tasks = taskService.createTaskQuery().processInstanceId(pd.getProcessInstanceId()).list();
            Set tid = new HashSet();
            for(Task t:tasks){
                tid.add(t.getId());
            }
            runtimeService.deleteProcessInstance(pd.getProcessInstanceId(),"del");
            taskService.deleteTasks(tid);
        }
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
        Purchasing _p = this.findPurchasingById(purchasing.getId());
        if (StringUtils.equals(Role.ROLE_TECHNOLOG, role)) {
            _p.setActualShrinkage(purchasing.getActualShrinkage());
            _p.setZipperShrinkage(purchasing.getZipperShrinkage());
            List<Zipper> _zippers = _p.getZippers();
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
                    _zipper.setPurchasing(purchasing);
                    zipperDao.save(_zipper);
                    dataDictService.saveDataDictByZipper(_zipper);
                }
            }
            purchasingDao.save(_p);

        } else if (StringUtils.equals(Role.ROLE_PRODUCT, role)) {
            List<Zipper> zippers = purchasing.getZippers();
            if (CollectionUtils.isNotEmpty(zippers)) {
                _p.setCountDetail(purchasing.getCountDetail());
                purchasingDao.save(_p);
            }
        }

        for (PurchasingDetail _pd : purchasing.getPds()) {
            PurchasingDetail pd = purchasingDetailDao.findOne(_pd.getId());
            ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(pd.getProcessInstanceId()).singleResult();
            if(null != pi){
                pd.setTask(wpdService.getCurrentTask(pi));
            }
            Assert.notNull(pd, "error");
            this.completeByRole(role, pd, _pd, purchasing);
        }

        boolean end = true;
        if (CollectionUtils.isEmpty(_p.getPds())) {
            end = false;
        }
        for (PurchasingDetail pd : _p.getPds()) {
            if (null == pd.getEndTime()) {
                end = false;
                break;
            }
        }
        if (end) {
            _p.setEndTime(new Date());
            _p.setOngoing(false);
            purchasingDao.save(_p);
        }

    }

    private void completeByRole(String role, PurchasingDetail pd, PurchasingDetail _pd, Purchasing p) {
        if (StringUtils.equals(role, Role.ROLE_PURCHASING)) {
            pd.setExpectedArrivalTime(_pd.getExpectedArrivalTime());
            pd.setActualPurchasingCount(_pd.getActualPurchasingCount());
            pd.setWarehouseCount(_pd.getWarehouseCount());
            purchasingDetailDao.save(pd);
            if (null != _pd.getExpectedArrivalTime()) {
                if (pd.getTask() != null && StringUtils.equals(pd.getTask().getTaskDefinitionKey(), "purchasing")) {
                    taskService.complete(_pd.getTaskId());
                }
            }
        } else if (StringUtils.equals(role, Role.ROLE_WAREHOUSE)) {
            pd.setWarehouseCount(_pd.getWarehouseCount());
            pd.setPlanEntryTime(_pd.getPlanEntryTime());
            pd.setPlanEntryCount(_pd.getPlanEntryCount());
            purchasingDetailDao.save(pd);
            if (null != _pd.getPlanEntryTime() && null != _pd.getPlanEntryCount()) {
                if (pd.getTask() != null && StringUtils.equals(pd.getTask().getTaskDefinitionKey(), "warehouseEntryPlan")) {
                    taskService.complete(_pd.getTaskId());
                }
                if (pd.getTask() != null && StringUtils.equals(pd.getTask().getTaskDefinitionKey(), "warehouseEntryActual")) {
                    pd.setActualEntryTime(_pd.getPlanEntryTime());
                    pd.setActualEntryCount(_pd.getPlanEntryCount());
                    pd.setEndTime(new Date());
                    purchasingDetailDao.save(pd);
                    taskService.complete(_pd.getTaskId());
                }
            }
        } else if (StringUtils.equals(role, Role.ROLE_QUALITY)) {
            pd.setQualified(_pd.getQualified());
            pd.setShrinkage(_pd.getShrinkage());
            purchasingDetailDao.save(pd);
            if (pd.getTask() != null && StringUtils.equals(pd.getTask().getTaskDefinitionKey(), "quality")) {
                Map<String, Object> variables = new HashMap<String, Object>();
                variables.put("qualified", _pd.getQualified());
                variables.put("shrinkage", _pd.getShrinkage());
                variables.put("review", pd.getReview() == null ? false : pd.getReview());
                variables.put("hasShrinkage", _pd.getHasShrinkage());
                taskService.complete(_pd.getTaskId(), variables);
            }
        } else if (StringUtils.equals(role, Role.ROLE_LEADER)) {
            if (pd.getTask() != null && StringUtils.equals(pd.getTask().getTaskDefinitionKey(), "leaderAudit")) {
                pd.setReview(true);
                pd.setReason(_pd.getReason());
                purchasingDetailDao.save(pd);
                Map<String, Object> variables = new HashMap<String, Object>();
                variables.put("review", pd.getReview());
                taskService.complete(_pd.getTaskId(), variables);
            }
        } else if (StringUtils.equals(role, Role.ROLE_TECHNOLOG)) {
            if (pd.getTask() != null && StringUtils.equals(pd.getTask().getTaskDefinitionKey(), "tech")) {
                taskService.complete(_pd.getTaskId());
            }
        } else if (StringUtils.equals(role, Role.ROLE_PRODUCT)) {
            if (pd.getTask() != null && StringUtils.equals(pd.getTask().getTaskDefinitionKey(), "product")) {
                Map<String, Object> variables = new HashMap<String, Object>();
                variables.put("qualified", true);
                variables.put("hasShrinkage", false);
                taskService.complete(_pd.getTaskId(), variables);
            }

        }

    }

    private void claim() {
        String role = UserContextUtil.getCurrentRole().getName();
        List<Task> tasks = taskService.createTaskQuery().processDefinitionKey(Purchasing.FLOW).taskCandidateGroup(role).active().orderByTaskPriority().desc()
                .orderByTaskCreateTime().desc().list();
        for (Task _task : tasks) {
            taskService.claim(_task.getId(), UserContextUtil.getCurrentUser().getId());
        }
    }

    @Override
    public Purchasing findTaskByCurrentUser(String id) {
        Purchasing purchasing = purchasingDao.findOne(id);
        int complete = 0;
        int pending = 0;
        for (PurchasingDetail pd : purchasing.getPds()) {
            if (pd.getEndTime() != null) {
                complete++;
            } else {
                pending++;
            }
            ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(pd.getProcessInstanceId()).active().singleResult();
            if (null == pi) {
                pd.setCurrentUserName("");
                continue;
            }
            pd.setTask(wpdService.getCurrentTask(pi));
            Task _task = pd.getTask();
            if (_task.getAssignee() == null) {
                pd.setCurrentUserName("未签收");
            } else {
                pd.setCurrentUserName(userDao.findOne(_task.getAssignee()).getName());
            }
            pd.setTaskId(_task.getId());


        }
        purchasing.getCountDetailList();
        purchasing.setPending(pending);
        purchasing.setComplete(complete);
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
