/*
 * Autores: Britney Benítez, Juan Manuel García,
 *          Yineidy Mamián Jiménez, Martín Silva Cabrera
 * Laboratorio de Ingeniería de Software II - Universidad del Cauca
 */
package co.edu.unicauca.validation.controller;

import co.edu.unicauca.validation.dto.Dtos.*;
import co.edu.unicauca.validation.service.ValidationService;
import org.springframework.web.bind.annotation.*;

/** Endpoint síncrono POST /validate. */
@RestController
public class ValidationController {

    private final ValidationService service;

    public ValidationController(ValidationService service) { this.service = service; }

    @PostMapping("/validate")
    public ValidationResult validar(@RequestBody ValidationRequest req) {
        return service.validar(req);
    }
}
