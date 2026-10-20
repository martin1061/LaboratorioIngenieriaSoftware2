/*
 * Autores: Britney Benítez, Juan Manuel García,
 *          Yineidy Mamián Jiménez, Martín Silva Cabrera
 * Laboratorio de Ingeniería de Software II - Universidad del Cauca
 */
package co.edu.unicauca.review;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/** Clase principal del microservicio. */
@SpringBootApplication
public class ReviewServiceApplication {
    public static void main(String[] args) { SpringApplication.run(ReviewServiceApplication.class, args); }

    // Cliente HTTP para llamar a otros microservicios
    @Bean
    public RestTemplate restTemplate() { return new RestTemplate(); }
}
