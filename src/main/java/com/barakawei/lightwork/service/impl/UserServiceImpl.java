package com.barakawei.lightwork.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.barakawei.lightwork.domain.SearchForm;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.barakawei.lightwork.dao.RoleDao;
import com.barakawei.lightwork.dao.UserDao;
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



	@Override
	public void createUser(User user, boolean synToActiviti) {
        user.setCreateDate(new Date());
        user.setEnable(true);
		userDao.save(user);
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
}
