package com.bridgelabz.fundoo.exception;

/**
 * Purpose : Email exception class
 * @author : Tasff Mohammed
 *
 */
public class EmailException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	int errorCode;
	public EmailException(String message,int errorCode) {
		super(message);
		this.errorCode = errorCode;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
}
