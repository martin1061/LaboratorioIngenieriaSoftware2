/*
 * Autores: Britney Benítez, Juan Manuel García,
 *          Yineidy Mamián Jiménez, Martín Silva Cabrera
 * Laboratorio de Ingeniería de Software II - Universidad del Cauca
 */
package co.edu.unicauca.validation.service;

import co.edu.unicauca.validation.dto.Dtos.*;
import org.springframework.stereotype.Service;
import java.util.*;

/** Reglas de validación de una pregunta de selección múltiple. */
@Service
public class ValidationService {

    public ValidationResult validar(ValidationRequest r) {
        List<String> errores = new ArrayList<>();

        if (r == null) return new ValidationResult(false, List.of("Solicitud vacía"));

        // 1. El enunciado es obligatorio
        if (r.text() == null || r.text().isBlank()) errores.add("El enunciado es obligatorio");

        // 2. Deben existir exactamente 4 opciones
        List<OptionDTO> ops = r.options();
        if (ops == null || ops.size() != 4) {
            errores.add("Debe haber exactamente 4 opciones");
        } else {
            Set<String> ids = new HashSet<>();
            Set<String> textos = new HashSet<>();
            for (OptionDTO o : ops) {
                if (o.id() == null || o.id().isBlank()) errores.add("Toda opción necesita un id");
                else if (!ids.add(o.id())) errores.add("Id de opción repetido: " + o.id());
                if (o.text() == null || o.text().isBlank()) errores.add("Toda opción necesita texto");
                else if (!textos.add(o.text().trim().toLowerCase())) errores.add("Opción repetida: " + o.text());
            }
            // 3. La clave debe ser una de las opciones
            if (r.correctOptionId() == null || ops.stream().noneMatch(o -> r.correctOptionId().equals(o.id())))
                errores.add("La respuesta correcta debe coincidir con el id de una opción");
        }
        return new ValidationResult(errores.isEmpty(), errores);
    }
}
