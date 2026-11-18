package co.edu.unicauca.saberpro.domain.ports.outbound;

import co.edu.unicauca.saberpro.domain.model.Pregunta;
import java.util.List;
import java.util.Optional;

public interface PreguntaRepositoryPort {
    Pregunta save(Pregunta pregunta);
    Optional<Pregunta> findById(Long id);
    List<Pregunta> findAll();
}