package com.barakawei.lightwork.dao;

import com.barakawei.lightwork.domain.Goods;
import org.springframework.data.jpa.repository.JpaRepository;

import com.barakawei.lightwork.domain.Purchasing;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author qianxiaowei
 * @date 2012-11-25
 */
public interface PurchasingDao extends JpaRepository<Purchasing, String>,JpaSpecificationExecutor<Purchasing> {


}
