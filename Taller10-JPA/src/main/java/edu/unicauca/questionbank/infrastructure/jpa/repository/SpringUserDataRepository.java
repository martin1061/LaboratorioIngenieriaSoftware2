package edu.unicauca.questionbank.infrastructure.jpa.repository;
import edu.unicauca.questionbank.infrastructure.jpa.record.UserRecord;
import org.springframework.data.jpa.repository.JpaRepository;
/** Internal Spring Data repository for users. */
public interface SpringUserDataRepository extends JpaRepository<UserRecord,Long> {}