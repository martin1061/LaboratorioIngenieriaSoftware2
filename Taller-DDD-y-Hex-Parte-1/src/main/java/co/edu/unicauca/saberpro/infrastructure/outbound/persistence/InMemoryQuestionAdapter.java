package co.edu.unicauca.saberpro.infrastructure.outbound.persistence;

import co.edu.unicauca.saberpro.domain.ports.outbound.PreguntaRepositoryPort;
import co.edu.unicauca.saberpro.domain.model.Pregunta;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Component("inMemoryAdapter")
@Primary
public class InMemoryQuestionAdapter implements PreguntaRepositoryPort {

    private final Map<Long, Pregunta> storage = new HashMap<>();
    private final AtomicLong sequence = new AtomicLong(1L);

    @Override
    public Pregunta save(Pregunta pregunta) {
        if (pregunta.getId() == null) {
            pregunta.setId(sequence.getAndIncrement());
        }
        storage.put(pregunta.getId(), pregunta);
        return pregunta;
    }

    @Override
    public Optional<Pregunta> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Pregunta> findAll() {
        return new ArrayList<>(storage.values());
    }
}