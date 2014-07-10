package com.rp.example.spring.config;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.rp.example.api.UserService;
import com.rp.example.api.UserServiceImpl;

@Configuration
@Import(CryptoUtilsConfig.class)
@PropertySource("classpath:database-config.properties")
public class AppConfig {
	private static String PROPERTY_NAME_DATABASE_DRIVER="db.driver";
	private static String PROPERTY_NAME_DATABASE_USERNAME="db.username";
	private static String PROPERTY_NAME_DATABASE_PASSWORD="db.password";
	private static String PROPERTY_NAME_DATABASE_URL="db.url";
	
	@Resource
	Environment env;
	
	@Bean
	public UserService userService() {
		return new UserServiceImpl();
	}
	
	@Bean
	public DataSource securityDatasource() {
		BasicDataSource datasource = new BasicDataSource();
		datasource.setDriverClassName(env.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));
		datasource.setUsername(env.getRequiredProperty(PROPERTY_NAME_DATABASE_USERNAME));
		datasource.setPassword(env.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD));
		datasource.setUrl(env.getRequiredProperty(PROPERTY_NAME_DATABASE_URL));
		return datasource;
	}
	
}
