package com.barakawei.lightwork.util;

import java.util.List;

import javax.annotation.PostConstruct;

import com.barakawei.lightwork.domain.Role;
import com.barakawei.lightwork.domain.User;
import com.barakawei.lightwork.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component("userContextUtil")
public class UserContextUtil {

	@Autowired
	private UserService userService;

	private static UserService us;

	@PostConstruct
	public void init() {
		us = this.userService;

	}

	public static UserDetails getCurrentUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null){
            return null;
        }
		return (UserDetails)authentication.getPrincipal();
	}

	public static User getCurrentUser() {
		UserDetails ud = getCurrentUserDetails();
		Assert.notNull(ud, "error_data_null");
		User user = us.findUserByAccount(ud.getUsername());
		Assert.notNull(user, "error_data_null");
		return user;

	}

	public static Role getCurrentRole() {
		User user = getCurrentUser();
		Assert.notNull(user, "error_data_null");
		List<Role> roles = user.getRoles();
		Assert.isTrue(null!=roles&&!roles.isEmpty(), "error_data_null");
		return roles.iterator().next();

	}
	
	public static boolean isCurrentUser(User user){
		Assert.notNull(user, "error_data_null");
		return user.getId().equals(getCurrentUser().getId());
	}

}
