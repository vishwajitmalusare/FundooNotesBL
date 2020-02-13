package com.bridgelabz.fundooApp.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Document
public class Note {

	@Id
	private String noteId;
	private String title;
	private String description;
	private String color;
	private LocalDateTime creationtTime;
	private LocalDateTime updateTime;
	private String userId;
	private boolean isTrash;
	private boolean isArchive;
	private boolean isPin;
	private LocalDateTime reminder;

	@JsonIgnore
	@DBRef(lazy=true)
	private List<Label> labels;
	
	@JsonIgnoreProperties
	@DBRef(lazy = true)
	private List<User> collaboratedUsers;
	

	public Note(String noteId, String title, String description, String color, LocalDateTime creationtTime,
			LocalDateTime updateTime, String userId, boolean isTrash, boolean isArchive, boolean isPin,
			LocalDateTime reminder, List<Label> labels, List<User> collaboratedUsers) {
		this.noteId = noteId;
		this.title = title;
		this.description = description;
		this.color = color;
		this.creationtTime = creationtTime;
		this.updateTime = updateTime;
		this.userId = userId;
		this.isTrash = isTrash;
		this.isArchive = isArchive;
		this.isPin = isPin;
		this.reminder = reminder;
		this.labels = labels;
		this.collaboratedUsers = collaboratedUsers;
	}

	public List<User> getCollaboratedUsers() {
		return collaboratedUsers;
	}

	public void setCollaboratedUsers(List<User> collaboratedUsers) {
		this.collaboratedUsers = collaboratedUsers;
	}

	public Note() {

	}

	public String getNoteId() {
		return noteId;
	}

	public void setNoteId(String noteId) {
		this.noteId = noteId;
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

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public LocalDateTime getCreationtTime() {
		return creationtTime;
	}

	public void setCreationtTime(LocalDateTime creationtTime) {
		this.creationtTime = creationtTime;
	}

	public LocalDateTime getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(LocalDateTime updateTime) {
		this.updateTime = updateTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public boolean isTrash() {
		return isTrash;
	}

	public void setTrash(boolean isTrash) {
		this.isTrash = isTrash;
	}

	public boolean isArchive() {
		return isArchive;
	}

	public void setArchive(boolean isArchive) {
		this.isArchive = isArchive;
	}

	public boolean isPin() {
		return isPin;
	}

	public void setPin(boolean isPin) {
		this.isPin = isPin;
	}

	public List<Label> getLabels() {
		return labels;
	}

	public void setLabels(List<Label> labels) {
		this.labels = labels;
	}

	public LocalDateTime getReminder() {
		return reminder;
	}

	public void setReminder(LocalDateTime reminder) {
		this.reminder = reminder;
	}

	@Override
	public String toString() {
		return "Note [noteId=" + noteId + ", title=" + title + ", description=" + description + ", color=" + color
				+ ", creationtTime=" + creationtTime + ", updateTime=" + updateTime + ", userId=" + userId
				+ ", isTrash=" + isTrash + ", isArchive=" + isArchive + ", isPin=" + isPin + ", reminder=" + reminder
				+ ", labels=" + labels + ", collaboratedUsers=" + collaboratedUsers + "]";
	}



}
