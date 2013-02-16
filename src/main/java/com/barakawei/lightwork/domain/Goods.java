package com.barakawei.lightwork.domain;

import javax.persistence.*;

import nl.bstoi.poiparser.api.strategy.annotations.Cell;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author qianxiaowei
 * @date 2013-1-6
 */
@Entity
public class Goods {

	@Id
	@GeneratedValue(generator = "system_uuid")
	@GenericGenerator(name = "system_uuid", strategy = "uuid")
	private String id;

    //货物名称
    @Cell(columnNumber = 0)
	private String name;

    //货物类型
    private String type;

	//型号规格
    @Cell(columnNumber = 1)
	private String specification;

	// 面辅料成分
    @Cell(columnNumber = 2)
	private String composition;

	//净宽/CM
    @Cell(columnNumber = 3)
	private String width;

    //单量
    @Cell(columnNumber = 4)
    private String orderCount;

	//颜色
    @Cell(columnNumber = 5)
	private String color;

	//预排单耗
    @Cell(columnNumber = 6)
	private String consume;

	//单位
    @Cell(columnNumber = 7)
	private String unit;

    //损耗1*%
    @Cell(columnNumber = 8)
    private String loss;

    //面辅料特殊要求
    @Cell(columnNumber = 9)
    private String description;

    //采购计划
    @Cell(columnNumber = 10)
    private String purchasingCount;

    //库存
    private String warehouseCount;

    //实需采购
    private String actualPurchasingCount;

    //原单价
    @Cell(columnNumber = 13)
    private String oriPrice;

    //采购单价
    @Cell(columnNumber = 14)
    private String price;

    // 计划入库时间
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date expectedArrivalTime;

    //预入库时间
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date planEntryTime;

    // 预入库数量
    private String planEntryCount;

    //实测缩率：经%/纬%
    private String shrinkage;


    //需追加
    @Cell(columnNumber = 19)
    private String needAdd;

    //排料规格
    @Cell(columnNumber = 20)
    private String dischargeSpec;

    //实际门幅
    @Cell(columnNumber = 21)
    private String actualWidth;

    //实排单耗
    @Cell(columnNumber = 22)
    private String actualConsume;

    //实际损耗
    @Cell(columnNumber = 23)
    private String actualLoss;

    //核定用料
    @Cell(columnNumber = 24)
    private String confirmUse;

    //实际使用
    @Cell(columnNumber = 25)
    private String actualUse;

    public String getWarehouseCount() {
        return warehouseCount;
    }

    public void setWarehouseCount(String warehouseCount) {
        this.warehouseCount = warehouseCount;
    }

    public String getActualPurchasingCount() {
        return actualPurchasingCount;
    }

    public void setActualPurchasingCount(String actualPurchasingCount) {
        this.actualPurchasingCount = actualPurchasingCount;
    }

    public Date getExpectedArrivalTime() {
        return expectedArrivalTime;
    }

    public void setExpectedArrivalTime(Date expectedArrivalTime) {
        this.expectedArrivalTime = expectedArrivalTime;
    }

    public Date getPlanEntryTime() {
        return planEntryTime;
    }

    public void setPlanEntryTime(Date planEntryTime) {
        this.planEntryTime = planEntryTime;
    }

    public String getPlanEntryCount() {
        return planEntryCount;
    }

    public void setPlanEntryCount(String planEntryCount) {
        this.planEntryCount = planEntryCount;
    }

    public String getShrinkage() {
        return shrinkage;
    }

    public void setShrinkage(String shrinkage) {
        this.shrinkage = shrinkage;
    }

    public String getOriPrice() {
        return oriPrice;
    }

    public void setOriPrice(String oriPrice) {
        this.oriPrice = oriPrice;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNeedAdd() {
        return needAdd;
    }

    public void setNeedAdd(String needAdd) {
        this.needAdd = needAdd;
    }

    public String getDischargeSpec() {
        return dischargeSpec;
    }

    public void setDischargeSpec(String dischargeSpec) {
        this.dischargeSpec = dischargeSpec;
    }

    public String getActualWidth() {
        return actualWidth;
    }

    public void setActualWidth(String actualWidth) {
        this.actualWidth = actualWidth;
    }

    public String getActualConsume() {
        return actualConsume;
    }

    public void setActualConsume(String actualConsume) {
        this.actualConsume = actualConsume;
    }

    public String getActualLoss() {
        return actualLoss;
    }

    public void setActualLoss(String actualLoss) {
        this.actualLoss = actualLoss;
    }

    public String getConfirmUse() {
        return confirmUse;
    }

    public void setConfirmUse(String confirmUse) {
        this.confirmUse = confirmUse;
    }

    public String getActualUse() {
        return actualUse;
    }

    public void setActualUse(String actualUse) {
        this.actualUse = actualUse;
    }

    @OneToMany(cascade = { CascadeType.REFRESH }, fetch = FetchType.LAZY, mappedBy = "goods")
    List<PurchasingDetail> pds = new ArrayList<PurchasingDetail>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getComposition() {
        return composition;
    }

    public void setComposition(String composition) {
        this.composition = composition;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(String orderCount) {
        this.orderCount = orderCount;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getConsume() {
        return consume;
    }

    public void setConsume(String consume) {
        this.consume = consume;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getLoss() {
        return loss;
    }

    public void setLoss(String loss) {
        this.loss = loss;
    }

    public String getPurchasingCount() {
        return purchasingCount;
    }

    public void setPurchasingCount(String purchasingCount) {
        this.purchasingCount = purchasingCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<PurchasingDetail> getPds() {
        return pds;
    }

    public void setPds(List<PurchasingDetail> pds) {
        this.pds = pds;
    }
}
