/*
 * Autores: Britney Benítez, Juan Manuel García,
 *          Yineidy Mamián Jiménez, Martín Silva Cabrera
 * Laboratorio de Ingeniería de Software II - Universidad del Cauca
 */
package co.edu.unicauca.question.client;

import co.edu.unicauca.question.dto.Dtos.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

/** Llamada REST síncrona al Validation Service. */
@Component
public class ValidationClient {

    private final RestTemplate rest;
    private final String url;

    public ValidationClient(RestTemplate rest, @Value("${app.services.validation-url}") String url) {
        this.rest = rest;
        this.url = url;
    }

    public ValidationResult validar(ValidationRequest req) {
        try {
            return rest.postForObject(url + "/validate", req, ValidationResult.class);
        } catch (RestClientException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Validation Service no disponible");
        }
    }
}
