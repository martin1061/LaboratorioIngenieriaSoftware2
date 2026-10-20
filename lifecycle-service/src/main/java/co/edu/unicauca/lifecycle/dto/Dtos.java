/*
 * Autores: Britney Benítez, Juan Manuel García,
 *          Yineidy Mamián Jiménez, Martín Silva Cabrera
 * Laboratorio de Ingeniería de Software II - Universidad del Cauca
 */
package co.edu.unicauca.lifecycle.dto;

/** DTOs y mensajes del Lifecycle Service. */
public class Dtos {
    public record SubmitCommand(String operationId, Long questionId, Long requestedBy, String requestedAt) {}
    public record OperationResultMessage(String operationId, Long questionId, String status, String message, String completedAt) {}
    public record TransitionRequest(String newState, String comment) {}
}
