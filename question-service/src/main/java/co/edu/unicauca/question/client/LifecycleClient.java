/*
 * Autores: Britney Benítez, Juan Manuel García,
 *          Yineidy Mamián Jiménez, Martín Silva Cabrera
 * Laboratorio de Ingeniería de Software II - Universidad del Cauca
 */
package co.edu.unicauca.question.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/** Llamada REST a Lifecycle Service para registrar el estado inicial DRAFT. */
@Component
public class LifecycleClient {

    private final RestTemplate rest;
    private final String url;

    public LifecycleClient(RestTemplate rest, @Value("${app.services.lifecycle-url}") String url) {
        this.rest = rest;
        this.url = url;
    }

    public void registrarBorrador(Long questionId) {
        rest.postForEntity(url + "/lifecycle/" + questionId + "/init", null, String.class);
    }
}
