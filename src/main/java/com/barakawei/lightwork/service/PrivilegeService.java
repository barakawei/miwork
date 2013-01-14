package com.barakawei.lightwork.service;

import org.springframework.transaction.annotation.Transactional;

import com.barakawei.lightwork.domain.Privilege;

/**
 * @author qianxiaowei
 * @date 2012-11-25
 */
public interface PrivilegeService {

	@Transactional
	void savePrivilege(Privilege privilege);

	@Transactional
	Privilege findPrivilegeById(String id);

	@Transactional
	void updatePrivilege(Privilege privilege);

	@Transactional
	void deletePrivilegeById(String id);

}
