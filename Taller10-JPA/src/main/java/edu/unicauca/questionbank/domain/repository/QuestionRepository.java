package edu.unicauca.questionbank.domain.repository;
import edu.unicauca.questionbank.domain.Question;
import java.util.List;
import java.util.Optional;
/** Technology-independent persistence contract for questions. */
public interface QuestionRepository {
    Question save(Question question);
    Optional<Question> findById(Long id);
    List<Question> findAll();
}