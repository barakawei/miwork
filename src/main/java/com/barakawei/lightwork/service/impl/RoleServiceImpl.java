package com.barakawei.lightwork.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.barakawei.lightwork.dao.RoleDao;
import com.barakawei.lightwork.domain.Role;
import com.barakawei.lightwork.service.RoleService;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

/**
 * @author qianxiaowei
 * @date 2012-11-25
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;

	/*
	 * (non-JavadocRR
	 * 
	 * @see
	 * com.fnst.cspif.iloan.service.RoleService#saveRole(com.fnst.cspif.iloan
	 * .domain.Role)
	 */
	@Override
	public void saveRole(Role role) {
		roleDao.save(role);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fnst.cspif.iloan.service.RoleService#findRoleById(java.lang.Integer)
	 */
	@Override
	public Role findRoleById(String id) {

		return roleDao.findOne(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fnst.cspif.iloan.service.RoleService#updateRole(com.fnst.cspif.iloan
	 * .domain.Role)
	 */
	@Override
	public void updateRole(Role role) {
		roleDao.save(role);

	}

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    /*
         * (non-Javadoc)
         *
         * @see
         * com.fnst.cspif.iloan.service.RoleService#deleteRoleById(java.lang.Integer
         * )
         */
	@Override
	public void deleteRoleById(String id) {
		roleDao.delete(id);

	}

	@Override
	public Page<Role> findRoles(Pageable pageable) {
		return roleDao.findAll(pageable);
	}

}
