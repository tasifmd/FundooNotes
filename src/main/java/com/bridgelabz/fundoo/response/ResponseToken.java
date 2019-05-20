package com.bridgelabz.fundoo.response;

/**
 * Purpose : ResponseToken Class for Fundoo Backend
 * @author : Tasif Mohammed
 *
 */
public class ResponseToken {

	private String statusMessage;	
	private int statusCode;
	private String token;
	private String userName;
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
