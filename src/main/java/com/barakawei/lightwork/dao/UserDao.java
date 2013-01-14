package com.barakawei.lightwork.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.barakawei.lightwork.domain.User;

/**
 * @author qianxiaowei
 * @date 2012-11-25
 */
public interface UserDao extends JpaRepository<User, String> {

	public User findByAccount(String account);

	@Modifying
	@Query("update User u set u.password = ?2 where u.account = ?1")
	public void changePassword(String account, String password);

}
