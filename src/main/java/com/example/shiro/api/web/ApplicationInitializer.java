package com.example.shiro.api.web;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.WebApplicationInitializer;

import com.example.shiro.api.web.filter.SimpleCORSFilter;

public class ApplicationInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext)
			throws ServletException {
		FilterRegistration.Dynamic corsFilter = servletContext.addFilter(
				"corsFilter", SimpleCORSFilter.class);
		corsFilter.addMappingForUrlPatterns(null, false, "/*");
	}

}
