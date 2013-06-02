package com.barakawei.lightwork.dao;

import com.barakawei.lightwork.domain.Zipper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author qianxiaowei
 * @date 2012-11-25
 */
public interface ZipperDao extends JpaRepository<Zipper, String> {

    @Modifying
    @Transactional
    @Query("delete from Zipper z where z.purchasing.id = ?1")
    public void deleteByPurchasing(String id);


}
