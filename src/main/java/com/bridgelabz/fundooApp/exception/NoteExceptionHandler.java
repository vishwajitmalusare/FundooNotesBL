package com.bridgelabz.fundooApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bridgelabz.fundooApp.response.Response;

@ControllerAdvice
public class NoteExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Response> handleException(String message)
	{
		Response response=new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, null);
		return new  ResponseEntity<Response>(response,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(NoteException.class)
	public ResponseEntity<Response> handleNoteException(RuntimeException runtimeException)
	{
		Response response=new Response(HttpStatus.BAD_REQUEST.value(), runtimeException.getMessage(), null);
		return new ResponseEntity<Response>(response,HttpStatus.BAD_REQUEST);
				
	}
}
