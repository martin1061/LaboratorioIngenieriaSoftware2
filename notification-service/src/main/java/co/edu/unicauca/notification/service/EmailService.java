/*
 * Autores: Britney Benítez, Juan Manuel García,
 *          Yineidy Mamián Jiménez, Martín Silva Cabrera
 * Laboratorio de Ingeniería de Software II - Universidad del Cauca
 */
package co.edu.unicauca.notification.service;

import co.edu.unicauca.notification.dto.ReviewerAssignedEvent;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/** Simula el envío (diferido) de un correo al revisor. */
@Service
public class EmailService {

    private final List<String> enviados = new CopyOnWriteArrayList<>();

    public void enviar(ReviewerAssignedEvent e) {
        try {
            Thread.sleep(1500); // simula la demora de un servidor de correo
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        String msg = "Correo al revisor " + e.reviewerId() + ": se le asignó la pregunta " + e.questionId();
        enviados.add(msg);
        System.out.println("[NotificationService] " + msg);
    }

    public List<String> listar() { return enviados; }
}
