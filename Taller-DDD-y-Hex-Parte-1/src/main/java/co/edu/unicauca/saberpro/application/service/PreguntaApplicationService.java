package co.edu.unicauca.saberpro.application.service;

import co.edu.unicauca.saberpro.domain.ports.inbound.CrearPreguntaUseCase;
import co.edu.unicauca.saberpro.domain.ports.outbound.PreguntaRepositoryPort;
import co.edu.unicauca.saberpro.domain.exception.ReglaNegocioException;
import co.edu.unicauca.saberpro.domain.model.Pregunta;
import co.edu.unicauca.saberpro.domain.valueobject.Enunciado;
import co.edu.unicauca.saberpro.domain.valueobject.Justificacion;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PreguntaApplicationService implements CrearPreguntaUseCase {

    private final PreguntaRepositoryPort repositoryPort;

    public PreguntaApplicationService(PreguntaRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    @Override
    public Pregunta crearPreguntaBorrador(String enunciadoStr, String justificacionStr) {
        Enunciado enunciado = new Enunciado(enunciadoStr);
        Justificacion justificacion = new Justificacion(justificacionStr);
        Pregunta pregunta = new Pregunta(null, enunciado, justificacion);
        return repositoryPort.save(pregunta);
    }

    @Override
    public Pregunta agregarOpcion(Long preguntaId, String texto, boolean esCorrecta) {
        Pregunta pregunta = repositoryPort.findById(preguntaId)
                .orElseThrow(() -> new ReglaNegocioException("Pregunta no encontrada con ID: " + preguntaId));
        pregunta.agregarOpcion(texto, esCorrecta);
        return repositoryPort.save(pregunta);
    }

    @Override
    public Pregunta enviarARevision(Long preguntaId) {
        Pregunta pregunta = repositoryPort.findById(preguntaId)
                .orElseThrow(() -> new ReglaNegocioException("Pregunta no encontrada con ID: " + preguntaId));
        pregunta.enviarARevision();
        return repositoryPort.save(pregunta);
    }

    @Override
    public List<Pregunta> listarPreguntas() {
        return repositoryPort.findAll();
    }
}