/*
 * Autores: Britney Benítez, Juan Manuel García,
 *          Yineidy Mamián Jiménez, Martín Silva Cabrera
 * Laboratorio de Ingeniería de Software II - Universidad del Cauca
 */
package co.edu.unicauca.lifecycle.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Exchange y colas del Lifecycle Service. */
@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "saberpro.direct.exchange";
    public static final String QUEUE_COMMANDS = "lifecycle.commands";
    public static final String RK_SUBMIT = "question.submit";
    public static final String QUEUE_RESULT = "question.operation-result";
    public static final String RK_RESULT = "question.operation-result";

    @Bean public DirectExchange exchange() { return new DirectExchange(EXCHANGE); }

    @Bean public Queue commandsQueue() { return QueueBuilder.durable(QUEUE_COMMANDS).build(); }
    @Bean public Binding bindCommands(Queue commandsQueue, DirectExchange exchange) {
        return BindingBuilder.bind(commandsQueue).to(exchange).with(RK_SUBMIT);
    }

    @Bean public Queue resultQueue() { return QueueBuilder.durable(QUEUE_RESULT).build(); }
    @Bean public Binding bindResult(Queue resultQueue, DirectExchange exchange) {
        return BindingBuilder.bind(resultQueue).to(exchange).with(RK_RESULT);
    }

    @Bean public Jackson2JsonMessageConverter messageConverter() { return new Jackson2JsonMessageConverter(); }
}
