package com.bridgelabz.fundoo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.bridgelabz.fundoo.interceptor.FundooInterceptor;

@SuppressWarnings("deprecation")
public class InterceptorConfig extends WebMvcConfigurerAdapter {
	@Autowired
	FundooInterceptor fundooInterceptor;
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(fundooInterceptor);
	}
}
	