/*
 * Autores: Britney Benítez, Juan Manuel García,
 *          Yineidy Mamián Jiménez, Martín Silva Cabrera
 * Laboratorio de Ingeniería de Software II - Universidad del Cauca
 */
package co.edu.unicauca.review.repository;

import co.edu.unicauca.review.model.ReviewAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface AssignmentRepository extends JpaRepository<ReviewAssignment, Long> {
    boolean existsByQuestionIdAndStatusIn(Long questionId, Collection<String> statuses);
    Optional<ReviewAssignment> findFirstByQuestionIdAndStatus(Long questionId, String status);
    List<ReviewAssignment> findByQuestionId(Long questionId);
}
