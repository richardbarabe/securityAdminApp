package com.rp.example.spring.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.rp.example.security.token.TokenService;

@Configuration
@EnableWebSecurity(debug=true)
@ComponentScan("com.rp.example.security.token")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private DataSource securityDatasource;
	
	@Autowired
	private TokenService tokenService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.authenticationProvider(tokenAuthenticationProvider());
	}
	
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.headers().disable();
		http.addFilterAfter(tokenAuthenticationFilter(),
							BasicAuthenticationFilter.class);
		http
			.authorizeRequests()
				.antMatchers(HttpMethod.OPTIONS, "/api/**").permitAll()
				.antMatchers("/api/v1/security/login").permitAll()
				.antMatchers(HttpMethod.GET, "/api/v1/security/users").hasRole("SYSTEM_ADMIN")
				.antMatchers(HttpMethod.POST, "/api/v1/security/users").hasRole("SYSTEM_ADMIN")
				.anyRequest().authenticated();
		http.exceptionHandling()
				.authenticationEntryPoint(new Http403ForbiddenEntryPoint());
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	@Bean
	public TokenAuthenticationFilter tokenAuthenticationFilter() throws Exception {
		TokenAuthenticationFilter tokenFilter = new TokenAuthenticationFilter();
		tokenFilter.setTokenService(tokenService);
		tokenFilter.setAuthenticationManager(this.authenticationManager());
		
		return tokenFilter;
	}
	
	@Bean
	public TokenAuthenticationProvider tokenAuthenticationProvider() {
		TokenAuthenticationProvider provider = new TokenAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService());
		return provider;
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		JdbcDaoImpl service = new JdbcDaoImpl();
		service.setDataSource(securityDatasource);
		service.setAuthoritiesByUsernameQuery("select username, role from user_roles where username =?");
		service.setUsersByUsernameQuery("select username,password, enabled from users where username=?");
		return service;
	}
	
}
