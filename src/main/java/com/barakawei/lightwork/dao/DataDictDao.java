package com.barakawei.lightwork.dao;

import com.barakawei.lightwork.domain.DataDict;
import com.barakawei.lightwork.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author qianxiaowei
 * @date 2012-11-25
 */
public interface DataDictDao extends JpaRepository<DataDict, String> {

    public List<DataDict> findByTypeAndValueLike(String type,String value);

    public List<DataDict> findByValueAndType(String value,String type);
}
