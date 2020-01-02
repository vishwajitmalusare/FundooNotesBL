package com.bridgelabz.fundooApp.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.bridgelabz.fundooApp.dto.Email;

@Component
public class MailUtil {

	@Autowired
	private JavaMailSender javaMailSender;

	public void send(Email email) {
		SimpleMailMessage simple = new SimpleMailMessage();
		simple.setTo(email.getTo());
		simple.setSubject(email.getSubject());
		simple.setText(email.getBody());

		javaMailSender.send(simple);
		System.out.println("Email send successfully....");
	}

}
