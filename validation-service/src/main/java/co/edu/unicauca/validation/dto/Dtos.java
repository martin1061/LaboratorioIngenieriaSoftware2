/*
 * Autores: Britney Benítez, Juan Manuel García,
 *          Yineidy Mamián Jiménez, Martín Silva Cabrera
 * Laboratorio de Ingeniería de Software II - Universidad del Cauca
 */
package co.edu.unicauca.validation.dto;

import java.util.List;

/** DTOs del servicio de validación. */
public class Dtos {
    public record OptionDTO(String id, String text) {}
    public record ValidationRequest(String text, List<OptionDTO> options, String correctOptionId) {}
    public record ValidationResult(boolean valid, List<String> errors) {}
}
