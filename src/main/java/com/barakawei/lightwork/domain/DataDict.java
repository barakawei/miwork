package com.barakawei.lightwork.domain;

import com.barakawei.lightwork.dao.DataDictDao;
import com.barakawei.lightwork.service.DataDictService;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: baraka
 * Date: 13-1-19
 * Time: 下午1:22
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class DataDict {

    @Id
    @GeneratedValue(generator = "system_uuid")
    @GenericGenerator(name = "system_uuid", strategy = "uuid")
    private String id;

    // 材质
    public static final  String MATERIAL = "material";

    //规格
    public  static final String SPEC = "spec";

    //部位
    public static  final String POSITION = "position";

    //名称
    public  static final String NAME = "name";

    //型号
    public static final String MODEL= "model";

    //货物类型
    public static final String ZIPPER = "zipper";

    //颜色
    public static final String COLOR = "color";

    //货物类别
    public static final String goodsType= "goodsType";

    private String type;

    @Column(name = "type_name")
    private String typeName;

    private String value;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getValue() {
        return value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
