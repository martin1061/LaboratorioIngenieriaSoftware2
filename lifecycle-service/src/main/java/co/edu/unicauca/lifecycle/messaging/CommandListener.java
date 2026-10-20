/*
 * Autores: Britney Benítez, Juan Manuel García,
 *          Yineidy Mamián Jiménez, Martín Silva Cabrera
 * Laboratorio de Ingeniería de Software II - Universidad del Cauca
 */
package co.edu.unicauca.lifecycle.messaging;

import co.edu.unicauca.lifecycle.config.RabbitMQConfig;
import co.edu.unicauca.lifecycle.dto.Dtos.*;
import co.edu.unicauca.lifecycle.service.LifecycleService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/** Consume comandos de RabbitMQ y publica el resultado. */
@Component
public class CommandListener {

    private final LifecycleService service;
    private final RabbitTemplate rabbit;

    public CommandListener(LifecycleService service, RabbitTemplate rabbit) {
        this.service = service;
        this.rabbit = rabbit;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_COMMANDS)
    public void alRecibirSubmit(SubmitCommand cmd) {
        System.out.println("[LifecycleService] Comando recibido para pregunta " + cmd.questionId());
        OperationResultMessage resultado = service.procesarSubmit(cmd);
        rabbit.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.RK_RESULT, resultado);
    }
}
