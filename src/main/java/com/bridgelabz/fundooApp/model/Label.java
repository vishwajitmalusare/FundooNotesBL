package com.bridgelabz.fundooApp.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class Label {

	@Id
	private String labelId;
	private String labelName;
	private LocalDateTime creationTime;
	private LocalDateTime updateTime;
	private String userId;


	public Label() {
	}

	/**
	 * @param labelId
	 * @param labelName
	 * @param creationTime
	 * @param updateTime
	 * @param userId
	 */
	public Label(String labelId, String labelName, LocalDateTime creationTime, LocalDateTime updateTime, String userId) {
		
		this.labelId = labelId;
		this.labelName = labelName;
		this.creationTime = creationTime;
		this.updateTime = updateTime;
		this.userId = userId;
	}

	public String getLabelId() {
		return labelId;
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
				+ ", updateTime=" + updateTime + ", userId=" + userId + "]";
	}


}
