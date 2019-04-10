package com.bridgelabz.fundoo.applicationconfig;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.util.GenerateEmail;

/**
 * Purpose : Configuration Class for Fundoo Backend
 * @author : Tasif Mohammed
 * @version : 1.0
 */
@Configuration
public class ApplicationConfiguration {

	/**
	 * Purpose : Creating bean object for PasswordEncoder
	 * @return : Return the bean object
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * Purpose : Creating bean object for ModelMapper
	 * @return : Return the bean object
	 */
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	/**
	 * Purpose : Creating bean object for Response
	 * @return : Return the bean object
	 */
	@Bean
	public Response getResponse() {
		return new Response();
	}
	
	@Bean
	public GenerateEmail getGenerateEmail() {
		return new GenerateEmail();
	}
	
}
