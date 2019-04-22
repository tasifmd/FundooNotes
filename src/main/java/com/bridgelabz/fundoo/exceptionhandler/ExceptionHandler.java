package com.bridgelabz.fundoo.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.bridgelabz.fundoo.exception.EmailException;
import com.bridgelabz.fundoo.exception.LabelException;
import com.bridgelabz.fundoo.exception.LoginException;
import com.bridgelabz.fundoo.exception.NotesException;
import com.bridgelabz.fundoo.exception.RegistrationException;
import com.bridgelabz.fundoo.exception.TokenException;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.util.StatusHelper;

/**
 * Puspose : Exception handler class of fundoo
 * @author : Tasif Mohammed
 *
 */
@RestControllerAdvice
public class ExceptionHandler {
	
	/**
	 * Purpose : Function for EmailException
	 * @param e
	 * @return
	 */
	@org.springframework.web.bind.annotation.ExceptionHandler(value = EmailException.class)
	public ResponseEntity<Response> emailExceptionHandler(EmailException e){
		Response response = StatusHelper.statusInfo(e.getMessage(), e.getErrorCode());
		return new ResponseEntity<Response> (response , HttpStatus.OK);
	}
	
	/**
	 * Purpose : Function for LoginException
	 * @param e
	 * @return
	 */
	@org.springframework.web.bind.annotation.ExceptionHandler(value = LoginException.class)
	public ResponseEntity<Response> loginExceptionHandler(LoginException e){
		Response response = StatusHelper.statusInfo(e.getMessage(), e.getErrorCode());
		return new ResponseEntity<Response> (response , HttpStatus.OK);
	}
	
	/**
	 * Purpose : Function for NotesException
	 * @param e
	 * @return
	 */
	@org.springframework.web.bind.annotation.ExceptionHandler(value = NotesException.class)
	public ResponseEntity<Response> notesExceptionHandler(NotesException e){
		Response response = StatusHelper.statusInfo(e.getMessage(), e.getErrorCode());
		return new ResponseEntity<Response> (response , HttpStatus.OK);
	}
	
	/**
	 * Purpose : Function for RegistrationException
	 * @param e
	 * @return
	 */
	@org.springframework.web.bind.annotation.ExceptionHandler(value = RegistrationException.class)
	public ResponseEntity<Response> registrationExceptionHandler(RegistrationException e){
		Response response = StatusHelper.statusInfo(e.getMessage(), e.getErrorCode());
		return new ResponseEntity<Response> (response , HttpStatus.OK);
	}
	
	/**
	 * Purpose : Function for TokenException
	 * @param e
	 * @return
	 */
	@org.springframework.web.bind.annotation.ExceptionHandler(value = TokenException.class)
	public ResponseEntity<Response> tokenExceptionHandler(TokenException e){
		Response response = StatusHelper.statusInfo(e.getMessage(), e.getErrorCode());
		return new ResponseEntity<Response> (response , HttpStatus.OK);
	}
	
	/**
	 * Purpose : Exception handler function for LabelException
	 * @param e
	 * @return
	 */
	@org.springframework.web.bind.annotation.ExceptionHandler(value = LabelException.class)
	public ResponseEntity<Response> labelExceptionHandler(LabelException e){
		Response response = StatusHelper.statusInfo(e.getMessage(), e.getErrorCode());
		return new ResponseEntity<Response> (response , HttpStatus.OK);
	}
}
