package edu.unicauca.questionbank.infrastructure.jpa.record;
import jakarta.persistence.*;
import java.time.LocalDate;
@Entity @Table(name="question_metadata")
/** One-to-one metadata persistence record. */
public class QuestionMetadataRecord {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @OneToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="question_id",nullable=false,unique=true) private QuestionRecord question;
 private String cognitiveLevel;
 private Integer estimatedSeconds;
 private LocalDate lastReviewDate;
 protected QuestionMetadataRecord(){}
 public QuestionMetadataRecord(QuestionRecord question,String cognitiveLevel,Integer estimatedSeconds){this.question=question;this.cognitiveLevel=cognitiveLevel;this.estimatedSeconds=estimatedSeconds;}
}