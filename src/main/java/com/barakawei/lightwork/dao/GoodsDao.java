package com.barakawei.lightwork.dao;

import com.barakawei.lightwork.domain.Goods;
import com.barakawei.lightwork.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author qianxiaowei
 * @date 2012-11-25
 */
public interface GoodsDao extends JpaRepository<Goods, String> {


}
