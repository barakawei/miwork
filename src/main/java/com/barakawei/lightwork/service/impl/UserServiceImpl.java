package com.barakawei.lightwork.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.barakawei.lightwork.domain.Purchasing;
import com.barakawei.lightwork.domain.SearchForm;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.UserQuery;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.barakawei.lightwork.dao.RoleDao;
import com.barakawei.lightwork.dao.UserDao;
import com.barakawei.lightwork.domain.Role;
import com.barakawei.lightwork.domain.User;
import com.barakawei.lightwork.service.UserService;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * @author qianxiaowei
 * @date 2012-11-25
 */
@Service("userService")
public class UserServiceImpl implements UserService {

	private static Logger logger = LoggerFactory
			.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RoleDao roleDao;

	@Autowired
	private IdentityService identityService;
	
	@Autowired
    private JdbcTemplate jdbcTemplate;

	/**
	 * 使用系统用户对象属性设置到Activiti User对象中
	 * 
	 * @param user
	 *            系统用户对象
	 * @param activitiUser
	 *            Activiti User
	 */
	private void cloneAndSaveActivitiUser(User user,
			org.activiti.engine.identity.User activitiUser) {
		activitiUser.setFirstName(user.getName());
		activitiUser.setLastName(StringUtils.EMPTY);
		activitiUser.setPassword(StringUtils.EMPTY);
		activitiUser.setEmail(user.getEmail());
		identityService.saveUser(activitiUser);
	}

	/**
	 * 添加工作流用户以及角色
	 * 
	 * @param user
	 *            用户对象{@link User}
	 * @param user
	 *            用户拥有的角色ID集合
	 */
	private void newActivitiUser(User user) {
		// 添加用户
		saveActivitiUser(user);
		// 添加membership
		addMembershipToIdentify(user);
	}

	/**
	 * 添加一个用户到Activiti {@link org.activiti.engine.identity.User}
	 * 
	 * @param user
	 *            用户对象, {@link User}
	 */
	private void saveActivitiUser(User user) {
		String userId = user.getId();
		org.activiti.engine.identity.User activitiUser = identityService
				.newUser(userId);
		cloneAndSaveActivitiUser(user, activitiUser);
		logger.debug("add activiti user: {}",
				ToStringBuilder.reflectionToString(activitiUser));
	}

	/**
	 * 添加Activiti Identify的用户于组关系
	 * 
	 * @param user
	 *            角色ID集合
	 *
	 */
	private void addMembershipToIdentify(User user) {
		List<Role> roles = user.getRoles();
		for (Role role : roles) {
			logger.debug("add role to activit: {}", role);
			identityService.createMembership(user.getId(), role.getId());
		}
	}

