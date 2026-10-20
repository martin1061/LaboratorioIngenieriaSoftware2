/*
 * Autores: Britney Benítez, Juan Manuel García,
 *          Yineidy Mamián Jiménez, Martín Silva Cabrera
 * Laboratorio de Ingeniería de Software II - Universidad del Cauca
 */
package co.edu.unicauca.review.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Exchange y cola donde se publica el evento ReviewerAssigned. */
@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "saberpro.direct.exchange";
    public static final String QUEUE_NOTIFICATION = "notification.reviewer-assigned";
    public static final String RK_REVIEWER_ASSIGNED = "reviewer.assigned";

    @Bean public DirectExchange exchange() { return new DirectExchange(EXCHANGE); }
    @Bean public Queue notificationQueue() { return QueueBuilder.durable(QUEUE_NOTIFICATION).build(); }
    @Bean public Binding bindNotification(Queue notificationQueue, DirectExchange exchange) {
        return BindingBuilder.bind(notificationQueue).to(exchange).with(RK_REVIEWER_ASSIGNED);
    }
    @Bean public Jackson2JsonMessageConverter messageConverter() { return new Jackson2JsonMessageConverter(); }
}
