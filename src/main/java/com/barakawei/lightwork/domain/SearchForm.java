package com.barakawei.lightwork.domain;

import org.springframework.data.domain.Sort;

import java.util.HashMap;
import java.util.Map;

/**
 * User: baraka
 * Date: 13-1-13
 * Time: 下午8:24
 */
public class SearchForm {
    private Map<String,String> searchMap= new HashMap<String,String>();

    private int pageNum = 1;

    private int numPerPage = 500;

    private String orderField="complete";

    private Sort.Direction direction= Sort.Direction.ASC;

    private  String orderDirection = "ASC";

    public String getOrderDirection() {
        return orderDirection;
    }

    public void setOrderDirection(String orderDirection) {
        this.orderDirection = orderDirection;
        if("asc".equals(orderDirection)){
            direction = Sort.Direction.ASC;
        }
        if("desc".equals(orderDirection)){
            direction = Sort.Direction.DESC;
        }
    }

    public Sort.Direction getDirection() {
        return direction;
    }

    public void setDirection(Sort.Direction direction) {
        this.direction = direction;
    }

    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    public Map<String, String> getSearchMap() {
        return searchMap;
    }

    public void setSearchMap(Map<String, String> searchMap) {
        this.searchMap = searchMap;
    }

    public int getPageNum() {

        return pageNum < 1 ?0:pageNum-1;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getNumPerPage() {
        return numPerPage;
    }

    public void setNumPerPage(int numPerPage) {
        this.numPerPage = numPerPage;
    }
}
