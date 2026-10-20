/*
 * Autores: Britney Benítez, Juan Manuel García,
 *          Yineidy Mamián Jiménez, Martín Silva Cabrera
 * Laboratorio de Ingeniería de Software II - Universidad del Cauca
 */
package co.edu.unicauca.lifecycle.service;

import co.edu.unicauca.lifecycle.dto.Dtos.*;
import co.edu.unicauca.lifecycle.model.*;
import co.edu.unicauca.lifecycle.repository.*;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

/** Única autoridad sobre el estado de las preguntas. */
@Service
public class LifecycleService {

    private final StateRepository states;
    private final OperationResultRepository results;

    public LifecycleService(StateRepository states, OperationResultRepository results) {
        this.states = states;
        this.results = results;
    }

    /** Registra una pregunta nueva en estado DRAFT. */
    public QuestionState iniciar(Long questionId) {
        if (states.existsById(questionId))
            throw new IllegalStateException("La pregunta " + questionId + " ya tiene estado");
        QuestionState s = new QuestionState();
        s.questionId = questionId;
        s.state = QuestionStatus.DRAFT;
        s.changedAt = LocalDateTime.now();
        s.comment = "Pregunta creada";
        return states.save(s);
    }

    public QuestionState obtener(Long questionId) {
        return states.findById(questionId)
                .orElseThrow(() -> new NoSuchElementException("No existe estado para la pregunta " + questionId));
    }

    /** Aplica una transición si es válida; si no, lanza excepción. */
    public QuestionState transicionar(Long questionId, QuestionStatus destino, String comentario) {
        QuestionState s = obtener(questionId);
        if (!s.state.canTransitionTo(destino))
            throw new IllegalArgumentException("Transición no permitida: " + s.state + " -> " + destino);
        s.state = destino;
        s.changedAt = LocalDateTime.now();
        s.comment = comentario;
        return states.save(s);
    }

    /** Procesa el comando SubmitQuestionForReview (idempotente por operationId). */
    public OperationResultMessage procesarSubmit(SubmitCommand c) {
        OperationResult op = results.findById(c.operationId()).orElseGet(() -> {
            OperationResult n = new OperationResult();
            n.operationId = c.operationId();
            n.questionId = c.questionId();
            n.type = "SubmitQuestionForReview";
            n.createdAt = LocalDateTime.now();
            try {
                transicionar(c.questionId(), QuestionStatus.PENDING_REVIEW, "Enviada a revisión");
                n.status = "COMPLETED";
                n.message = "Transición DRAFT -> PENDING_REVIEW completada";
            } catch (Exception e) {
                n.status = "FAILED";   // se registra el fallo, no se informa éxito falso
                n.message = e.getMessage();
            }
            n.completedAt = LocalDateTime.now();
            return results.save(n);
        });
        return new OperationResultMessage(op.operationId, op.questionId, op.status, op.message,
                op.completedAt.toString());
    }

    public OperationResult operacion(String operationId) {
        return results.findById(operationId)
                .orElseThrow(() -> new NoSuchElementException("Operación no encontrada"));
    }
}
