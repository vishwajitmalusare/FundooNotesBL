package com.bridgelabz.fundooApp.utility;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bridgelabz.fundooApp.dto.Email;
import com.bridgelabz.fundooApp.service.NoteService;

@Component
public class RabbitMQListener {

	@Autowired
	MailUtil mailService;
	
	String token;
	
	@Autowired
	NoteService noteService;
	
	@RabbitListener(queues="userQueue")
	public void receiveMessage(Email email) {
		System.out.println("User in Listner");
		mailService.send(email);
	}
	
}