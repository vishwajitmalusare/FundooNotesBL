package com.bridgelabz.fundooApp.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundooApp.exception.UserException;
import com.bridgelabz.fundooApp.model.User;
import com.bridgelabz.fundooApp.repository.UserRepository;
import com.bridgelabz.fundooApp.utility.JWTTokenGenerator;

@Service
public class PDFServiceImpl implements PDFService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JWTTokenGenerator tokenGenerator;
	
	@SuppressWarnings("resource")
	@Override
	public String createDocument(String token, String fileName) {
		// TODO Auto-generated method stub
		try {
		PDDocument document = new PDDocument();
		
		String userId = tokenGenerator.verifyToken(token);
		Optional<User> optUser =userRepository.findByUserId(userId);
		PDDocumentInformation pdfAttribute = document.getDocumentInformation();
		
		if(optUser.isPresent()) {
			User user = optUser.get();
			String userName = user.getFirstName();
			pdfAttribute.setAuthor(userName);
		}
		else {
			throw new UserException("user dose not exist");
		}
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String currentDate = dateFormat.format(date);
		document.save("/home/admin1/Downloads/Vish/bdzl/MyPDFs/"+fileName+"_"+currentDate+".pdf");
		document.close();
		}catch (Exception e) {
			// TODO: handle exception
			e.getMessage();
		}
		return "PDF created sucessfully.......";
	}

}
