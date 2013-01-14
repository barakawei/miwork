package com.barakawei.lightwork.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.barakawei.lightwork.dao.UserDao;
import com.barakawei.lightwork.domain.Privilege;
import com.barakawei.lightwork.domain.Role;
import com.barakawei.lightwork.domain.User;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

	private static final Logger logger = Logger
			.getLogger(UserDetailServiceImpl.class);

	@Autowired
	private UserDao userDao;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String account)
			throws UsernameNotFoundException {
		logger.info("account is " + account);
		User user = userDao.findByAccount(account);
		if (user == null) {
			throw new UsernameNotFoundException(account);
		}
		Collection<GrantedAuthority> grantedAuths = obtionGrantedAuthorities(user);

		boolean enables = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;

		org.springframework.security.core.userdetails.User userdetails = new org.springframework.security.core.userdetails.User(
				user.getAccount(), user.getPassword(), enables,
				accountNonExpired, credentialsNonExpired, accountNonLocked,
				grantedAuths);
		return userdetails;
	}

	@Transactional
	private Set<GrantedAuthority> obtionGrantedAuthorities(User user) {
		Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();
		List<Role> roles = user.getRoles();
		logger.info(roles.size());
		for (Role role : roles) {
			List<Privilege> privileges = role.getPrivileges();
			for (Privilege res : privileges) {
				authSet.add(new SimpleGrantedAuthority(res.getName()));
			}
		}
		return authSet;
	}
}
