/**
 * Copyright(c)2012 Beijing PeaceMap Co.,Ltd.
 * All right reserved. 
 */
package com.barakawei.lightwork.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * @description 
 * @author aokunsang
 * @date 2012-8-17
 */
public class LoginAuthenticationSuccesssHandler implements
		AuthenticationSuccessHandler {

	private String defaultUrl;
	
	/* (non-Javadoc)
	 * @see org.springframework.security.web.authentication.AuthenticationSuccessHandler#onAuthenticationSuccess(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.Authentication)
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication) throws IOException,
			ServletException {
		
		System.out.println("----------LoginAuthenticationSuccesssHandler.onAuthenticationSuccess()----------验证完成!!!");
		response.sendRedirect(request.getContextPath()+defaultUrl);
	}
	
	/**
	 * @param defaultUrl the defaultUrl to set
	 */
	public void setDefaultUrl(String defaultUrl) {
		this.defaultUrl = defaultUrl;
	}
}
