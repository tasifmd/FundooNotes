package com.bridgelabz.fundoo.applicationconfig;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.core.AmqpTemplate;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

/**
 * Purpose : RabbitMq Configuration class
 * @author Tasif Mohammed
 *
 */
@Service
public class RabbitMqConfig {

		private String queueName = "queue";
		private String exchange = "exchange";
		private String routingkey = "tasif";
		
		
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

//		
//		@Bean
//		public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
//			final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//			rabbitTemplate.setMessageConverter(jsonMessageConverter());
//			return rabbitTemplate;
//		}
}
