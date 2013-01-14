package com.barakawei.lightwork.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.barakawei.lightwork.dao.UserDao;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.barakawei.lightwork.dao.PurchasingDao;
import com.barakawei.lightwork.dao.PurchasingDetailDao;
import com.barakawei.lightwork.domain.Purchasing;
import com.barakawei.lightwork.domain.PurchasingDetail;

@Service
@Transactional
public class PurchasingWorkflowService {

	private static Logger logger = LoggerFactory.getLogger(PurchasingWorkflowService.class);

	private RuntimeService runtimeService;

	protected TaskService taskService;

	protected HistoryService historyService;

	protected RepositoryService repositoryService;
	
	@Autowired
	private IdentityService identityService;
	
	@Autowired
	private PurchasingDao purchasingDao;

    @Autowired
    private UserDao userDao;

	@Autowired
	private PurchasingDetailDao purchasingDetailDao;


	public List<ProcessInstance> startWorkflow(Purchasing purchasing) {

        purchasing.setPlanningUser(userDao.findOne("3"));
        purchasing.setStartTime(new Date());
		purchasingDao.save(purchasing);
		List<ProcessInstance> pis = new ArrayList<ProcessInstance>();
		for(PurchasingDetail pd: purchasing.getPds()){
			String businessKey = pd.getId();
			identityService.setAuthenticatedUserId(purchasing.getPlanningUser().getId());
			ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("purchasing-flow", businessKey);
			String processInstanceId = processInstance.getId();
			pd.setProcessInstanceId(processInstanceId);
			pis.add(processInstance);
            purchasingDetailDao.save(pd);
		}

		return pis;
	}

//	/**
//	 * 查询待办任务
//	 * 
//	 * @param userId 用户ID
//	 * @return
//	 */
//	@Transactional(readOnly = true)
//	public List<Leave> findTodoTasks(String userId) {
//		List<Leave> results = new ArrayList<Leave>();
//		List<Task> tasks = new ArrayList<Task>();
//
//		// 根据当前人的ID查询
//		List<Task> todoList = taskService.createTaskQuery().processDefinitionKey("leave").taskAssignee(userId).active().orderByTaskPriority().desc()
//				.orderByTaskCreateTime().desc().list();
//
//		// 根据当前人未签收的任务
//		List<Task> unsignedTasks = taskService.createTaskQuery().processDefinitionKey("leave").taskCandidateUser(userId).active().orderByTaskPriority().desc()
//				.orderByTaskCreateTime().desc().list();
//
//		// 合并
//		tasks.addAll(todoList);
//		tasks.addAll(unsignedTasks);
//
//		// 根据流程的业务ID查询实体并关联
//		for (Task task : tasks) {
//			String processInstanceId = task.getProcessInstanceId();
//			ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).active()
//					.singleResult();
//			String businessKey = processInstance.getBusinessKey();
//			Leave leave = leaveManager.getLeave(new Long(businessKey));
//			leave.setTask(task);
//			leave.setProcessInstance(processInstance);
//			leave.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
//			results.add(leave);
//		}
//		return results;
//	}
//
//	/**
//	 * 读取运行中的流程
//	 * @return
//	 */
//	@Transactional(readOnly = true)
//	public List<Leave> findRunningProcessInstaces() {
//		List<Leave> results = new ArrayList<Leave>();
//		List<ProcessInstance> list = runtimeService.createProcessInstanceQuery().processDefinitionKey("leave").active().list();
//
//		// 关联业务实体
//		for (ProcessInstance processInstance : list) {
//			String businessKey = processInstance.getBusinessKey();
//			Leave leave = leaveManager.getLeave(new Long(businessKey));
//			leave.setProcessInstance(processInstance);
//			leave.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
//			results.add(leave);
//
//			// 设置当前任务信息
//			List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstance.getId()).active().orderByTaskCreateTime()
//					.desc().listPage(0, 1);
//			leave.setTask(tasks.get(0));
//
//		}
//		return results;
//	}
//
//	/**
//	 * 读取运行中的流程
//	 * @return
//	 */
//	@Transactional(readOnly = true)
//	public List<Leave> findFinishedProcessInstaces() {
//		List<Leave> results = new ArrayList<Leave>();
//		List<HistoricProcessInstance> list = historyService.createHistoricProcessInstanceQuery().processDefinitionKey("leave")
//				.finished().list();
//
//		// 关联业务实体
//		for (HistoricProcessInstance historicProcessInstance : list) {
//			String businessKey = historicProcessInstance.getBusinessKey();
//			Leave leave = leaveManager.getLeave(new Long(businessKey));
//			leave.setProcessDefinition(getProcessDefinition(historicProcessInstance.getProcessDefinitionId()));
//			leave.setHistoricProcessInstance(historicProcessInstance);
//			results.add(leave);
//		}
//		return results;
//	}

	/**
	 *  查询流程定义对象
	 * @param processDefinitionId	流程定义ID
	 * @return
	 */
	protected ProcessDefinition getProcessDefinition(String processDefinitionId) {
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
				.processDefinitionId(processDefinitionId).singleResult();
		return processDefinition;
	}

	

	@Autowired
	public void setRuntimeService(RuntimeService runtimeService) {
		this.runtimeService = runtimeService;
	}

	@Autowired
	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	@Autowired
	public void setHistoryService(HistoryService historyService) {
		this.historyService = historyService;
	}

	@Autowired
	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

}
