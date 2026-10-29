package edu.unicauca.questionbank.infrastructure.jpa;

import edu.unicauca.questionbank.domain.Difficulty;
import edu.unicauca.questionbank.domain.Question;
import edu.unicauca.questionbank.domain.QuestionStatus;
import edu.unicauca.questionbank.domain.repository.QuestionRepository;
import edu.unicauca.questionbank.infrastructure.jpa.record.CompetencyRecord;
import edu.unicauca.questionbank.infrastructure.jpa.record.TopicRecord;
import edu.unicauca.questionbank.infrastructure.jpa.record.UserRecord;
import edu.unicauca.questionbank.infrastructure.jpa.repository.SpringTopicDataRepository;
import edu.unicauca.questionbank.infrastructure.jpa.repository.SpringUserDataRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import; // <--- IMPORTANTE

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(QuestionRepositoryJpa.class) // <--- ESTA ES LA LÍNEA MÁGICA QUE FALTABA
class QuestionRepositoryJpaTest {

    @Autowired private QuestionRepository questionRepository;
    @Autowired private SpringUserDataRepository userRepository;
    @Autowired private SpringTopicDataRepository topicRepository;
    @Autowired private EntityManager entityManager; 

    @Test
    void shouldSaveBasicQuestionSuccessfully() {
        // 1. Crear Competencia directamente en la BD
        CompetencyRecord competency = new CompetencyRecord("Ingeniería de Software");
        entityManager.persist(competency);

        // 2. Crear Tema (Requiere la competencia)
        TopicRecord topic = new TopicRecord("Arquitectura", competency);
        topic = topicRepository.save(topic);

        // 3. Crear Autor
        UserRecord author = new UserRecord("Test Author", "test@unicauca.edu.co", "AUTHOR");
        author = userRepository.save(author);

        // 4. Crear Pregunta de Dominio
        Question q = new Question(
            null, 
            "Contexto de prueba", 
            "¿Cuál es el principio DIP?", 
            "Justificación...", 
            "Bibliografía...",
            Difficulty.MEDIUM, 
            QuestionStatus.DRAFT, 
            author.getId(), 
            topic.getId()
        );

        // 5. Guardar usando el repositorio (Aquí se prueba la Arquitectura Limpia)
        Question saved = questionRepository.save(q);

        // 6. Verificar que se guardó correctamente
        assertNotNull(saved.getId(), "El ID no debe ser nulo después de guardar");
        assertEquals("¿Cuál es el principio DIP?", saved.getStatement(), "El enunciado debe coincidir");
        assertEquals(QuestionStatus.DRAFT, saved.getStatus(), "El estado debe ser DRAFT");
    }
}