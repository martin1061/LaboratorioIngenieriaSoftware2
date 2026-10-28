package edu.unicauca.questionbank.infrastructure.jpa.repository;
import edu.unicauca.questionbank.infrastructure.jpa.record.TopicRecord;
import org.springframework.data.jpa.repository.JpaRepository;
/** Internal Spring Data repository for topics. */
public interface SpringTopicDataRepository extends JpaRepository<TopicRecord,Long> {}