package com.rp.example.spring.security;

import java.io.IOException;
import java.text.MessageFormat;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.rp.example.security.token.TokenService;

public class TokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	private static final String HEADER_SECURITY_TOKEN = "auth-token";

	TokenService tokenService;
	
	protected TokenAuthenticationFilter() {
		super(new TokenRequestMatcher());
		AuthenticationSuccessHandler successHandler = new AuthenticationSuccessHandler() {
			@Override
			public void onAuthenticationSuccess(HttpServletRequest request,
					HttpServletResponse response, Authentication authentication)
					throws IOException, ServletException {
			}
		};
		super.setAuthenticationSuccessHandler(successHandler);
	}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        if (!requiresAuthentication(request, response)) {
            chain.doFilter(request, response);

            return;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Request is to process authentication");
        }

        Authentication authResult;

        try {
            authResult = attemptAuthentication(request, response);
            if (authResult == null) {
                // return immediately as subclass has indicated that it hasn't completed authentication
                return;
            }
        } catch(InternalAuthenticationServiceException failed) {
            logger.error("An internal error occurred while trying to authenticate the user.", failed);
            unsuccessfulAuthentication(request, response, failed);

            return;
        }
        catch (AuthenticationException failed) {
            // Authentication failed
            unsuccessfulAuthentication(request, response, failed);

            return;
        }

        successfulAuthentication(request, response, chain, authResult);
        chain.doFilter(request, response);
	}



	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException,
			IOException, ServletException {
		String token = request.getHeader(HEADER_SECURITY_TOKEN);
        logger.info("token found:"+token);
        AbstractAuthenticationToken userAuthenticationToken = authUserByToken(token);
        if(userAuthenticationToken == null) 
        	throw new AuthenticationServiceException(MessageFormat.format("Error | {0}", "Bad Token"));
        return this.getAuthenticationManager().authenticate(userAuthenticationToken);
	}
	
	private AbstractAuthenticationToken authUserByToken(String token) {
		if(token==null) {
            return null;
        }
        AbstractAuthenticationToken authToken = new AuthenticationToken(token);
        return authToken;
	}
	
	public void setTokenService(TokenService tokenService) {
		this.tokenService = tokenService;
	}
	
	
	static class TokenRequestMatcher implements RequestMatcher {

		@Override
		public boolean matches(HttpServletRequest request) {
			return request.getHeader(HEADER_SECURITY_TOKEN) != null;
		}
		
	}
	
}
