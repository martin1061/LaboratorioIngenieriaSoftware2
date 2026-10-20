/*
 * Autores: Britney Benítez, Juan Manuel García,
 *          Yineidy Mamián Jiménez, Martín Silva Cabrera
 * Laboratorio de Ingeniería de Software II - Universidad del Cauca
 */
package co.edu.unicauca.user.dto;

import java.util.List;

/** DTOs del servicio de usuarios. */
public class Dtos {
    public record LoginRequest(String username, String password) {}
    public record UserDTO(Long id, String username, String fullName, String email, List<String> roles) {}
    public record LoginResponse(boolean authenticated, UserDTO user, String message) {}
}
