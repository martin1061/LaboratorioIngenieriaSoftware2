/*
 * Autores: Britney Benítez, Juan Manuel García,
 *          Yineidy Mamián Jiménez, Martín Silva Cabrera
 * Laboratorio de Ingeniería de Software II - Universidad del Cauca
 */
package co.edu.unicauca.review.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/** Resultado de una revisión. */
@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public Long questionId;
    public Long reviewerId;
    @Column(name = "resultado")
    public String result;   // APPROVED o REJECTED
    @Column(name = "comentarios", length = 1000)
    public String comments;
    public LocalDateTime createdAt = LocalDateTime.now();
}
