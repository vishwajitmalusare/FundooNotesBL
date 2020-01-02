package com.bridgelabz.fundooApp.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundooApp.dto.LoginDto;
import com.bridgelabz.fundooApp.dto.UserDto;
import com.bridgelabz.fundooApp.response.Response;
import com.bridgelabz.fundooApp.service.UserService;

@RestController
@RequestMapping("/userservice")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/register")
	public ResponseEntity<Response> registerUser(@Valid @RequestBody UserDto userDto, HttpServletRequest request) {
		StringBuffer requestUrl = request.getRequestURL();
		String message = userService.registrationUser(userDto, requestUrl);
		Response response = new Response(200, message, null);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@PostMapping("/login")
	public ResponseEntity<Response> loginUser(@RequestBody LoginDto loginDto, HttpServletResponse httpServletResponse)
			throws IllegalArgumentException, UnsupportedEncodingException {
		String token = userService.loginUser(loginDto);
		httpServletResponse.setHeader("Authorization", token);
		Response response = new Response(HttpStatus.OK.value(), "User logged in successfully", null);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@GetMapping("/forget")
	public ResponseEntity<Response> forgotPassword(@RequestParam String emailId, HttpServletRequest request) {
		StringBuffer requestUrl = request.getRequestURL();
		// Response response = userService.forgetPassword(emailId, requestUrl);
		String message = userService.forgetPassword(emailId, requestUrl);
		Response response = new Response(HttpStatus.OK.value(), message, null);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@PutMapping("/resetpassword")
	public ResponseEntity<Response> resetPassword(@RequestParam String token, @RequestBody String password) {
		String message = userService.restSetPassword(token, password);
		Response response = new Response(HttpStatus.OK.value(), message, null);
		return new ResponseEntity<Response>(response, HttpStatus.OK);

	}

	@GetMapping("/verification/{token}")
	public ResponseEntity<Response> mailValidation(@PathVariable String token) {
		String message = userService.validateUser(token);
		Response response = new Response(HttpStatus.OK.value(), message, null);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

}
