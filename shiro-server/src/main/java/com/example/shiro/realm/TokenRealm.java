package com.example.shiro.realm;

import javax.annotation.PostConstruct;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.realm.jdbc.JdbcRealm;

import com.example.shiro.api.web.AuthToken;

public class TokenRealm extends JdbcRealm {
	
	@PostConstruct
	public void overrideTokenClass() {
		setAuthenticationTokenClass(AuthToken.class);
	}

	@Override
	public boolean supports(AuthenticationToken token) {
		return token != null && token.getClass().isAssignableFrom(AuthToken.class);
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		AuthToken authToken = (AuthToken) token;
		String username = (String) authToken.getPrincipal();
		if(authToken.isExpired())
			throw new AuthenticationException("Expired token.");
		
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username, username, this.getName());
		return info;
		
	}
	
}
