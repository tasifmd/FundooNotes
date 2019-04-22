package com.bridgelabz.fundoo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.bridgelabz.fundoo.interceptor.FundooInterceptor;

public class InterceptorConfig implements WebMvcConfigurer{
	@Autowired
	FundooInterceptor fundooIntercepto;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(fundooIntercepto);
	}
	
}