	@Override
	public void createUser(User user, boolean synToActiviti) {
        user.setCreateDate(new Date());
        user.setEnable(true);
		userDao.save(user);
		// 同步数据到Activiti Identify模块
		if (synToActiviti) {
			newActivitiUser(user);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fnst.cspif.iloan.service.UserService#findUserById(java.lang.Integer)
	 */
	@Override
	public User findUserById(String id) {

		return userDao.findOne(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fnst.cspif.iloan.service.UserService#updateUser(com.fnst.cspif.iloan
	 * .domain.User)
	 */
	@Override
	public void updateUser(User user, boolean synToActiviti) {
		userDao.save(user);
		// 同步数据到Activiti Identify模块
		if (synToActiviti) {
			UserQuery userQuery = identityService.createUserQuery();
			List<org.activiti.engine.identity.User> activitiUsers = userQuery
					.userId(user.getId()).list();
			if (activitiUsers.size() == 1) {
				updateActivitiData(user, activitiUsers.get(0));
			}

		}

	}

	/**
	 * 更新工作流用户以及角色
	 * 
	 * @param user
	 *            用户对象{@link User}
	 * @param user
	 *            用户拥有的角色ID集合
	 * @param activitiUser
	 *            Activiti引擎的用户对象，{@link org.activiti.engine.identity.User}
	 */
	private void updateActivitiData(User user,
			org.activiti.engine.identity.User activitiUser) {

		// 更新用户主体信息
		cloneAndSaveActivitiUser(user, activitiUser);

		// 删除用户的membership
		List<Group> activitiGroups = identityService.createGroupQuery()
				.groupMember(user.getId()).list();
		for (Group group : activitiGroups) {
			logger.debug("delete group from activit: {}",
					ToStringBuilder.reflectionToString(group));
			identityService.deleteMembership(user.getId(), group.getId());
		}

		// 添加membership
		addMembershipToIdentify(user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fnst.cspif.iloan.service.UserService#deleteUserById(java.lang.Integer
	 * )
	 */
	@Override
	public void deleteUserById(String id, boolean synToActiviti) {
		User user = userDao.findOne(id);
		Assert.notNull(user);
		if (synToActiviti) {
			// 同步删除Activiti User
			List<Role> roleList = user.getRoles();
			for (Role role : roleList) {
				identityService.deleteMembership(user.getId(), role.getId());
			}

			// 同步删除Activiti User
			identityService.deleteUser(user.getId());
		}

		// 删除本系统用户
		userDao.delete(id);
	}

	@Override
	public User findUserByAccount(String account) {
		return userDao.findByAccount(account);
	}

	@Override
	public void changePassword(String account, String password) {
		userDao.changePassword(account, password);

	}

	@Override
	public Page<User> findUsers(SearchForm sf) {
        Pageable pageable = new PageRequest(sf.getPageNum(), sf.getNumPerPage());
        return userDao.findAll(getWhereClause(sf), pageable);
	}

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }



    private Specification<User> getWhereClause(final SearchForm sf) {
        return new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                Map<String, String> search = sf.getSearchMap();
                for (String key : search.keySet()) {
                    String value = search.get(key);
                    if (StringUtils.isNotBlank(value)) {
                        predicate.getExpressions().add(cb.like(root.<String>get(key), "%" + value + "%"));

                    }
                }
                query.where(predicate);
                return null;
            }
        };
    }
	
	@Override
	public void synAllUserAndRoleToActiviti() {

		// 清空工作流用户、角色以及关系
		deleteAllActivitiIdentifyData();

		// 复制角色数据
		synRoleToActiviti();

		// 复制用户以及关系数据
		synUserWithRoleToActiviti();
	}

	/**
	 * 复制用户以及关系数据
	 */
	private void synUserWithRoleToActiviti() {
		List<User> allUser = userDao.findAll();
		for (User user : allUser) {
			// 添加一个用户到Activiti
			saveActivitiUser(user);
			// 角色和用户的关系
			List<Role> roleList = user.getRoles();
			for (Role role : roleList) {
				identityService.createMembership(user.getId(), role.getId());
				logger.debug("add membership {user: {}, role: {}}", user.getId(),
						role.getId());
			}
		}
	}

	/**
	 * 同步所有角色数据到{@link Group}
	 */
	private void synRoleToActiviti() {
		List<Role> allRole = roleDao.findAll();
		for (Role role : allRole) {
			Group group = identityService.newGroup(role.getId());
			group.setName(role.getName());
			group.setType(role.getType());
			identityService.saveGroup(group);
		}
	}


	private void deleteAllActivitiIdentifyData() {
		this.deleteAllMemerShip();
		this.deleteAllRole();
		this.deleteAllUser();
	}
	
 
    /**
     * 删除用户和组的关系
     */
    private void deleteAllUser() {
        String sql = "delete from ACT_ID_USER";
        jdbcTemplate.execute(sql);
        logger.debug("deleted from activiti user.");
    }
 
    /**
     * 删除用户和组的关系
     */
    private void deleteAllRole() {
        String sql = "delete from ACT_ID_GROUP";
        jdbcTemplate.execute(sql);
        logger.debug("deleted from activiti group.");
    }
 
    /**
     * 删除用户和组的关系
     */
    private void deleteAllMemerShip() {
        String sql = "delete from ACT_ID_MEMBERSHIP";
        jdbcTemplate.execute(sql);
        logger.debug("deleted from activiti membership.");
    }
	

}
