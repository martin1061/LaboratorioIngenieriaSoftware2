/*
 * Autores: Britney Benítez, Juan Manuel García,
 *          Yineidy Mamián Jiménez, Martín Silva Cabrera
 * Laboratorio de Ingeniería de Software II - Universidad del Cauca
 */
package co.edu.unicauca.question.messaging;

import co.edu.unicauca.question.config.RabbitMQConfig;
import co.edu.unicauca.question.dto.Dtos.SubmitCommand;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/** Publica comandos en RabbitMQ. */
@Component
public class CommandPublisher {

    private final RabbitTemplate rabbit;

    public CommandPublisher(RabbitTemplate rabbit) { this.rabbit = rabbit; }

    public void publicarSubmit(SubmitCommand cmd) {
        rabbit.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.RK_SUBMIT, cmd);
    }
}
