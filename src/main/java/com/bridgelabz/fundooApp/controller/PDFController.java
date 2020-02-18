package com.bridgelabz.fundooApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundooApp.response.Response;
import com.bridgelabz.fundooApp.service.PDFService;

@CrossOrigin(allowedHeaders = "*", origins = "*")
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
	
	@GetMapping("/notetopdf")
	public ResponseEntity<Response> extractNoteToPDF(@RequestHeader String token, @RequestParam String fileName) {
		String message = pdfService.extractNoteToPDF(token, fileName);
		Response response = new Response(HttpStatus.CREATED.value(), message, null);
		return new ResponseEntity<Response>(response, HttpStatus.CREATED);
	}
}
