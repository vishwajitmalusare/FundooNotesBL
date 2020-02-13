package com.bridgelabz.fundooApp.utility;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.bridgelabz.fundooApp.dto.Email;

@Component
public class MailUtil {

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private JWTTokenGenerator tokenGenerator;

	public void send(Email email) {
		SimpleMailMessage simple = new SimpleMailMessage();
		simple.setTo(email.getTo());
		simple.setSubject(email.getSubject());
		simple.setText(email.getBody());

		javaMailSender.send(simple);
		System.out.println("Email send successfully....");
	}
	
	/** * Purpose :function to generate validation link
		 * @param link : Passing the link of user 
		 * @param id : Passing the user id to create token for that particular userId 
		 * @return : Return validation link
		 * @throws IllegalArgumentException
		 * @throws UnsupportedEncodingException
		 * @return verification link
		 * */
	public String getLink(String link, String id) throws IllegalArgumentException, UnsupportedEncodingException
	{
		return link+tokenGenerator.generateToken(id);
	}

}