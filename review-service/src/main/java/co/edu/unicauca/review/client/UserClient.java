/*
 * Autores: Britney Benítez, Juan Manuel García,
 *          Yineidy Mamián Jiménez, Martín Silva Cabrera
 * Laboratorio de Ingeniería de Software II - Universidad del Cauca
 */
package co.edu.unicauca.review.client;

import co.edu.unicauca.review.dto.Dtos.UserDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.*;

/** Cliente REST del User Service. */
@Component
public class UserClient {

    private final RestTemplate rest;
    private final String url;

    public UserClient(RestTemplate rest, @Value("${app.services.user-url}") String url) {
        this.rest = rest;
        this.url = url;
    }

    /** Devuelve el usuario o null si no existe. */
    public UserDTO buscar(Long id) {
        try {
            return rest.getForObject(url + "/users/" + id, UserDTO.class);
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        }
    }
}
