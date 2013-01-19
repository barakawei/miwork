package com.barakawei.lightwork.domain;

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

    private int numPerPage =20;

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
