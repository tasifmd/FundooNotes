package com.bridgelabz.fundoo.user.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.bridgelabz.fundoo.notes.model.Label;
import com.bridgelabz.fundoo.notes.model.Note;
import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * Purpose : Pojo class for user
 * @author : Tasif Moahammed
 * @version : 1.0
 */
@Entity
@Table(name = "user")
public class User{

	@Id
	@Column(name = "userId")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long userId;

	@Column(name = "name")
	@NotEmpty(message = "please provide your name")
	@NotNull(message = "please provide your name")
	private String name;

	@Column(name = "email",unique = true)
	@Email(regexp =  "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.(?:[A-Z]{2,}|com|org))+$")
	@NotEmpty(message = "Please provide valid email")
	@NotNull(message = "Please provide valid email")
	private String email;

	@NotEmpty(message = "Please provide password")
	@Column(name = "password")
	@NotNull(message = "Please provide password")
	private String password;
	
	@Column(name = "mobileNumber")
	@Pattern(regexp = "[0-9]{10}" , message = "provide valid mobile number")
	@NotEmpty(message = "please provide your mobile number")
	@NotNull(message = "please provide your mobile number")
	private String mobileNumber;
	
	@Column(name = "isVerified")
	boolean isVarified;
	
	@Column(name = "registeredDate")
	private LocalDate registeredDate;

	@Column(name = "updatedDate")
	private LocalDate updatedDate;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Note> notes;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Label> label;
	
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL)
	private Set<Note> collaboratedNotes;
	
	public User() {
		super();
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public boolean isVarified() {
		return isVarified;
	}

	public void setVarified(boolean isVarified) {
		this.isVarified = isVarified;
	}

	public LocalDate getRegisteredDate() {
		return registeredDate;
	}

	public void setRegisteredDate(LocalDate registeredDate) {
		this.registeredDate = registeredDate;
	}

	public LocalDate getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDate updatedDate) {
		this.updatedDate = updatedDate;
	}

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	public List<Label> getLabel() {
		return label;
	}

	public void setLabel(List<Label> label) {
		this.label = label;
	}

	public Set<Note> getCollaboratedNotes() {
		return collaboratedNotes;
	}

	public void setCollaboratedNotes(Set<Note> collaboratedNotes) {
		this.collaboratedNotes = collaboratedNotes;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", name=" + name + ", email=" + email + ", password=" + password
				+ ", mobileNumber=" + mobileNumber + ", isVarified=" + isVarified + ", registeredDate=" + registeredDate
				+ ", updatedDate=" + updatedDate + ", notes=" + notes + ", label=" + label + ", collaboratedNotes="
				+ collaboratedNotes + "]";
	}

	
	
	
}
