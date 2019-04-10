package com.bridgelabz.fundoo.exception;

/**
 * Purpose : Token Exception class
 * @author : Tasif Mohammed
 *
 */
public class TokenException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	int errorCode;
	public TokenException(String message,int errorCode) {
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
