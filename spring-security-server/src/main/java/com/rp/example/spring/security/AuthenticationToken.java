package com.rp.example.spring.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import com.rp.example.security.token.Token;

public class AuthenticationToken extends AbstractAuthenticationToken implements Token  {
	static final long serialVersionUID = -5218811531665865077L;
	String username;
	long expireTimestamp;
	String ip;
	String userAgent;
	String token;
	
	public AuthenticationToken() {
		super(null);
	}
	
	public AuthenticationToken(String token) {
		super(null);
		this.token = token;
	}

	public boolean isExpired() {
		return System.currentTimeMillis() > expireTimestamp;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public long getExpireTimestamp() {
		return expireTimestamp;
	}

	public void setExpireTimestamp(long expireTimestamp) {
		this.expireTimestamp = expireTimestamp;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	@Override
	public Object getCredentials() {
		return token;
	}

	@Override
	public Object getPrincipal() {
		return getUsername();
	}

}