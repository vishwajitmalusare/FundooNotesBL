package com.bridgelabz.fundooApp.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundooApp.dto.LoginDto;
import com.bridgelabz.fundooApp.dto.UserDto;
import com.bridgelabz.fundooApp.response.Response;
import com.bridgelabz.fundooApp.service.UserService;

@CrossOrigin(allowedHeaders = "*", origins = "*")
@RestController
@RequestMapping("/userservice")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/register")
	public ResponseEntity<Response> registerUser(@Valid @RequestBody UserDto userDto, HttpServletRequest request) {
		StringBuffer requestUrl = request.getRequestURL();
		String message = userService.registrationUser(userDto, requestUrl);
		Response response = new Response(HttpStatus.CREATED.value(), message, null);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@PostMapping("/login")
	public ResponseEntity<Response> loginUser(@RequestBody LoginDto loginDto, HttpServletResponse httpServletResponse)
			throws IllegalArgumentException, UnsupportedEncodingException {
		String token = userService.loginUser(loginDto);
		httpServletResponse.setHeader("Authorization", token);
		Response response = new Response(HttpStatus.OK.value(), "User logged in successfully", token);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@GetMapping("/forgetpassword")
	public ResponseEntity<Response> forgotPassword(@RequestParam String emailId, HttpServletRequest request) {
		StringBuffer requestUrl = request.getRequestURL();
		// Response response = userService.forgetPassword(emailId, requestUrl);
		String message = userService.forgetPassword(emailId, requestUrl);
		Response response = new Response(HttpStatus.OK.value(), message, null);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@PutMapping("/resetpassword")
	public ResponseEntity<Response> resetPassword(@RequestParam String emailId, @RequestBody String password) {
		String message = userService.restSetPassword(emailId, password);
		Response response = new Response(HttpStatus.OK.value(), message, null);
		return new ResponseEntity<Response>(response, HttpStatus.OK);

	}

	@GetMapping("/verification/{token}")
	public ResponseEntity<Response> mailValidation(@PathVariable String token) {
		String message = userService.validateUser(token);
		Response response = new Response(HttpStatus.OK.value(), message, null);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@PostMapping("/uploadprofilepicture")
	public ResponseEntity<Response> uploadProfilePicture(@RequestHeader String token,
			@RequestParam MultipartFile file) {
		String message = userService.uploadProfilePicture(token, file);
		Response response = new Response(HttpStatus.OK.value(), message, null);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@GetMapping("/getprofilepicture/{fileName:.+}")
	public ResponseEntity<Response> getProfilePicture(@RequestHeader String token, @PathVariable String fileName) {
		String resourceStatus = userService.getProfilePicture(token, fileName);
		String message = "Got a profile picture....";
		Response response = new Response(HttpStatus.OK.value(), message, resourceStatus);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

}
