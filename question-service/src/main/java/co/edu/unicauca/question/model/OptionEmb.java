/*
 * Autores: Britney Benítez, Juan Manuel García,
 *          Yineidy Mamián Jiménez, Martín Silva Cabrera
 * Laboratorio de Ingeniería de Software II - Universidad del Cauca
 */
package co.edu.unicauca.question.model;

import jakarta.persistence.*;

/** Opción de respuesta (se guarda embebida en la pregunta). */
@Embeddable
public class OptionEmb {
    @Column(name = "option_id")
    public String optionId;
    @Column(name = "option_text", length = 1000)
    public String optionText;

    public OptionEmb() {}
    public OptionEmb(String id, String text) { this.optionId = id; this.optionText = text; }
}
