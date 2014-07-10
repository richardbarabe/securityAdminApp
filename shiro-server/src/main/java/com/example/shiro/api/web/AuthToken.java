package com.example.shiro.api.web;

import org.apache.shiro.authc.AuthenticationToken;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.example.security.token.Token;

@Component
@Scope("prototype")
public class AuthToken implements AuthenticationToken, Token {
	
	private static final long serialVersionUID = 7376782454935451752L;
	
	private String username;
	private long expireTimestamp;
	private String ip;
	private String userAgent;
	
	@Override
	public Object getPrincipal() {
		return this.username;
	}

	@Override
	public Object getCredentials() {
		return username;
	}
	
	public boolean isExpired() {
		return System.currentTimeMillis() > expireTimestamp;
	}
	
	public String getUsername() {
		return username;
	}

	void setUsername(String username) {
		this.username = username;
	}

	public long getExpireTimestamp() {
		return expireTimestamp;
	}

	void setExpireTimestamp(long expireTimestamp) {
		this.expireTimestamp = expireTimestamp;
	}

	public String getIp() {
		return ip;
	}

	void setIp(String ip) {
		this.ip = ip;
	}

	public String getUserAgent() {
		return userAgent;
	}

	void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
