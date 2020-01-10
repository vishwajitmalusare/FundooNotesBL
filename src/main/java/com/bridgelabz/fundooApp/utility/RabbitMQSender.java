package com.bridgelabz.fundooApp.utility;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bridgelabz.fundooApp.dto.Email;

@Component
public class RabbitMQSender {

	@Autowired
	private AmqpTemplate rabbitTemplate;
	
	@Value("${spring.rabbitmq.template.userExchange}")
	private String userExchange;
	
	@Value("${spring.rabbitmq.template.userRouting-Key}")
	private String userRoutingKey;
	
	public void sendMessageToQueue(Email email) {
		System.out.println("In user Sender");
		rabbitTemplate.convertAndSend(userExchange, userRoutingKey, email);
	}
}