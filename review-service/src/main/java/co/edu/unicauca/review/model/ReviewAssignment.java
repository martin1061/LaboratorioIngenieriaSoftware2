/*
 * Autores: Britney Benítez, Juan Manuel García,
 *          Yineidy Mamián Jiménez, Martín Silva Cabrera
 * Laboratorio de Ingeniería de Software II - Universidad del Cauca
 */
package co.edu.unicauca.review.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/** Asignación de un revisor a una pregunta. */
@Entity
@Table(name = "review_assignments")
public class ReviewAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public Long questionId;
    public Long reviewerId;
    public Long assignedBy;
    public LocalDateTime assignedAt = LocalDateTime.now();
    public String status;   // PENDING, CONFIRMED, FAILED o COMPLETED
}
