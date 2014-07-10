package com.rp.example.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.rp.example.security.token.TokenService;

public class TokenAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	TokenService tokenService;
	
	private UserDetailsService userDetailsService;
	
	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		String encryptedAndEncodedToken = (String)authentication.getCredentials();
		AuthenticationToken token = tokenService.<AuthenticationToken>decrypt(encryptedAndEncodedToken);
		if(!token.isExpired()) {
			token.setAuthenticated(true);
			
			String username = token.getUsername();
			UserDetails details = userDetailsService.loadUserByUsername(username);
			
			UsernamePasswordAuthenticationToken confirmedToken = new UsernamePasswordAuthenticationToken(details.getUsername(), details.getPassword(), details.getAuthorities());
			
			return confirmedToken;
		}
		
		return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return AuthenticationToken.class.isAssignableFrom(authentication);
	}

	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

}
