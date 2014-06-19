package com.example.shiro.api.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.security.token.TokenService;

@Controller
public class JsonLogin implements ApplicationContextAware {
	public static long TOKEN_TIMEOUT_MILLIS = 1000*60*60*24;
	
	ApplicationContext springContext;
	
	@Autowired
	TokenService tokenService;
	
	@RequestMapping(value="/api/v1/security/login", method=RequestMethod.POST)
	@ResponseBody
	public Response login(@RequestBody Credentials credentials, HttpServletRequest request) {
		Subject subject = SecurityUtils.getSubject();
		subject.login(new UsernamePasswordToken(credentials.username, credentials.password, false));
		AuthToken tokenObject = new AuthToken();
		tokenObject.setUsername(credentials.username);
		tokenObject.setExpireTimestamp(System.currentTimeMillis()+TOKEN_TIMEOUT_MILLIS);
		tokenObject.setIp(request.getRemoteAddr());
		tokenObject.setUserAgent(request.getHeader("user-agent"));
		Response res = new Response();
		res.id = "1";
		res.userid = subject.getPrincipal().toString();
		res.role = "SYSTEM_ADMIN";
		res.token = tokenService.encrypt(tokenObject);
		return res;
	}
	
	class Response {
		public String id;
		public String userid;
		public String role;
		public String token;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.springContext = applicationContext;
		
	}
	
}
