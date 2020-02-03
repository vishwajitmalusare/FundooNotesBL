package com.bridgelabz.fundooApp.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@SuppressWarnings("unused")
public class UserDto {

	@NotNull
	@Size(min = 2 , max = 20)
	private String name;

	private String email;

	@Pattern(regexp = "^[6-9]?[0-9]{9}+$")
	private String phoneNumber;
	@NotNull
	private String password;

	public UserDto() {

	}

	/**
	 * @param name
	 * @param email
	 * @param phoneNumber
	 * @param password
	 */
	public UserDto(@NotNull @Size(min = 2, max = 20) String name, String email,
			@Pattern(regexp = "^[6-9]?[0-9]{9}+$") String phoneNumber, @NotNull String password) {
		super();
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.password = password;
	}


	public String getName() {
		return name;
	}

	public void setName(String firstName) {
		this.name = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
//	public void setEmail(String email) {
//		this.email = email;
//	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserDto [name=" + name + ", email=" + email + ", phoneNumber="
				+ phoneNumber + ", password=" + password + "]";
	}

	

}
