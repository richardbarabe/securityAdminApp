package com.example.shiro.api.web;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.example.security.token.TokenService;

public class TokenFilter extends AuthenticatingFilter implements ApplicationContextAware {

	ApplicationContext context;
	
	@Autowired
	TokenService tokenService;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.context = applicationContext;
	}
	
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest httpReq = (HttpServletRequest) request;
		if(httpReq.getMethod().equals("OPTIONS"))
			return true;
		
		boolean loggedIn = false;
        if (httpReq.getHeader("auth-token") != null) {
            loggedIn = executeLogin(request, response);
        }
        if (!loggedIn) {
        	HttpServletResponse httpRes = (HttpServletResponse)response;
        	httpRes.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        return loggedIn;
    }
	
	@Override
	protected AuthenticationToken createToken(ServletRequest request,
			ServletResponse response) throws Exception {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		String tokenValue = httpRequest.getHeader("auth-token");
		AuthToken token = tokenService.<AuthToken>decrypt(tokenValue);
		return token;
	}
	
	protected boolean isLoginRequest(ServletRequest request,
			ServletResponse response) {
		HttpServletRequest httpReq = (HttpServletRequest) request;
		
		if(httpReq.getHeader("auth-token") != null)
			return true;
		
		return false;
	}

}
