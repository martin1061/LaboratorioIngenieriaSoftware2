package co.edu.unicauca.saberpro.domain;

import co.edu.unicauca.saberpro.domain.exception.ReglaNegocioException;
import co.edu.unicauca.saberpro.domain.model.EstadoPregunta;
import co.edu.unicauca.saberpro.domain.model.Pregunta;
import co.edu.unicauca.saberpro.domain.valueobject.Enunciado;
import co.edu.unicauca.saberpro.domain.valueobject.Justificacion;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PreguntaTest { //sin @SpringBootTest

    @Test
    void debeCrearPreguntaEnBorradorExitosamente() {
        Enunciado enunciado = new Enunciado("¿Cuál es la capital del Cauca?");
        Justificacion justificacion = new Justificacion("Pregunta oficial evaluada en el componente de ciencias sociales y geografía.");
        
        Pregunta pregunta = new Pregunta(1L, enunciado, justificacion);

        assertNotNull(pregunta);
        assertEquals(EstadoPregunta.BORRADOR, pregunta.getEstado());
        assertEquals(0, pregunta.getOpciones().size());
    }

    @Test
    void debeFallarSiJustificacionEsMuyCorta() {
        Exception exception = assertThrows(ReglaNegocioException.class, () -> {
            new Justificacion("Muy corta");
        });

        assertTrue(exception.getMessage().contains("al menos 15 caracteres"));
    }

    @Test
    void debeAgregarOpcionesYPasarAPendienteRevisionCorrectamente() {
        Pregunta pregunta = new Pregunta(
            1L, 
            new Enunciado("¿Cuál es la capital del Cauca?"), 
            new Justificacion("Justificación técnica con más de quince caracteres requeridos.")
        );

        pregunta.agregarOpcion("Popayán", true);
        pregunta.agregarOpcion("Cali", false);
        pregunta.agregarOpcion("Pasto", false);
        pregunta.agregarOpcion("Bogotá", false);

        assertEquals(4, pregunta.getOpciones().size());

        pregunta.enviarARevision();
        assertEquals(EstadoPregunta.PENDIENTE_REVISION, pregunta.getEstado());
    }

    @Test
    void debeFallarAlEnviarARevisionSiNoTieneExactamenteCuatroOpciones() {
        Pregunta pregunta = new Pregunta(
            1L, 
            new Enunciado("¿Cuál es la capital del Cauca?"), 
            new Justificacion("Justificación técnica con más de quince caracteres requeridos.")
        );

        pregunta.agregarOpcion("Popayán", true);
        pregunta.agregarOpcion("Cali", false);

        Exception exception = assertThrows(ReglaNegocioException.class, () -> {
            pregunta.enviarARevision();
        });

        assertTrue(exception.getMessage().contains("exactamente 4 opciones"));
    }

    @Test
    void debeFallarAlEnviarARevisionSiTieneMasDeUnaClaveCorrecta() {
        Pregunta pregunta = new Pregunta(
            1L, 
            new Enunciado("¿Cuál es la capital del Cauca?"), 
            new Justificacion("Justificación técnica con más de quince caracteres requeridos.")
        );

        pregunta.agregarOpcion("Popayán", true);
        pregunta.agregarOpcion("Ciudad Blanca", true); // Dos correctas
        pregunta.agregarOpcion("Pasto", false);
        pregunta.agregarOpcion("Bogotá", false);

        Exception exception = assertThrows(ReglaNegocioException.class, () -> {
            pregunta.enviarARevision();
        });

        assertTrue(exception.getMessage().contains("exactamente 1 opción correcta"));
    }
}