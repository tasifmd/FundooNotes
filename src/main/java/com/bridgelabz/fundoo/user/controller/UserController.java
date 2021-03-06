package com.bridgelabz.fundoo.user.controller;

import java.io.UnsupportedEncodingException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.response.ResponseToken;
import com.bridgelabz.fundoo.user.dto.LoginDTO;
import com.bridgelabz.fundoo.user.dto.PasswordDTO;
import com.bridgelabz.fundoo.user.dto.UserDTO;
import com.bridgelabz.fundoo.user.model.User;
import com.bridgelabz.fundoo.user.service.IUserServices;

/**
 * Puspose : Controller Class for fundoo backend
 * @author : Tasif Mohammed
 * @version : 1.0
 */
@RestController
@CrossOrigin(allowedHeaders = "*" ,origins = "*")
@RequestMapping("/user")
//annotation for set environment file 
@PropertySource("classpath:message.properties")
public class UserController {

	//Creating Logger Object
	static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	//Injecting bean of IUserServices
	@Autowired
	private IUserServices userServices;

	/**
	 * Purpose : Method for registration
	 * @param userDTO 
	 * @param request
	 * @return response
	 */
	@PostMapping("/register")
	public ResponseEntity<Response> register(@Valid @RequestBody UserDTO userDTO)
	{
		logger.info("userDTO data"+userDTO.toString());
		logger.trace("User Registration");

		Response statusResponse = userServices.register(userDTO);

		return new ResponseEntity<Response>(statusResponse, HttpStatus.OK);
	}

	/**
	 * Purpose : Method for Login
	 * @param loginDto
	 * @return response
	 * @throws IllegalArgumentException
	 * @throws UnsupportedEncodingException
	 */
	@PostMapping("/login")
	public ResponseEntity<ResponseToken> login(@Valid @RequestBody LoginDTO loginDto) throws IllegalArgumentException, UnsupportedEncodingException{
		logger.info("LoginDTO data " + loginDto.toString());
		logger.trace("User Login");
		ResponseToken statusResponse = userServices.login(loginDto);
		return new ResponseEntity<ResponseToken>(statusResponse, HttpStatus.OK);
	}
	
	/**
	 * Purpose : Method for validating email
	 * @param token
	 * @return response
	 */
	@GetMapping("/emailvalidation/{token}")
	public ResponseEntity<Response> validateEmail(@PathVariable String token){
		Response statusResponse = userServices.validateEmail(token);
		return new ResponseEntity<Response> (statusResponse, HttpStatus.ACCEPTED);
		
	}
	
	
	/**
	 * Purpose : Method for forgot password
	 * @param email
	 * @param request
	 * @return
	 */
	@PostMapping("/forgotpassword")
	public ResponseEntity<Response> forgotPassword(@RequestBody String email){
		logger.info("User email : " + email);
		Response statusResponse = userServices.forgotPassword(email);
		return new ResponseEntity<Response> (statusResponse, HttpStatus.OK);
	}
	
	/**
	 * Purpose : Method for validating password
	 * @param token
	 * @param newPassword
	 * @return
	 */
	@PutMapping("/forgotpassword/{token}")
	public ResponseEntity<Response> validatePassword(@PathVariable String token, @RequestBody String newPassword){
		Response statusResponse = userServices.setForgettedPasword(token,newPassword);
		return new ResponseEntity<Response> (statusResponse, HttpStatus.ACCEPTED);
		
	}
	
	/**
	 * Method for resetting password
	 * @param passwordDto
	 * @param token
	 * @return
	 */
	@PutMapping("/resetpassword/{token}")
	public ResponseEntity<Response> resetPassword(@Valid @RequestBody PasswordDTO passwordDto,@PathVariable String token){
		Response statusResponse = userServices.resetPassword(passwordDto, token);
		return new ResponseEntity<Response> (statusResponse, HttpStatus.OK);
		
	}
	
	/**
	 * Purpose : Function to get user info
	 * @param email
	 * @return
	 */
	@GetMapping("/userinfo")
	public ResponseEntity<User> getUserInfo(@RequestParam String email) {
		User user = userServices.getUserInfo(email);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@PostMapping("/uploadprofilepic")
	public  ResponseEntity<Response> uploadProfilePic(@RequestHeader String token , @RequestParam MultipartFile file) {
		Response statusResponse = userServices.uploadImage(token, file);
		return new ResponseEntity<Response> (statusResponse, HttpStatus.OK);
	}
	
	@GetMapping("/getuploadedimage/{token}")
	public ResponseEntity<Resource> getProfilePic(@PathVariable String token) {
		Resource resourseStatus = userServices.getUploadedImage(token);
		return new ResponseEntity<Resource> (resourseStatus, HttpStatus.OK);
	}
}
