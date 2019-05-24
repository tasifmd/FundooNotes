package com.bridgelabz.fundoo.util;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.bridgelabz.fundoo.elasticsearch.ElasticSearch;
import com.bridgelabz.fundoo.notes.model.Note;
import com.bridgelabz.fundoo.user.model.Email;

/**
 * Purpose : Mail Service class to do task of mailing
 * @author : Tasif Mohammed
 * 
 */
@Component
public class RabbitMqService {
	
	@Autowired
	private AmqpTemplate amqpTemplate;
	
	@Autowired
	private JavaMailSender javaMailSender;	
	
	@Autowired
	private UserToken userToken;
	
	@Autowired
	private ElasticSearch elasticSearch;
	
	@Value("${exchange}")
	private String exchange;
	
	@Value("${routingkey}")
	private String routingkey;
	
	private String elasticRountingKey = "elasticRountingKey";
	
	
	/**
	 * Purpose : Method to send email
	 * @param email
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */
	
	@RabbitListener(queues = "${queueName}")
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
	
	public void sendNote(NoteContainer noteContainer) {
		 amqpTemplate.convertAndSend(exchange,elasticRountingKey, noteContainer);
	}
	
	@RabbitListener(queues = "elasticQueue")
	public void operation(NoteContainer notecontainer) {
		System.out.println("operation");
		Note note=notecontainer.getNote();
		switch(notecontainer.getNoteOperation()) {
		
		case CREATE:
			elasticSearch.create(note);
			break;
			
		case UPDATE :
			elasticSearch.updateNote(note);
			break;

		case DELETE :
			elasticSearch.deleteNote(note.getId());
			break;
		}
	}
}
