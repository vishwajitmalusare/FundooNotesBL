
package com.bridgelabz.fundooApp.service;

import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundooApp.dto.LoginDto;
import com.bridgelabz.fundooApp.dto.UserDto;

public interface UserService {

	String registrationUser(UserDto userDto, StringBuffer requestUrl);

	String loginUser(LoginDto loginDto);

	String forgetPassword(String emailId, StringBuffer requestUrl);

	String restSetPassword(String token, String password);

	String validateUser(String token);
	
	String uploadProfilePicture(String token, MultipartFile file);
	
	String getProfilePicture(String token, String fileName);

}
