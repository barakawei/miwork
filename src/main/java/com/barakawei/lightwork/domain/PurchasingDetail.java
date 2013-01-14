package com.barakawei.lightwork.domain;

import java.util.Date;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class PurchasingDetail{
	
	@Id
	@GeneratedValue(generator = "system_uuid")
	@GenericGenerator(name = "system_uuid", strategy = "uuid")
	private String id;


	private String processInstanceId;

    @ManyToOne(cascade = { CascadeType.REFRESH })
    @JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne(cascade = { CascadeType.ALL})
	@JoinColumn(name = "goods_id")
	private Goods goods;

    @ManyToOne(cascade = { CascadeType.REFRESH })
    @JoinColumn(name = "purchasing_id")
    private Purchasing purchasing;
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date startTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date endTime;

	// 期望到货时间
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date expectedArrivalTime;

	// 是否合格
	private boolean qualified;

	// 领导审批信息
	private String reason;

    //单量
    private String orderCount;


	// 采购计划数量
	private float planPurchasingCount;

	// 库存数量
	private float warehouseCount;

	// 实需采购数量
	private float actualPurchasingCount;

	// 预入库数量
	private float planEntryCount;

	// 实际入库数量
	private float actualEntryCount;

    //面辅料特殊要求
    private String specialRequirements;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date planEntryTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date actualEntryTime;

	// -- 临时属性 --//

	// 流程任务
	@Transient
	private Task task;

	@Transient
	private Map<String, Object> variables;

	// 运行中的流程实例
	@Transient
	private ProcessInstance processInstance;

	// 历史的流程实例
	@Transient
	private HistoricProcessInstance historicProcessInstance;

	// 流程定义
	@Transient
	private ProcessDefinition processDefinition;

    public String getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(String orderCount) {
        this.orderCount = orderCount;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public String getSpecialRequirements() {
        return specialRequirements;
    }

    public void setSpecialRequirements(String specialRequirements) {
        this.specialRequirements = specialRequirements;
    }

    /**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the processInstanceId
	 */
	public String getProcessInstanceId() {
		return processInstanceId;
	}

	/**
	 * @param processInstanceId the processInstanceId to set
	 */
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the purchasing
	 */
	public Purchasing getPurchasing() {
		return purchasing;
	}

	/**
	 * @param purchasing the purchasing to set
	 */
	public void setPurchasing(Purchasing purchasing) {
		this.purchasing = purchasing;
	}

	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the expectedArrivalTime
	 */
	public Date getExpectedArrivalTime() {
		return expectedArrivalTime;
	}

	/**
	 * @param expectedArrivalTime the expectedArrivalTime to set
	 */
	public void setExpectedArrivalTime(Date expectedArrivalTime) {
		this.expectedArrivalTime = expectedArrivalTime;
	}

	/**
	 * @return the qualified
	 */
	public boolean isQualified() {
		return qualified;
	}

	/**
	 * @param qualified the qualified to set
	 */
	public void setQualified(boolean qualified) {
		this.qualified = qualified;
	}

	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * @param reason the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	/**
	 * @return the good
	 */

	/**
	 * @param good the good to set
	 */

	/**
	 * @return the planPurchasingCount
	 */
	public float getPlanPurchasingCount() {
		return planPurchasingCount;
	}

	/**
	 * @param planPurchasingCount the planPurchasingCount to set
	 */
	public void setPlanPurchasingCount(float planPurchasingCount) {
		this.planPurchasingCount = planPurchasingCount;
	}

	/**
	 * @return the warehouseCount
	 */
	public float getWarehouseCount() {
		return warehouseCount;
	}

	/**
	 * @param warehouseCount the warehouseCount to set
	 */
	public void setWarehouseCount(float warehouseCount) {
		this.warehouseCount = warehouseCount;
	}

	/**
	 * @return the actualPurchasingCount
	 */
	public float getActualPurchasingCount() {
		return actualPurchasingCount;
	}

	/**
	 * @param actualPurchasingCount the actualPurchasingCount to set
	 */
	public void setActualPurchasingCount(float actualPurchasingCount) {
		this.actualPurchasingCount = actualPurchasingCount;
	}

	/**
	 * @return the planEntryCount
	 */
	public float getPlanEntryCount() {
		return planEntryCount;
	}

	/**
	 * @param planEntryCount the planEntryCount to set
	 */
	public void setPlanEntryCount(float planEntryCount) {
		this.planEntryCount = planEntryCount;
	}

	/**
	 * @return the actualEntryCount
	 */
	public float getActualEntryCount() {
		return actualEntryCount;
	}

	/**
	 * @param actualEntryCount the actualEntryCount to set
	 */
	public void setActualEntryCount(float actualEntryCount) {
		this.actualEntryCount = actualEntryCount;
	}

	/**
	 * @return the planEntryTime
	 */
	public Date getPlanEntryTime() {
		return planEntryTime;
	}

	/**
	 * @param planEntryTime the planEntryTime to set
	 */
	public void setPlanEntryTime(Date planEntryTime) {
		this.planEntryTime = planEntryTime;
	}

	/**
	 * @return the actualEntryTime
	 */
	public Date getActualEntryTime() {
		return actualEntryTime;
	}

	/**
	 * @param actualEntryTime the actualEntryTime to set
	 */
	public void setActualEntryTime(Date actualEntryTime) {
		this.actualEntryTime = actualEntryTime;
	}

	/**
	 * @return the task
	 */
	public Task getTask() {
		return task;
	}

	/**
	 * @param task the task to set
	 */
	public void setTask(Task task) {
		this.task = task;
	}

	/**
	 * @return the variables
	 */
	public Map<String, Object> getVariables() {
		return variables;
	}

	/**
	 * @param variables the variables to set
	 */
	public void setVariables(Map<String, Object> variables) {
		this.variables = variables;
	}

	/**
	 * @return the processInstance
	 */
	public ProcessInstance getProcessInstance() {
		return processInstance;
	}

	/**
	 * @param processInstance the processInstance to set
	 */
	public void setProcessInstance(ProcessInstance processInstance) {
		this.processInstance = processInstance;
	}

	/**
	 * @return the historicProcessInstance
	 */
	public HistoricProcessInstance getHistoricProcessInstance() {
		return historicProcessInstance;
	}

	/**
	 * @param historicProcessInstance the historicProcessInstance to set
	 */
	public void setHistoricProcessInstance(
			HistoricProcessInstance historicProcessInstance) {
		this.historicProcessInstance = historicProcessInstance;
	}

	/**
	 * @return the processDefinition
	 */
	public ProcessDefinition getProcessDefinition() {
		return processDefinition;
	}

	/**
	 * @param processDefinition the processDefinition to set
	 */
	public void setProcessDefinition(ProcessDefinition processDefinition) {
		this.processDefinition = processDefinition;
	}

	
}
