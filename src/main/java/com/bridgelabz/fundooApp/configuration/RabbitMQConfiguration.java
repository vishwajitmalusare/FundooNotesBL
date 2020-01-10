package com.bridgelabz.fundooApp.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

	@Value("${spring.rabbitmq.template.userExchange}")
	private String userExchange;
	
	@Value("${spring.rabbitmq.template.userQueue}")
	private String userQueue;
	
	@Value("${spring.rabbitmq.template.userRouting-Key}")
	private String userRoutingkey;
	
	@Bean
	Queue userQueue() {
		return new Queue("userQueue");
	}
	
	@Bean
	DirectExchange userExchange() {
		return new DirectExchange(userExchange);
	}
	
	@Bean
	Binding bindingUser(Queue userQueue, DirectExchange exchange) {
		return BindingBuilder.bind(userQueue).to(exchange).with(userRoutingkey);
	}
}