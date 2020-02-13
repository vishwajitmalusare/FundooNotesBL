package com.bridgelabz.fundooApp.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.Email;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Document
public class User {

	@Id
	private String userId;
	// @Indexed(unique=true)
	@Email(message = "Email should not be null")
	private String email;
	private String name;
	private String password;
	private String phoneNumber;
	private String address;
	private boolean isVerified;
	private LocalDateTime creationTime;
	private LocalDateTime updateTime;
	private String profilePicture;

	@JsonIgnore
	@DBRef()
	private List<Note> userNotes;

	@JsonIgnore
	@DBRef(lazy = true)
	private List<Note> collaboratedNotes;

	public User() {

	}

	/**
	 * @param userId
	 * @param email
	 * @param name
	 * @param password
	 * @param phoneNumber
	 * @param address
	 * @param isVerified
	 * @param creationTime
	 * @param updateTime
	 */


	public User(String userId, @Email(message = "Email should not be null") String email, String name, String password,
			String phoneNumber, String address, boolean isVerified, LocalDateTime creationTime,
			LocalDateTime updateTime, String profilePicture, List<Note> userNotes, List<Note> collaboratedNotes) {
		this.userId = userId;
		this.email = email;
		this.name = name;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.isVerified = isVerified;
		this.creationTime = creationTime;
		this.updateTime = updateTime;
		this.profilePicture = profilePicture;
		this.userNotes = userNotes;
		this.collaboratedNotes = collaboratedNotes;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the isVerified
	 */
	public boolean isVerified() {
		return isVerified;
	}

	/**
	 * @param isVerified the isVerified to set
	 */
	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}

	/**
	 * @return the userid
	 */
	public String getUserid() {
		return userId;
	}

	/**
	 * @param userid the userid to set
	 */
	public void setUserid(String userid) {
		this.userId = userid;
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public LocalDateTime getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(LocalDateTime creationTime) {
		this.creationTime = creationTime;
	}

	public LocalDateTime getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(LocalDateTime updateTime) {
		this.updateTime = updateTime;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

	public List<Note> getUserNotes() {
		return userNotes;
	}

	public void setUserNotes(List<Note> userNotes) {
		this.userNotes = userNotes;
	}

	public List<Note> getCollaboratedNotes() {
		return collaboratedNotes;
	}

	public void setCollaboratedNotes(List<Note> collaboratedNotes) {
		this.collaboratedNotes = collaboratedNotes;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", email=" + email + ", name=" + name + ", password=" + password
				+ ", phoneNumber=" + phoneNumber + ", address=" + address + ", isVerified=" + isVerified
				+ ", creationTime=" + creationTime + ", updateTime=" + updateTime + ", profilePicture=" + profilePicture
				+ ", userNotes=" + userNotes + ", collaboratedNotes=" + collaboratedNotes + "]";
	}

}
