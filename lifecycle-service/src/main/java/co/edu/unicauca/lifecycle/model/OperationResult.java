/*
 * Autores: Britney Benítez, Juan Manuel García,
 *          Yineidy Mamián Jiménez, Martín Silva Cabrera
 * Laboratorio de Ingeniería de Software II - Universidad del Cauca
 */
package co.edu.unicauca.lifecycle.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/** Registro del resultado de cada comando procesado. */
@Entity
@Table(name = "operation_results")
public class OperationResult {
    @Id
    public String operationId;
    public Long questionId;
    public String type;
    public String status;   // COMPLETED o FAILED
    @Column(length = 1000)
    public String message;
    public LocalDateTime createdAt;
    public LocalDateTime completedAt;
}
