package com.bridgelabz.fundoo.user.service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.exception.EmailException;
import com.bridgelabz.fundoo.exception.LoginException;
import com.bridgelabz.fundoo.exception.RegistrationException;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.response.ResponseToken;
import com.bridgelabz.fundoo.user.dto.LoginDTO;
import com.bridgelabz.fundoo.user.dto.PasswordDTO;
import com.bridgelabz.fundoo.user.dto.UserDTO;
import com.bridgelabz.fundoo.user.model.Email;
import com.bridgelabz.fundoo.user.model.User;
import com.bridgelabz.fundoo.user.repository.IUserRepository;
import com.bridgelabz.fundoo.util.GenerateEmail;
import com.bridgelabz.fundoo.util.StatusHelper;
import com.bridgelabz.fundoo.util.UserToken;

/**
 * Purpose : Implementation class for IUserService
 * @author : Tasif Mohammed
 *
 */
@Service("userService")
@PropertySource("classpath:message.properties")
public class UserServicesImplementation implements IUserServices {

	static final Logger log = LoggerFactory.getLogger(UserServicesImplementation.class);
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private MailService mailServise;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserToken userToken;
	
	@Autowired
	private GenerateEmail generateEmail;
	
	
	/* (non-Javadoc)
	 * @see com.bridgelabz.fundoo.user.service.IUserServices#register(com.bridgelabz.fundoo.user.dto.UserDTO, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public Response register(UserDTO userDTO) {

		Email email = new Email();
		Response response = null;
		log.info(userDTO.toString());

		//getting user record by email
		java.util.Optional<User> avaiability = userRepository.findByEmail(userDTO.getEmail());

		//Checking whether the user is existing or not  
		if(avaiability.isPresent()){
			throw new RegistrationException("User exist", -2);
		}

		//encrypting password by using BCrypt encoder
		userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		User user = modelMapper.map(userDTO, User.class);//storing value of one model into another

		user.setRegisteredDate(LocalDate.now());

		User saveResponse = userRepository.save(user);

		//Checking whether data is stored successfully in database
		if(saveResponse==null)
		{
			throw new RegistrationException("Data is not saved in database", -2);
//			response = StatusHelper.statusInfo(environment.getProperty("status.register.emailExistError"),Integer.parseInt(environment.getProperty("status.register.errorCode")));
//			return response;
		}
		
		log.info(saveResponse.toString());
		System.out.println(user.getUserId());
		email.setFrom("fundootasif@gmail.com");
		email.setTo(userDTO.getEmail());
		email.setSubject("Email Verification ");
		try {
			email.setBody( mailServise.getLink("http://localhost:8080/user/emailvalidation/",user.getUserId()));
		} catch (IllegalArgumentException | UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		generateEmail.send(email);

		response = StatusHelper.statusInfo(environment.getProperty("status.register.success"),Integer.parseInt(environment.getProperty("status.success.code")));
		return response;
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.fundoo.user.service.IUserServices#login(com.bridgelabz.fundoo.user.dto.LoginDTO)
	 */
	
	@Override
	public ResponseToken login(LoginDTO loginDto) {
		ResponseToken response = null;
		//getting user record by email
		java.util.Optional<User> user = userRepository.findByEmail(loginDto.getEmail());
		log.info("User Password : " + user.get().getPassword());
		//Checking whether user is registered
		if(user.isPresent()) {
			
			//Checking whether user is verified
			if(user.get().isVarified() == true) {
				if(passwordEncoder.matches(loginDto.getPassword(), user.get().getPassword())) {
					String generatedToken = userToken.generateToken(user.get().getUserId());
					response = StatusHelper.tokenStatusInfo(environment.getProperty("status.login.success"),Integer.parseInt(environment.getProperty("status.success.code")),generatedToken);
					return response;
				}else {
					throw new LoginException("Invalid Password ", -3);
				}
			}else {
				throw new LoginException("Email is not verified ", -3);
			}
		}
		else {
			throw new LoginException("Invalid EmailId", -3);
		}
		
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.fundoo.user.service.IUserServices#validateEmail(java.lang.String)
	 */
	
	@Override
	public Response validateEmail(String token) {
		Response response = null;
		long id = userToken.tokenVerify(token);
		Optional<User> user = userRepository.findById(id).map(this::verify);
		if(user.isPresent()) {
			response = StatusHelper.statusInfo(environment.getProperty("status.email.verified"),Integer.parseInt(environment.getProperty("status.success.code")));
			return response;
		}
		else {
			throw new LoginException("EmailId is not verified", -3);
		}
	}
	
	/**
	 * Method to verify user
	 * @param user
	 * @return
	 */
	private User verify(User user) {
		log.info("User : " + user);
		user.setVarified(true);
		user.setUpdatedDate(LocalDate.now());
		log.info("User : "+user);
		return userRepository.save(user);
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.fundoo.user.service.IUserServices#forgotPassword(java.lang.String, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public Response forgotPassword(String email ) {
		Email emailObj = new Email();
		Response response = null;
		log.info("Email of user is :" + email);
		Optional<User> user = userRepository.findByEmail(email);
		if(!user.isPresent()) {
			throw new EmailException("No user exist ", -4);
		}
		
		emailObj.setFrom("fundootasif@gmail.com");
		emailObj.setTo(email);
		emailObj.setSubject("Forgot Password ");
		try {
			emailObj.setBody( mailServise.getLink("http://localhost:4200/user/resetpassword/",user.get().getUserId()));
		} catch (IllegalArgumentException | UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		generateEmail.send(emailObj);
		
		response = StatusHelper.statusInfo(environment.getProperty("status.forgot.emailSent"),Integer.parseInt(environment.getProperty("status.success.code")));
		return response;
	}

	
	/* (non-Javadoc)
	 * @see com.bridgelabz.fundoo.user.service.IUserServices#setForgettedPasword(java.lang.String, java.lang.String)
	 */
	public Response setForgettedPasword(String token , String newPassword) {
		Response response = null;
		long id = userToken.tokenVerify(token);
		Optional<User> user = userRepository.findById(id);
		user.get().setPassword(passwordEncoder.encode(newPassword));
			
		userRepository.save(user.get());
		log.info("Password Changed");
		
		response = StatusHelper.statusInfo(environment.getProperty("status.resetPassword.success"),Integer.parseInt(environment.getProperty("status.success.code")));
		return response;
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.fundoo.user.service.IUserServices#resetPassword(com.bridgelabz.fundoo.user.dto.PasswordDTO, java.lang.String)
	 */
	@Override
	public Response resetPassword(PasswordDTO passwordDto, String token) {
		Response response = null;
		long id = userToken.tokenVerify(token);
		Optional<User> user = userRepository.findById(id);
		if(passwordDto.getNewPassword().equals(passwordDto.getConfirmPassword())) {
			user.get().setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
			userRepository.save(user.get());
			log.info("Password Reset Successfully");
			response = StatusHelper.statusInfo(environment.getProperty("status.resetPassword.success"),Integer.parseInt(environment.getProperty("status.success.code")));
			return response;
		}
//		if(passwordEncoder.matches(passwordDto.getOldPassword(), user.get().getPassword())) {
//			user.get().setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
//			userRepository.save(user.get());
//			log.info("Password Reset Successfully");
//			response = StatusHelper.statusInfo(environment.getProperty("status.resetPassword.success"),Integer.parseInt(environment.getProperty("status.success.code")));
//			return response;
//		}
		response = StatusHelper.statusInfo(environment.getProperty("status.passreset.failed"),Integer.parseInt(environment.getProperty("status.login.errorCode")));
		return response;
	}
	


}
