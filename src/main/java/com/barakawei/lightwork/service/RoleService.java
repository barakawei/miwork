package com.barakawei.lightwork.service;

import org.springframework.transaction.annotation.Transactional;

import com.barakawei.lightwork.domain.Role;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
/**
 * @author qianxiaowei
 * @date 2012-11-25
 */
public interface RoleService {

	@Transactional
	void saveRole(Role role);

	@Transactional
	Role findRoleById(String id);

	@Transactional
	void updateRole(Role role);

	@Transactional
	void deleteRoleById(String id);

	@Transactional
	Page<Role> findRoles(Pageable pageable);

}
