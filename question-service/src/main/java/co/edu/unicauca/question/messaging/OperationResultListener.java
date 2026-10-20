/*
 * Autores: Britney Benítez, Juan Manuel García,
 *          Yineidy Mamián Jiménez, Martín Silva Cabrera
 * Laboratorio de Ingeniería de Software II - Universidad del Cauca
 */
package co.edu.unicauca.question.messaging;

import co.edu.unicauca.question.config.RabbitMQConfig;
import co.edu.unicauca.question.dto.Dtos.OperationResultMessage;
import co.edu.unicauca.question.repository.OperationRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

/** Recibe el resultado del procesamiento hecho por Lifecycle y actualiza la operación. */
@Component
public class OperationResultListener {

    private final OperationRepository operations;

    public OperationResultListener(OperationRepository operations) { this.operations = operations; }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_RESULT)
    public void alRecibir(OperationResultMessage msg) {
        operations.findById(msg.operationId()).ifPresent(op -> {
            op.status = msg.status();
            op.message = msg.message();
            op.completedAt = LocalDateTime.now();
            operations.save(op);
        });
    }
}
