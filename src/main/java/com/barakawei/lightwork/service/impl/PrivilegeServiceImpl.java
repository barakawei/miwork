package com.barakawei.lightwork.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.barakawei.lightwork.dao.PrivilegeDao;
import com.barakawei.lightwork.domain.Privilege;
import com.barakawei.lightwork.service.PrivilegeService;

/**
 * @author qianxiaowei
 * @date 2012-11-25
 */
@Service("privilegeService")
public class PrivilegeServiceImpl implements PrivilegeService {

	@Autowired
	private PrivilegeDao privilegeDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fnst.cspif.iloan.service.PrivilegeService#savePrivilege(com.fnst.
	 * cspif.iloan.domain.Privilege)
	 */
	@Override
	public void savePrivilege(Privilege privilege) {
		privilegeDao.save(privilege);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fnst.cspif.iloan.service.PrivilegeService#findPrivilegeById(java.
	 * lang.Integer)
	 */
	@Override
	public Privilege findPrivilegeById(String id) {

		return privilegeDao.findOne(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fnst.cspif.iloan.service.PrivilegeService#updatePrivilege(com.fnst
	 * .cspif.iloan.domain.Privilege)
	 */
	@Override
	public void updatePrivilege(Privilege privilege) {
		privilegeDao.save(privilege);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fnst.cspif.iloan.service.PrivilegeService#deletePrivilegeById(java
	 * .lang.Integer)
	 */
	@Override
	public void deletePrivilegeById(String id) {
		privilegeDao.delete(id);

	}

}
