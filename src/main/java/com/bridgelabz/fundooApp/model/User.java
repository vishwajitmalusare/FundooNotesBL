package com.bridgelabz.fundooApp.model;

import java.time.LocalDateTime;

import javax.validation.constraints.Email;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

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
	
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	public User(String userId, @Email(message = "Email should not be null") String email, String name, String password, String phoneNumber, String address, boolean isVerified,
			LocalDateTime creationTime, LocalDateTime updateTime, String profilePicture) {
		super();
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

	@Override
	public String toString() {
		return "User [userId=" + userId + ", email=" + email + ", name=" + name +", password=" + password + ", phoneNumber=" + phoneNumber + ", address=" + address + ", isVerified="
				+ isVerified + ", creationTime=" + creationTime + ", updateTime=" + updateTime + ", profilePicture="
				+ profilePicture + "]";
	}



}
