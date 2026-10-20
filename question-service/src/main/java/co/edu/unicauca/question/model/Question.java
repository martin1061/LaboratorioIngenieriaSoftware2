/*
 * Autores: Britney Benítez, Juan Manuel García,
 *          Yineidy Mamián Jiménez, Martín Silva Cabrera
 * Laboratorio de Ingeniería de Software II - Universidad del Cauca
 */
package co.edu.unicauca.question.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

/** Pregunta. El estado NO se guarda aquí: lo gobierna Lifecycle Service. */
@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "enunciado", length = 2000)
    public String text;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "question_options", joinColumns = @JoinColumn(name = "question_id"))
    public List<OptionEmb> options = new ArrayList<>();

    public String correctOptionId;
    public Long authorId;
    public LocalDateTime createdAt = LocalDateTime.now();
}
