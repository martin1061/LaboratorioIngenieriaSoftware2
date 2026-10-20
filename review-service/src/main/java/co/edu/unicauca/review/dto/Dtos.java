/*
 * Autores: Britney Benítez, Juan Manuel García,
 *          Yineidy Mamián Jiménez, Martín Silva Cabrera
 * Laboratorio de Ingeniería de Software II - Universidad del Cauca
 */
package co.edu.unicauca.review.dto;

import java.util.List;

/** DTOs y eventos del Review Service. */
public class Dtos {
    public record AssignRequest(Long questionId, Long reviewerId, Long assignedBy) {}
    public record ReviewResultRequest(String result, String comments) {}
    public record TransitionRequest(String newState, String comment) {}
    public record UserDTO(Long id, String username, String fullName, String email, List<String> roles) {}
    // Evento publicado hacia Notification Service
    public record ReviewerAssignedEvent(Long questionId, Long reviewerId, Long assignedBy, String occurredAt) {}
}
