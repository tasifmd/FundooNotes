package com.bridgelabz.fundoo.util;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelabz.fundoo.user.model.Email;

public class GenerateEmail {

	@Autowired
	private AmqpTemplate rabbitTemplate;
	
	private String exchange = "exchange";
	private String routingkey = "tasif";
	
	public void send(Email email) {
		rabbitTemplate.convertAndSend(exchange , routingkey , email);
	}
}
