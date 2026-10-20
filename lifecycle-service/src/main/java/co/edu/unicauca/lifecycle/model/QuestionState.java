/*
 * Autores: Britney Benítez, Juan Manuel García,
 *          Yineidy Mamián Jiménez, Martín Silva Cabrera
 * Laboratorio de Ingeniería de Software II - Universidad del Cauca
 */
package co.edu.unicauca.lifecycle.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/** Estado actual de una pregunta. */
@Entity
@Table(name = "question_states")
public class QuestionState {
    @Id
    public Long questionId;
    @Enumerated(EnumType.STRING)
    public QuestionStatus state;
    public LocalDateTime changedAt;
    @Column(name = "comentario", length = 1000)
    public String comment;
}
