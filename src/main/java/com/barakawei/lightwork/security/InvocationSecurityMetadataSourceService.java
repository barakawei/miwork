package com.barakawei.lightwork.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;

import com.barakawei.lightwork.dao.PrivilegeDao;
import com.barakawei.lightwork.domain.Privilege;

@Service
public class InvocationSecurityMetadataSourceService implements
		FilterInvocationSecurityMetadataSource {

	private static final Logger logger = Logger
			.getLogger(InvocationSecurityMetadataSourceService.class);

	@Autowired
	private PrivilegeDao privilegeDao;

	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return true;
	}

	private void loadResourceDefine() {
		if (resourceMap == null) {
			resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
			List<Privilege> privileges = privilegeDao.findAll();
			for (Privilege privilege : privileges) {
				Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();

				ConfigAttribute configAttribute = new SecurityConfig(
						privilege.getName());
				configAttributes.add(configAttribute);
				resourceMap.put(privilege.getUrl(), configAttributes);
			}
		}

	}

	@Override
	public Collection<ConfigAttribute> getAttributes(Object object)
			throws IllegalArgumentException {

		String requestUrl = ((FilterInvocation) object).getRequestUrl();
		logger.info("requestUrl is " + requestUrl);
		if (resourceMap == null) {
			loadResourceDefine();
		}
		return resourceMap.get(requestUrl);
	}

}
