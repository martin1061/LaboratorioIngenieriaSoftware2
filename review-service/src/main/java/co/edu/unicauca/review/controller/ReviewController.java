/*
 * Autores: Britney Benítez, Juan Manuel García,
 *          Yineidy Mamián Jiménez, Martín Silva Cabrera
 * Laboratorio de Ingeniería de Software II - Universidad del Cauca
 */
package co.edu.unicauca.review.controller;

import co.edu.unicauca.review.dto.Dtos.*;
import co.edu.unicauca.review.model.*;
import co.edu.unicauca.review.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/** Endpoints REST del Review Service. */
@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService service;

    public ReviewController(ReviewService service) { this.service = service; }

    /** POST /reviews/assign: asigna un revisor. */
    @PostMapping("/assign")
    public ResponseEntity<ReviewAssignment> asignar(@RequestBody AssignRequest req) {
        return ResponseEntity.status(201).body(service.asignar(req));
    }

    /** POST /reviews/{questionId}/result: aprueba o rechaza la pregunta. */
    @PostMapping("/{questionId}/result")
    public Review resultado(@PathVariable Long questionId, @RequestBody ReviewResultRequest req) {
        return service.registrarResultado(questionId, req);
    }

    /** GET /reviews/question/{questionId}: asignaciones de una pregunta. */
    @GetMapping("/question/{questionId}")
    public List<ReviewAssignment> listar(@PathVariable Long questionId) { return service.listar(questionId); }
}
