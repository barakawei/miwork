package com.barakawei.lightwork.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author qianxiaowei
 * @date 2013-1-9
 */
@Entity
public class Purchasing {
	@Id
	@GeneratedValue(generator = "system_uuid")
	@GenericGenerator(name = "system_uuid", strategy = "uuid")
	private String id;

    //计划人
    @ManyToOne(cascade = { CascadeType.REFRESH })
    @JoinColumn(name = "planning_user_id")
	private User planningUser;

    //排料确认
    private String dischargeRecognition;

    //订单名称
    private String orderName;

    //订单编号
    private String orderNumber;

    //序列号
    private String serialNumber;

    //订单数量
    private String orderCount;

    //供应时间
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date applyTime;


    //确认时间
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date confirmTime;


    //开始时间
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date startTime;

    //结束时间
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date endTime;

    //采购明细
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER, mappedBy = "purchasing")
	List<PurchasingDetail> pds = new ArrayList<PurchasingDetail>();

    public List<PurchasingDetail> getPds() {
        return pds;
    }

    public void setPds(List<PurchasingDetail> pds) {
        this.pds = pds;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getPlanningUser() {
        return planningUser;
    }

    public void setPlanningUser(User planningUser) {
        this.planningUser = planningUser;
    }

    public String getDischargeRecognition() {
        return dischargeRecognition;
    }

    public void setDischargeRecognition(String dischargeRecognition) {
        this.dischargeRecognition = dischargeRecognition;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(String orderCount) {
        this.orderCount = orderCount;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
