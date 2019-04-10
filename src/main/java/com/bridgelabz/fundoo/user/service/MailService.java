package com.bridgelabz.fundoo.user.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.bridgelabz.fundoo.user.model.Email;
import com.bridgelabz.fundoo.util.UserToken;

/**
 * Purpose : Mail Service class to do task of mailing
 * @author : Tasif Mohammed
 * 
 */
@Component
public class MailService {
	
	
	@Autowired
	private JavaMailSender javaMailSender;	
	
	@Autowired
	private UserToken userToken;
	
	/**
	 * Purpose : Method to send email
	 * @param email
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */
	
	@RabbitListener(queues = "queue")
	public void send(Email email) {
		
		SimpleMailMessage message = new SimpleMailMessage(); 
	    message.setTo(email.getTo()); 
	    message.setSubject(email.getSubject()); 
	    message.setText(email.getBody());
	    
	    System.out.println("Sending Email ");
	    
	    javaMailSender.send(message);

	    System.out.println("Email Sent Successfully!!");

	}
	
	/**
	 * Purpose : Method to generate validation link
	 * @param link : Passing the link of user 
	 * @param id : Passing the user id 
	 * @return : Return validation link
	 * @throws IllegalArgumentException
	 * @throws UnsupportedEncodingException
	 */
	public String getLink(String link,long id) throws IllegalArgumentException, UnsupportedEncodingException 
	{
		return link+userToken.generateToken(id);
	}
}
