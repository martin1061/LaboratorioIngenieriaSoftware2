package co.edu.unicauca.saberpro.infrastructure.outbound.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "preguntas")
@Getter @Setter
public class QuestionJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String enunciado;

    @Column(nullable = false, length = 1000)
    private String justificacion;

    @Column(nullable = false)
    private String estado;

    // Almacenaremos las opciones serializadas o en tabla asociada (simplificado para el taller)
    @Lob
    private String opcionesJson;
}