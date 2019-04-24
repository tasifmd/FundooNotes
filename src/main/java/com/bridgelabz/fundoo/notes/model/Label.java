package com.bridgelabz.fundoo.notes.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "label")
public class Label {
	
	@Id
	@Column(name = "labelId")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long labelId;
	
	@Column(name = "labelName")
	@NotNull(message = "Label name can not be null")
	@NotEmpty(message = "Label name can not be empty")
	private String labelName;
	
	@Column(name = "createdDate")
	private LocalDateTime createdDate;
	
	@Column(name = "modifiedDate")
	private LocalDateTime modifiedDate;
	
//	@Column(name = "noteId")
//	private long noteId;
	
	@ManyToMany(cascade = CascadeType.ALL)
	private List<Note> notes;
	
	@Column(name = "userId")
	private long userId;
	
	public Label() {
		super();
	}

	public long getLabelId() {
		return labelId;
	}

	public void setLabelId(long labelId) {
		this.labelId = labelId;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

//	public long getNoteId() {
//		return noteId;
//	}
//
//	public void setNoteId(long noteId) {
//		this.noteId = noteId;
//	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	@Override
	public String toString() {
		return "Label [labelId=" + labelId + ", labelName=" + labelName + ", createdDate=" + createdDate
				+ ", modifiedDate=" + modifiedDate + ", notes=" + notes + ", userId=" + userId + "]";
	}
	
}
