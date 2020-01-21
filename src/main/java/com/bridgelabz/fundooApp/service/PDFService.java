package com.bridgelabz.fundooApp.service;

public interface PDFService {
	
	public String createDocument(String token, String fileName);
	
	public String extractNoteToPDF(String token, String fileName);
}
