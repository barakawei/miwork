package com.barakawei.lightwork.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.barakawei.lightwork.domain.Role;

/**
 * @author qianxiaowei
 * @date 2012-11-25
 */
public interface RoleDao extends JpaRepository<Role, String> {

}
