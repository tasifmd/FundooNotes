package com.bridgelabz.fundoo.user.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.response.ResponseToken;
import com.bridgelabz.fundoo.user.dto.LoginDTO;
import com.bridgelabz.fundoo.user.dto.PasswordDTO;
import com.bridgelabz.fundoo.user.dto.UserDTO;
import com.bridgelabz.fundoo.user.model.User;

/**
 * Purpose : Creating IUserServices interface to add services to FundooBackend
 * @author : Tasif Moahmmed
 *
 */
@Service
public interface IUserServices {
	/**
	 * Purpose : Method to register
	 * @param userDto
	 * @param request
	 * @return
	 */
	public Response register(UserDTO userDto); 
	/**
	 * Purpose : Method to login
	 * @param loginDto
	 * @return
	 * @throws IllegalArgumentException
	 * @throws UnsupportedEncodingException
	 */
	public ResponseToken login(LoginDTO loginDto) throws IllegalArgumentException, UnsupportedEncodingException;
	/**
	 * Purpose : Method to validate email
	 * @param token
	 * @return
	 */
	public Response validateEmail(String token);
	
	/**
	 * Purpose : Method for forgot password
	 * @param email
	 * @return
	 */
	public Response forgotPassword(String email);
	
	/**
	 * Purpose : Setting forgot password
	 * @param token
	 * @param newPassword
	 * @return
	 */
	public Response setForgettedPasword(String token , String newPassword);
	
	/**
	 * Purpose : Resetting Password
	 * @param passwordDto
	 * @param token
	 * @return
	 */
	public Response resetPassword(PasswordDTO passwordDto , String token);
	
	public List<User> getUserInfo(String email);
}
