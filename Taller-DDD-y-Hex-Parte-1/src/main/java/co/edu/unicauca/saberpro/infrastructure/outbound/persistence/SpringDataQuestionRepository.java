package co.edu.unicauca.saberpro.infrastructure.outbound.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataQuestionRepository extends JpaRepository<QuestionJpaEntity, Long> {
}