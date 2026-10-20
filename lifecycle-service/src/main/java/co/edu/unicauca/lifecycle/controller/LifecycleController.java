/*
 * Autores: Britney Benítez, Juan Manuel García,
 *          Yineidy Mamián Jiménez, Martín Silva Cabrera
 * Laboratorio de Ingeniería de Software II - Universidad del Cauca
 */
package co.edu.unicauca.lifecycle.controller;

import co.edu.unicauca.lifecycle.dto.Dtos.TransitionRequest;
import co.edu.unicauca.lifecycle.model.QuestionStatus;
import co.edu.unicauca.lifecycle.service.LifecycleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

/** Endpoints REST del Lifecycle Service. */
@RestController
@RequestMapping("/lifecycle")
public class LifecycleController {

    private final LifecycleService service;

    public LifecycleController(LifecycleService service) { this.service = service; }

    /** Registra el estado inicial DRAFT. */
    @PostMapping("/{id}/init")
    public ResponseEntity<?> iniciar(@PathVariable Long id) {
        return ResponseEntity.status(201).body(service.iniciar(id));
    }

    /** Consulta el estado actual. */
    @GetMapping("/{id}")
    public Object obtener(@PathVariable Long id) { return service.obtener(id); }

    /** Solicita una transición de estado (200 si es válida, 409 si no). */
    @PostMapping("/{id}/transition")
    public ResponseEntity<?> transicion(@PathVariable Long id, @RequestBody TransitionRequest req) {
        QuestionStatus destino;
        try {
            destino = QuestionStatus.valueOf(String.valueOf(req.newState()).toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Estado destino inválido: " + req.newState()));
        }
        return ResponseEntity.ok(service.transicionar(id, destino, req.comment()));
    }

    /** Consulta el resultado de una operación asíncrona. */
    @GetMapping("/operations/{operationId}")
    public Object operacion(@PathVariable String operationId) { return service.operacion(operationId); }

    // ---- Manejo de errores ----
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Map<String, String>> noEncontrado(NoSuchElementException e) {
        return ResponseEntity.status(404).body(Map.of("error", e.getMessage()));
    }

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<Map<String, String>> conflicto(RuntimeException e) {
        return ResponseEntity.status(409).body(Map.of("error", e.getMessage()));
    }
}
