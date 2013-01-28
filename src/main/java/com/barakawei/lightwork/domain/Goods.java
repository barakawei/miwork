package com.barakawei.lightwork.domain;

import javax.persistence.*;

import nl.bstoi.poiparser.api.strategy.annotations.Cell;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
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

    //采购计划
    @Cell(columnNumber = 10)
    private String purchasingCount;

	//特殊信息
    @Cell(columnNumber = 9)
	private String description;


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
