package com.bridgelabz.fundooApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundooApp.response.Response;
import com.bridgelabz.fundooApp.service.PDFService;

@RestController()
@RequestMapping("/pdf")
public class PDFController {

	@Autowired
	private PDFService pdfService;
	
	@PostMapping("/create")
	public ResponseEntity<Response> createPDF(@RequestParam String token,@RequestParam String fileName) {
		String message = pdfService.createDocument(token, fileName);
		Response response = new Response(HttpStatus.OK.value(), message,null);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	
	@PostMapping("/notetopdf")
	public ResponseEntity<Response> extractNoteToPDF(@RequestParam String token, @RequestParam String fileName) {
		String message = pdfService.extractNoteToPDF(token, fileName);
		Response response = new Response(HttpStatus.OK.value(), message, null);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
}