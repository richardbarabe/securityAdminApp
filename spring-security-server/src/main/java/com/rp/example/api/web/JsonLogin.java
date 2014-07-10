package com.rp.example.api.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rp.example.security.token.Token;
import com.rp.example.security.token.TokenService;
import com.rp.example.spring.security.AuthenticationToken;

@Controller
public class JsonLogin {
	public static long TOKEN_TIMEOUT_MILLIS = 1000*60*60*24;
	
	@Autowired
	TokenService tokenService;
	
	@RequestMapping(value="/api/v1/security/login", method=RequestMethod.POST)
	@ResponseBody
	public Response login(@RequestBody Credentials credentials, HttpServletRequest request) {
//		AuthToken tokenObject = new AuthToken();
//		tokenObject.setUsername(credentials.username);
//		tokenObject.setExpireTimestamp(System.currentTimeMillis()+TOKEN_TIMEOUT_MILLIS);
//		tokenObject.setIp(request.getRemoteAddr());
//		tokenObject.setUserAgent(request.getHeader("user-agent"));
		
		AuthenticationToken tokenObject = new AuthenticationToken();
		tokenObject.setUsername(credentials.username);
		tokenObject.setExpireTimestamp(System.currentTimeMillis()+TOKEN_TIMEOUT_MILLIS);
		tokenObject.setIp(request.getRemoteAddr());
		tokenObject.setUserAgent(request.getHeader("user-agent"));
		
		
		Response res = new Response();
		res.id = "1";
		res.userid = tokenObject.getUsername();
		res.role = "ROLE_SYSTEM_ADMIN";
		res.token = tokenService.encrypt(tokenObject);
		return res;
	}
	
	class Response {
		public String id;
		public String userid;
		public String role;
		public String token;
	}

	
}
