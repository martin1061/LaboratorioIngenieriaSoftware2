/*
 * Autores: Britney Benítez, Juan Manuel García,
 *          Yineidy Mamián Jiménez, Martín Silva Cabrera
 * Laboratorio de Ingeniería de Software II - Universidad del Cauca
 */
package co.edu.unicauca.question.dto;

import java.util.List;

/** DTOs y mensajes del Question Service. */
public class Dtos {
    public record OptionDTO(String id, String text) {}
    public record CreateQuestionRequest(String text, List<OptionDTO> options, String correctOptionId, Long authorId) {}
    public record CreateQuestionResponse(Long id, String status, String message) {}
    // Se envían al Validation Service
    public record ValidationRequest(String text, List<OptionDTO> options, String correctOptionId) {}
    public record ValidationResult(boolean valid, List<String> errors) {}
    // Comando que se publica en RabbitMQ hacia Lifecycle
    public record SubmitCommand(String operationId, Long questionId, Long requestedBy, String requestedAt) {}
    // Resultado que Lifecycle devuelve por RabbitMQ
    public record OperationResultMessage(String operationId, Long questionId, String status, String message, String completedAt) {}
}
