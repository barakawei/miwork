package com.barakawei.lightwork.domain;

import javax.persistence.*;

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
	private String name;

    //货物类型
    private String type;

	//型号规格
	private String specification;

	// 面辅料成分
	private String composition;

	//净宽/CM
	private Integer width;

	//颜色
	private String color;

	//单位
	private String unit;

	//特殊信息
	private String description;

    @OneToMany(cascade = { CascadeType.REFRESH }, fetch = FetchType.LAZY, mappedBy = "goods")
    List<PurchasingDetail> pds = new ArrayList<PurchasingDetail>();

    public List<PurchasingDetail> getPds() {
        return pds;
    }

    public void setPds(List<PurchasingDetail> pds) {
        this.pds = pds;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the specification
	 */
	public String getSpecification() {
		return specification;
	}

	/**
	 * @param specification
	 *            the specification to set
	 */
	public void setSpecification(String specification) {
		this.specification = specification;
	}

	/**
	 * @return the composition
	 */
	public String getComposition() {
		return composition;
	}

	/**
	 * @param composition
	 *            the composition to set
	 */
	public void setComposition(String composition) {
		this.composition = composition;
	}

	/**
	 * @return the width
	 */
	public Integer getWidth() {
		return width;
	}

	/**
	 * @param width
	 *            the width to set
	 */
	public void setWidth(Integer width) {
		this.width = width;
	}

	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @param color
	 *            the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * @return the unit
	 */
	public String getUnit() {
		return unit;
	}

	/**
	 * @param unit
	 *            the unit to set
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
