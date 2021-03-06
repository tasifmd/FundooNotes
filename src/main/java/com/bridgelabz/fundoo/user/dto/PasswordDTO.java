package com.bridgelabz.fundoo.user.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

/**
 * Purpose : DTO class for password resetting
 * @author admin1
 *
 */
public class PasswordDTO {

	@NotEmpty(message="Please provide password")
	@Length(min=6 , max = 50, message = "password must be at least 6 character and max 50 character") 
	@Column(name="password")
	private String newPassword;

	@NotEmpty(message="Please provide password")
	@Length(min=6 , max = 50, message = "password must be at least 6 character and max 50 character") 
	@Column(name="password")
	private String confirmPassword;

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	@Override
	public String toString() {
		return "PasswordDTO [newPassword=" + newPassword + ", confirmPassword=" + confirmPassword + "]";
	}
	
	
	
	
}
