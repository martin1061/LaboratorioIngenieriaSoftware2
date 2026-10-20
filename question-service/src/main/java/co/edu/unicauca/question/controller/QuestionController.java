/*
 * Autores: Britney Benítez, Juan Manuel García,
 *          Yineidy Mamián Jiménez, Martín Silva Cabrera
 * Laboratorio de Ingeniería de Software II - Universidad del Cauca
 */
package co.edu.unicauca.question.controller;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.amqp.AmqpException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.question.client.LifecycleClient;
import co.edu.unicauca.question.client.ValidationClient;
import co.edu.unicauca.question.dto.Dtos.CreateQuestionRequest;
import co.edu.unicauca.question.dto.Dtos.CreateQuestionResponse;
import co.edu.unicauca.question.dto.Dtos.SubmitCommand;
import co.edu.unicauca.question.dto.Dtos.ValidationRequest;
import co.edu.unicauca.question.dto.Dtos.ValidationResult;
import co.edu.unicauca.question.messaging.CommandPublisher;
import co.edu.unicauca.question.model.OperationEntity;
import co.edu.unicauca.question.model.OptionEmb;
import co.edu.unicauca.question.model.Question;
import co.edu.unicauca.question.repository.OperationRepository;
import co.edu.unicauca.question.repository.QuestionRepository;

/** Endpoints REST del Question Service. */
@RestController
public class QuestionController {

    private final ValidationClient validationClient;
    private final LifecycleClient lifecycleClient;
    private final QuestionRepository questions;
    private final OperationRepository operations;
    private final CommandPublisher publisher;

    public QuestionController(ValidationClient v, LifecycleClient l, QuestionRepository q,
                              OperationRepository o, CommandPublisher p) {
        this.validationClient = v;
        this.lifecycleClient = l;
        this.questions = q;
        this.operations = o;
        this.publisher = p;
    }

    /** POST /questions: valida (síncrono) y guarda en DRAFT. */
    @PostMapping("/questions")
    public ResponseEntity<?> crear(@RequestBody CreateQuestionRequest req,
                                   @RequestHeader(value = "Authorization", required = false) String auth) {
        // 1. Validación síncrona contra Validation Service
        ValidationResult r = validationClient.validar(
                new ValidationRequest(req.text(), req.options(), req.correctOptionId()));
        if (r == null || !r.valid()) {
            return ResponseEntity.badRequest().body(Map.of("status", "INVALID",
                    "errors", r == null ? List.of("Sin respuesta de validación") : r.errors()));
        }

        // 2. Se guarda la pregunta
        Question q = new Question();
        q.text = req.text();
        q.correctOptionId = req.correctOptionId();
       q.authorId = req.authorId();
req.options().forEach(o -> q.options.add(new OptionEmb(o.id(), o.text())));

Question savedQuestion = questions.save(q);

        // 3. Se registra el estado DRAFT en Lifecycle (única autoridad del estado)
        try {
            lifecycleClient.registrarBorrador(q.id);
        } catch (Exception e) {
            questions.delete(q); // no dejamos preguntas sin estado
            return ResponseEntity.status(503).body(Map.of("error", "No se pudo registrar DRAFT: " + e.getMessage()));
        }
        return ResponseEntity.status(201)
                .body(new CreateQuestionResponse(q.id, "DRAFT", "Pregunta creada en estado DRAFT"));
    }

    /** GET /questions/{id}: consulta una pregunta. */
    @GetMapping("/questions/{id}")
    public ResponseEntity<?> obtener(@PathVariable Long id) {
        return questions.findById(id).<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /** POST /questions/{id}/submit: publica el comando y responde 202 Accepted. */
    @PostMapping("/questions/{id}/submit")
    public ResponseEntity<?> enviarARevision(@PathVariable Long id,
                                             @RequestParam(required = false) Long requestedBy) {
        if (!questions.existsById(id))
            return ResponseEntity.status(404).body(Map.of("error", "La pregunta no existe"));

        OperationEntity op = new OperationEntity();
        op.operationId = UUID.randomUUID().toString();
        op.questionId = id;
        op.status = "PENDING";
        op.message = "Solicitud en cola";
        operations.save(op);

        try {
            publisher.publicarSubmit(new SubmitCommand(op.operationId, id, requestedBy, Instant.now().toString()));
        } catch (AmqpException e) {
            op.status = "FAILED";
            op.message = "No se pudo publicar en RabbitMQ";
            operations.save(op);
            return ResponseEntity.status(503).body(Map.of("error", op.message, "operationId", op.operationId));
        }
        return ResponseEntity.status(202).body(Map.of(
                "message", "Solicitud de envío a revisión aceptada para procesamiento.",
                "operationId", op.operationId,
                "status", "PROCESSING"));
    }

    /** GET /operations/{operationId}: consulta el resultado de una operación asíncrona. */
    @GetMapping("/operations/{operationId}")
    public ResponseEntity<?> operacion(@PathVariable String operationId) {
        return operations.findById(operationId).<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
