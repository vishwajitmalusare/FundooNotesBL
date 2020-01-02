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
	private String firstName;
	
	@NotNull
	@Size(min=2 , max = 20)
	private String lastName;

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
	public UserDto(@NotNull @Size(min = 2, max = 20) String firstName,@NotNull @Size(min = 2, max = 20) String lastName, String email,
			@Pattern(regexp = "^[6-9]?[0-9]{9}+$") String phoneNumber, @NotNull String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.password = password;
	}


	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

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
		return "UserDto [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", phoneNumber="
				+ phoneNumber + ", password=" + password + "]";
	}

	

}
