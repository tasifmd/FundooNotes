package com.bridgelabz.fundoo.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.core.AmqpTemplate;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Purpose : RabbitMq Configuration class
 * @author Tasif Mohammed
 *
 */
@Configuration
public class RabbitMqConfig {
	
		@Value("${queueName}")
		private String queueName;
		@Value("${exchange}")
		private String exchange;
		@Value("${routingkey}")
		private String routingkey;
		
		
		/**
		 * Purpose : Bean object for creating Queue
		 * @return
		 */
		@Bean
		Queue queue() {
			return new Queue(queueName);
		}

		/**
		 * Purpose : Bean object for creating DirectExchange
		 * @return
		 */
		@Bean
		DirectExchange exchange() {
			return new DirectExchange(exchange);
		}

		
		/**
		 * Purpose : Bean object for creating Binding that binds queue with exchange with routing key
		 * @param queue
		 * @param exchange
		 * @return
		 */
		@Bean
		Binding binding(Queue queue, DirectExchange exchange) {
			return BindingBuilder.bind(queue).to(exchange).with(routingkey);
		}

		@Bean
		public MessageConverter jsonMessageConverter() {
			return new Jackson2JsonMessageConverter();
		}

		
//		@Bean
//		public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
//			final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//			rabbitTemplate.setMessageConverter(jsonMessageConverter());
//			return rabbitTemplate;
//		}
}
