package com.bridgelabz.fundoo.notes.model;

import java.io.Serializable;
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

@Entity
@Table(name = "notes")
public class Note implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "noteId")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "userId")
	private long userId;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "isPin")
	private boolean isPin;
	
	@Column(name = "isArchive")
	private boolean isArchive;
	
	@Column(name = "isTrash")
	private boolean isTrash;
	
	@Column(name = "created")
	private LocalDateTime created;
	
	@Column(name = "modified")
	private LocalDateTime modified;
	
	@ManyToMany(cascade = CascadeType.ALL)
	private List<Label> listLabel;
	
	public Note() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isPin() {
		return isPin;
	}

	public void setPin(boolean isPin) {
		this.isPin = isPin;
	}

	public boolean isArchive() {
		return isArchive;
	}

	public void setArchive(boolean isArchive) {
		this.isArchive = isArchive;
	}

	public boolean isTrash() {
		return isTrash;
	}

	public void setTrash(boolean isTrash) {
		this.isTrash = isTrash;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public LocalDateTime getModified() {
		return modified;
	}

	public void setModified(LocalDateTime modified) {
		this.modified = modified;
	}

	public List<Label> getListLabel() {
		return listLabel;
	}

	public void setListLabel(List<Label> listLabel) {
		this.listLabel = listLabel;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Note [id=" + id + ", userId=" + userId + ", title=" + title + ", description=" + description
				+ ", isPin=" + isPin + ", isArchive=" + isArchive + ", isTrash=" + isTrash + ", created=" + created
				+ ", modified=" + modified + ", listLabel=" + listLabel + "]";
	}
	
	
}
