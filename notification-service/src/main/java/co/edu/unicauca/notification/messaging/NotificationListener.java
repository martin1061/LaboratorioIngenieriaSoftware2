/*
 * Autores: Britney Benítez, Juan Manuel García,
 *          Yineidy Mamián Jiménez, Martín Silva Cabrera
 * Laboratorio de Ingeniería de Software II - Universidad del Cauca
 */
package co.edu.unicauca.notification.messaging;

import co.edu.unicauca.notification.config.RabbitMQConfig;
import co.edu.unicauca.notification.dto.ReviewerAssignedEvent;
import co.edu.unicauca.notification.service.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/** Escucha el evento ReviewerAssigned. */
@Component
public class NotificationListener {

    private final EmailService email;

    public NotificationListener(EmailService email) { this.email = email; }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NOTIFICATION)
    public void alRecibir(ReviewerAssignedEvent evento) { email.enviar(evento); }
}
