/*
 * Autores: Britney Benítez, Juan Manuel García,
 *          Yineidy Mamián Jiménez, Martín Silva Cabrera
 * Laboratorio de Ingeniería de Software II - Universidad del Cauca
 */
package co.edu.unicauca.user.controller;

import co.edu.unicauca.user.dto.Dtos.*;
import co.edu.unicauca.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** Endpoints REST de autenticación y consulta de usuarios. */
@RestController
public class AuthController {

    private final UserService service;

    public AuthController(UserService service) { this.service = service; }

    /** POST /auth/login -> 200 si es válido, 401 si no. */
    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest req) {
        LoginResponse r = service.autenticar(req);
        return r.authenticated() ? ResponseEntity.ok(r) : ResponseEntity.status(401).body(r);
    }

    /** GET /users/{id} -> datos y roles del usuario. */
    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> obtener(@PathVariable Long id) {
        return service.buscar(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
