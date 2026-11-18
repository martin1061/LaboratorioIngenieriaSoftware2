package co.edu.unicauca.saberpro.application.port.inbound;

import co.edu.unicauca.saberpro.domain.model.Pregunta;
import java.util.List;

public interface CrearPreguntaUseCase {
    Pregunta crearPreguntaBorrador(String enunciadoStr, String justificacionStr);
    Pregunta agregarOpcion(Long preguntaId, String texto, boolean esCorrecta);
    Pregunta enviarARevision(Long preguntaId);
    List<Pregunta> listarPreguntas();
}