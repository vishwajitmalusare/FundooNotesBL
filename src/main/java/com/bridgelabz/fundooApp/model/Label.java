package com.bridgelabz.fundooApp.model;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.io.filefilter.TrueFileFilter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document
public class Label {

	@Id
	private String labelId;
	private String labelName;
	private LocalDateTime creationTime;
	private LocalDateTime updateTime;
	private String userId;
	
	@JsonIgnore
	@DBRef(lazy = true)
	private List<Note> noteList;

	public Label() {
	}

	/**
	 * @param labelId
	 * @param labelName
	 * @param creationTime
	 * @param updateTime
	 * @param userId
	 */
	
	public Label(String labelId, String labelName, LocalDateTime creationTime, LocalDateTime updateTime, String userId,
			List<Note> noteList) {
		this.labelId = labelId;
		this.labelName = labelName;
		this.creationTime = creationTime;
		this.updateTime = updateTime;
		this.userId = userId;
		this.noteList = noteList;
	}

	public String getLabelId() {
		return labelId;
	}

	public List<Note> getNoteList() {
		return noteList;
	}

	public void setNoteList(List<Note> noteList) {
		this.noteList = noteList;
	}

	public String getLabelName() {
		return labelName;
	}

	public LocalDateTime getCreationTime() {
		return creationTime;
	}

	public LocalDateTime getUpdateTime() {
		return updateTime;
	}

	public String getUserId() {
		return userId;
	}


	public void setLabelId(String labelId) {
		this.labelId = labelId;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public void setCreationTime(LocalDateTime creationTime) {
		this.creationTime = creationTime;
	}

	public void setUpdateTime(LocalDateTime updateTime) {
		this.updateTime = updateTime;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Label [labelId=" + labelId + ", labelName=" + labelName + ", creationTime=" + creationTime
				+ ", updateTime=" + updateTime + ", userId=" + userId + ", noteList=" + noteList + "]";
	}


}
