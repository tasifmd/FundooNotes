package com.bridgelabz.fundoo.exception;

/**
 * Purpose : Notes exception class
 * @author Tasif Mohammed
 *
 */
public class NotesException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	int errorCode;
	public NotesException(String message , int errorCode) {
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
