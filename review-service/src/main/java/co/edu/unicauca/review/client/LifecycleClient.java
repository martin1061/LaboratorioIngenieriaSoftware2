/*
 * Autores: Britney Benítez, Juan Manuel García,
 *          Yineidy Mamián Jiménez, Martín Silva Cabrera
 * Laboratorio de Ingeniería de Software II - Universidad del Cauca
 */
package co.edu.unicauca.review.client;

import co.edu.unicauca.review.dto.Dtos.TransitionRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.*;
import java.util.Map;

/** Cliente REST del Lifecycle Service. */
@Component
public class LifecycleClient {

    private final RestTemplate rest;
    private final String url;

    public LifecycleClient(RestTemplate rest, @Value("${app.services.lifecycle-url}") String url) {
        this.rest = rest;
        this.url = url;
    }

    /** Devuelve el estado actual o null si la pregunta no existe. */
    public String obtenerEstado(Long questionId) {
        try {
            Map<?, ?> m = rest.getForObject(url + "/lifecycle/" + questionId, Map.class);
            return m == null ? null : String.valueOf(m.get("state"));
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        }
    }

    /** Solicita una transición; lanza IllegalStateException si falla. */
    public void transicionar(Long questionId, String nuevoEstado, String comentario) {
        try {
            rest.postForEntity(url + "/lifecycle/" + questionId + "/transition",
                    new TransitionRequest(nuevoEstado, comentario), String.class);
        } catch (HttpStatusCodeException e) {
            throw new IllegalStateException("Lifecycle rechazó la transición: " + e.getResponseBodyAsString());
        } catch (RestClientException e) {
            throw new IllegalStateException("Lifecycle Service no disponible: " + e.getMessage());
        }
    }
}
