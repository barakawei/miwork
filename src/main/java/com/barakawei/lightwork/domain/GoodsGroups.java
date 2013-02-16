package com.barakawei.lightwork.domain;

import nl.bstoi.poiparser.api.strategy.annotations.Cell;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: baraka
 * Date: 13-2-16
 * Time: 下午4:26
 * To change this template use File | Settings | File Templates.
 */
public class GoodsGroups {

    private String name;

    List<Goods> goods = new ArrayList<Goods>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Goods> getGoods() {
        return goods;
    }

    public void setGoods(List<Goods> goods) {
        this.goods = goods;
    }
}
