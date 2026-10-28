package edu.unicauca.questionbank.infrastructure.jpa.repository;
import edu.unicauca.questionbank.infrastructure.jpa.record.QuestionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
/** Internal Spring Data repository. */
public interface SpringQuestionDataRepository extends JpaRepository<QuestionRecord,Long> {}