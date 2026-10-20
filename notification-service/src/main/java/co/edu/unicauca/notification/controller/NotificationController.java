/*
 * Autores: Britney Benítez, Juan Manuel García,
 *          Yineidy Mamián Jiménez, Martín Silva Cabrera
 * Laboratorio de Ingeniería de Software II - Universidad del Cauca
 */
package co.edu.unicauca.notification.controller;

import co.edu.unicauca.notification.service.EmailService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/** GET /notifications: permite comprobar qué notificaciones se enviaron. */
@RestController
public class NotificationController {

    private final EmailService email;

    public NotificationController(EmailService email) { this.email = email; }

    @GetMapping("/notifications")
    public List<String> listar() { return email.listar(); }
}
