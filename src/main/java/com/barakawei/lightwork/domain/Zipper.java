package com.barakawei.lightwork.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: baraka
 * Date: 13-1-19
 * Time: 下午12:38
 * To change this template use File | Settings | File Templates.
 */

@Entity
public class Zipper {

    @Id
    @GeneratedValue(generator = "system_uuid")
    @GenericGenerator(name = "system_uuid", strategy = "uuid")
    private String id;

    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "purchasing_id")
    private Purchasing purchasing;

    // 材质
    private String material;

    //规格
    private  String spec;

    //部位
    private String position;

    //名称
    private  String name;

    //尺寸
    private String size;

    //数量
    private Integer number;

    //型号
    private  String model;

    public Purchasing getPurchasing() {
        return purchasing;
    }

    public void setPurchasing(Purchasing purchasing) {
        this.purchasing = purchasing;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }


}
