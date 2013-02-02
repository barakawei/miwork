package com.barakawei.lightwork.domain;

import com.barakawei.lightwork.util.JsonUtil;

/**
 * Created with IntelliJ IDEA.
 * User: baraka
 * Date: 13-1-30
 * Time: 下午10:27
 * To change this template use File | Settings | File Templates.
 */
public class Model {

    private String name;

    private String type;

    private String value;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    private String position;
    public Model() {
    }

    public Model(String name, String type, String value, String position) {
        this.name = name;
        this.type = type;
        this.value = value;
        this.position = position;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String toJson(){
       return  JsonUtil.Obj2Json(this);
    }

}
