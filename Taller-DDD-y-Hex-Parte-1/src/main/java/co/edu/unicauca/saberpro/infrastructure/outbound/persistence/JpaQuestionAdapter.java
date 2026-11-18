package co.edu.unicauca.saberpro.infrastructure.outbound.persistence;

import co.edu.unicauca.saberpro.domain.ports.outbound.PreguntaRepositoryPort;
import co.edu.unicauca.saberpro.domain.model.EstadoPregunta;
import co.edu.unicauca.saberpro.domain.model.OpcionRespuesta;
import co.edu.unicauca.saberpro.domain.model.Pregunta;
import co.edu.unicauca.saberpro.domain.valueobject.Enunciado;
import co.edu.unicauca.saberpro.domain.valueobject.Justificacion;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component

public class JpaQuestionAdapter implements PreguntaRepositoryPort {

    private final SpringDataQuestionRepository repository;

    public JpaQuestionAdapter(SpringDataQuestionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Pregunta save(Pregunta pregunta) {
        QuestionJpaEntity entity = new QuestionJpaEntity();
        entity.setId(pregunta.getId());
        entity.setEnunciado(pregunta.getEnunciado().getTexto());
        entity.setJustificacion(pregunta.getJustificacion().getTexto());
        entity.setEstado(pregunta.getEstado().name());
        
        String opcionesSerialized = pregunta.getOpciones().stream()
                .map(o -> o.getTexto() + "::" + o.isEsCorrecta())
                .collect(Collectors.joining("||"));
        entity.setOpcionesJson(opcionesSerialized);

        QuestionJpaEntity saved = repository.save(entity);
        pregunta.setId(saved.getId());
        return pregunta;
    }

    @Override
    public Optional<Pregunta> findById(Long id) {
        return repository.findById(id).map(this.toDomain);
    }

    @Override
    public List<Pregunta> findAll() {
        return repository.findAll().stream().map(this.toDomain).collect(Collectors.toList());
    }

    private final java.util.function.Function<QuestionJpaEntity, Pregunta> toDomain = entity -> {
        Pregunta p = new Pregunta(
                entity.getId(),
                new Enunciado(entity.getEnunciado()),
                new Justificacion(entity.getJustificacion())
        );
        if (entity.getOpcionesJson() != null && !entity.getOpcionesJson().isEmpty()) {
            String[] opts = entity.getOpcionesJson().split("\\|\\|");
            for (String opt : opts) {
                String[] parts = opt.split("::");
                if (parts.length == 2) {
                    p.agregarOpcion(parts[0], Boolean.parseBoolean(parts[1]));
                }
            }
        }
        return p;
    };
}