/*
 * Autores: Britney Benítez, Juan Manuel García,
 *          Yineidy Mamián Jiménez, Martín Silva Cabrera
 * Laboratorio de Ingeniería de Software II - Universidad del Cauca
 */
package co.edu.unicauca.review.service;

import co.edu.unicauca.review.client.*;
import co.edu.unicauca.review.dto.Dtos.*;
import co.edu.unicauca.review.messaging.EventPublisher;
import co.edu.unicauca.review.model.*;
import co.edu.unicauca.review.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.server.ResponseStatusException;
import java.time.Instant;
import java.util.List;

/** Lógica de asignación de revisores y registro de resultados. */
@Service
public class ReviewService {

    private final AssignmentRepository assignments;
    private final ReviewRepository reviews;
    private final LifecycleClient lifecycle;
    private final UserClient users;
    private final EventPublisher publisher;

    public ReviewService(AssignmentRepository a, ReviewRepository r, LifecycleClient l,
                         UserClient u, EventPublisher p) {
        this.assignments = a;
        this.reviews = r;
        this.lifecycle = l;
        this.users = u;
        this.publisher = p;
    }

    /** Asigna un revisor y pide a Lifecycle la transición PENDING_REVIEW -> IN_REVIEW. */
    public ReviewAssignment asignar(AssignRequest req) {
        if (req == null || req.questionId() == null || req.reviewerId() == null || req.assignedBy() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "questionId, reviewerId y assignedBy son obligatorios");

        // 1. Quien asigna debe ser ADMIN y el revisor debe tener rol REVISOR
        UserDTO admin = buscarUsuario(req.assignedBy());
        if (admin == null || !admin.roles().contains("ADMIN"))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Solo un administrador puede asignar revisores");
        UserDTO revisor = buscarUsuario(req.reviewerId());
        if (revisor == null || !revisor.roles().contains("REVISOR"))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario indicado no es un revisor válido");

        // 2. La pregunta debe estar en PENDING_REVIEW
        String estado;
        try {
            estado = lifecycle.obtenerEstado(req.questionId());
        } catch (RestClientException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Lifecycle Service no disponible");
        }
        if (estado == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La pregunta no existe");
        if (!estado.equals("PENDING_REVIEW"))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La pregunta no es elegible, estado actual: " + estado);
        if (assignments.existsByQuestionIdAndStatusIn(req.questionId(), List.of("PENDING", "CONFIRMED")))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La pregunta ya tiene un revisor asignado");

        // 3. Se registra la asignación como PENDING
        ReviewAssignment a = new ReviewAssignment();
        a.questionId = req.questionId();
        a.reviewerId = req.reviewerId();
        a.assignedBy = req.assignedBy();
        a.status = "PENDING";
        a = assignments.save(a);

        // 4. Se pide la transición; si falla, la asignación queda FAILED (sin inconsistencias)
        try {
            lifecycle.transicionar(req.questionId(), "IN_REVIEW", "Revisor asignado: " + req.reviewerId());
        } catch (IllegalStateException e) {
            a.status = "FAILED";
            assignments.save(a);
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
        a.status = "CONFIRMED";
        a = assignments.save(a);

        // 5. Evento asíncrono: si RabbitMQ falla no se revierte la asignación
        try {
            publisher.publicarReviewerAssigned(new ReviewerAssignedEvent(
                    a.questionId, a.reviewerId, a.assignedBy, Instant.now().toString()));
        } catch (Exception e) {
            System.err.println("[ReviewService] No se pudo publicar ReviewerAssigned: " + e.getMessage());
        }
        return a;
    }

    /** Registra APPROVED o REJECTED y pide la transición a Lifecycle. */
    public Review registrarResultado(Long questionId, ReviewResultRequest req) {
        String res = req == null || req.result() == null ? "" : req.result().toUpperCase();
        if (!res.equals("APPROVED") && !res.equals("REJECTED"))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "result debe ser APPROVED o REJECTED");

        ReviewAssignment a = assignments.findFirstByQuestionIdAndStatus(questionId, "CONFIRMED")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "No hay revisor asignado para esta pregunta"));
        try {
            lifecycle.transicionar(questionId, res, req.comments());
        } catch (IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
        Review r = new Review();
        r.questionId = questionId;
        r.reviewerId = a.reviewerId;
        r.result = res;
        r.comments = req.comments();
        a.status = "COMPLETED";
        assignments.save(a);
        return reviews.save(r);
    }

    public List<ReviewAssignment> listar(Long questionId) { return assignments.findByQuestionId(questionId); }

    private UserDTO buscarUsuario(Long id) {
        try {
            return users.buscar(id);
        } catch (RestClientException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "User Service no disponible");
        }
    }
}
