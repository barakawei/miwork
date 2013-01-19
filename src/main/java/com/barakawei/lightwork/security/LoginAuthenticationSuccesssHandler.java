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

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication) throws IOException,
			ServletException {
		
		response.sendRedirect(request.getContextPath()+"/home");
	}
}
