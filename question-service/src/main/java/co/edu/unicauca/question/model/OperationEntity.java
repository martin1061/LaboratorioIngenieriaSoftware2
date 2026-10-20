/*
 * Autores: Britney Benítez, Juan Manuel García,
 *          Yineidy Mamián Jiménez, Martín Silva Cabrera
 * Laboratorio de Ingeniería de Software II - Universidad del Cauca
 */
package co.edu.unicauca.question.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/** Seguimiento de una operación asíncrona (consultable por GET /operations/{id}). */
@Entity
@Table(name = "operations")
public class OperationEntity {
    @Id
    public String operationId;
    public Long questionId;
    public String status;      // PENDING, COMPLETED o FAILED
    @Column(length = 1000)
    public String message;
    public LocalDateTime createdAt = LocalDateTime.now();
    public LocalDateTime completedAt;
}
