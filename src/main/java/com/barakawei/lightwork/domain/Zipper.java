package com.barakawei.lightwork.domain;

import com.barakawei.lightwork.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;

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

    //数量
    private String size;

    //已含缩率
    private int checkvalue;
    
    //净胸围
    private int chest;

    public int getChest() {
		return chest;
	}

	public void setChest(int chest) {
		this.chest = chest;
	}

	public int getCheckvalue() {
        return checkvalue;
    }

    public void setCheckvalue(int checkvalue) {
        this.checkvalue = checkvalue;
    }

    //套数
    private Integer number;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    private String model;

    //型号
    @Column(name="zipper_count", columnDefinition = "text")
    private String zipperCount;

    @Transient
    private List<Model> zipperCountList = new ArrayList<Model>();

    public List<String> getCount() {
        return count;
    }

    public void setCount(List<String> count) {
        this.count = count;
    }

    @Transient
    private List<String> count = new ArrayList<String>();

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

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getZipperCount() {
        return zipperCount;
    }

    public void setZipperCount(String zipperCount) {
        this.zipperCount = zipperCount;
    }

    public List<Model> getZipperCountList() {
        this.zipperCountList = JsonUtil.json2GenericObject(this.zipperCount,new TypeReference<List<Model>>() {
        });
        if(zipperCountList!=null && zipperCountList.size()==11){
            zipperCountList.add(new Model("200","model_200","",zipperCountList.get(0).getPosition(),0));
            zipperCountList.add(new Model("205","model_205","",zipperCountList.get(0).getPosition(),0));

        }
        return zipperCountList;
    }

    public void setZipperCountList(List<Model> zipperCountList) {
        this.zipperCountList = zipperCountList;
        this.zipperCount = JsonUtil.Obj2Json(zipperCountList);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        Zipper p = (Zipper) obj;

        return StringUtils.equals(this.id, p.getId());

    }


}
