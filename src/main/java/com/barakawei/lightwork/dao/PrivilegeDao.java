package com.barakawei.lightwork.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.barakawei.lightwork.domain.Privilege;

/**
 * @author qianxiaowei
 * @date 2012-11-25
 */
public interface PrivilegeDao extends JpaRepository<Privilege, String> {

}
