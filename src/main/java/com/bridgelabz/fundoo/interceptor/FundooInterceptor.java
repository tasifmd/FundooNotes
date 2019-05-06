package com.bridgelabz.fundoo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.bridgelabz.fundoo.util.UserToken;

@Component
public class FundooInterceptor extends HandlerInterceptorAdapter{
	
	@Autowired
	private UserToken userToken;
	
	private static Logger logger = LoggerFactory.getLogger(FundooInterceptor.class);
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("Prehandler");
		logger.info("Pre Handler");
		String token = request.getHeader("token");
		long id = userToken.tokenVerify(token);
		request.setAttribute("userId",id);
		return true;
	}


	
}
