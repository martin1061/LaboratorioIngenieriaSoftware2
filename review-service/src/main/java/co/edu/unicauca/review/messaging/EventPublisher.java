/*
 * Autores: Britney Benítez, Juan Manuel García,
 *          Yineidy Mamián Jiménez, Martín Silva Cabrera
 * Laboratorio de Ingeniería de Software II - Universidad del Cauca
 */
package co.edu.unicauca.review.messaging;

import co.edu.unicauca.review.config.RabbitMQConfig;
import co.edu.unicauca.review.dto.Dtos.ReviewerAssignedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/** Publica el evento ReviewerAssigned en RabbitMQ. */
@Component
public class EventPublisher {

    private final RabbitTemplate rabbit;

    public EventPublisher(RabbitTemplate rabbit) { this.rabbit = rabbit; }

    public void publicarReviewerAssigned(ReviewerAssignedEvent e) {
        rabbit.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.RK_REVIEWER_ASSIGNED, e);
    }
}
