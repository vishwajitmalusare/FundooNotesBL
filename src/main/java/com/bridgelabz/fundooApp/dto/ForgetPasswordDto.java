package com.bridgelabz.fundooApp.dto;

public class ForgetPasswordDto {

	private String password;
	
	public ForgetPasswordDto()
	{
		
	}

	/**
	 * @param password
	 */
	public ForgetPasswordDto(String password) {
		super();
		this.password = password;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	

}
