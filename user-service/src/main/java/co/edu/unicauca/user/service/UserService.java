/*
 * Autores: Britney Benítez, Juan Manuel García,
 *          Yineidy Mamián Jiménez, Martín Silva Cabrera
 * Laboratorio de Ingeniería de Software II - Universidad del Cauca
 */
package co.edu.unicauca.user.service;

import co.edu.unicauca.user.dto.Dtos.*;
import org.springframework.stereotype.Service;
import java.util.*;

/** Lógica de autenticación con usuarios de prueba en memoria. */
@Service
public class UserService {

    // Usuario junto con su contraseña (solo para este taller)
    private record Registro(UserDTO user, String password) {}

    private final Map<Long, Registro> usuarios = new HashMap<>();

    public UserService() {
        agregar(1L, "docente", "docente123", "Docente Demo", List.of("DOCENTE"));
        agregar(2L, "admin", "admin123", "Administrador Demo", List.of("ADMIN"));
        agregar(3L, "revisor1", "revisor123", "Revisor Uno", List.of("REVISOR"));
        agregar(4L, "revisor2", "revisor123", "Revisor Dos", List.of("REVISOR"));
    }

    private void agregar(Long id, String user, String pass, String nombre, List<String> roles) {
        usuarios.put(id, new Registro(new UserDTO(id, user, nombre, user + "@unicauca.edu.co", roles), pass));
    }

    /** Verifica usuario y contraseña. */
    public LoginResponse autenticar(LoginRequest req) {
        if (req == null || req.username() == null || req.password() == null)
            return new LoginResponse(false, null, "Credenciales incompletas");
        return usuarios.values().stream()
                .filter(r -> r.user().username().equals(req.username()) && r.password().equals(req.password()))
                .findFirst()
                .map(r -> new LoginResponse(true, r.user(), "Autenticación exitosa"))
                .orElse(new LoginResponse(false, null, "Usuario o contraseña incorrectos"));
    }

    /** Busca un usuario por id (lo usa Review Service). */
    public Optional<UserDTO> buscar(Long id) {
        return Optional.ofNullable(usuarios.get(id)).map(Registro::user);
    }
}
