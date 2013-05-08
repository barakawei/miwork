package com.barakawei.lightwork.service;

import java.util.List;

import com.barakawei.lightwork.domain.SearchForm;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.transaction.annotation.Transactional;

import com.barakawei.lightwork.domain.User;

/**
 * @author qianxiaowei
 * @date 2012-11-25
 */
@Transactional
public interface UserService {

	// @PreAuthorize("hasAuthority('add')")
	void createUser(User user, boolean synToActiviti);

	User findUserById(String id);

	User findUserByAccount(String account);

	void updateUser(User user, boolean synToActiviti);

	void deleteUserById(String id, boolean synToActiviti);

	// @PreAuthorize("#account == principal.username and hasAuthority('changePassword')")
	void changePassword(String account, String password);

	// @PreFilter("filterObject.account == principal.username")
	Page<User> findUsers(SearchForm sf);

    List<User> findAll();

}
