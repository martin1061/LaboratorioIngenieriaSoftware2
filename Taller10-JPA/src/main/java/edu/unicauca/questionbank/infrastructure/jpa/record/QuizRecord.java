package edu.unicauca.questionbank.infrastructure.jpa.record;
import jakarta.persistence.*;
import java.util.*;
@Entity @Table(name="quizzes")
/** Persistence record for quizzes. */
public class QuizRecord {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private String title;
 @Column(nullable=false) private Integer durationMinutes;
 @OneToMany(mappedBy="quiz",cascade=CascadeType.ALL,orphanRemoval=true) private List<QuizQuestionRecord> questions=new ArrayList<>();
 protected QuizRecord(){}
 public QuizRecord(String title,Integer durationMinutes){this.title=title;this.durationMinutes=durationMinutes;}
 public Long getId(){return id;} public String getTitle(){return title;} public Integer getDurationMinutes(){return durationMinutes;}
 public List<QuizQuestionRecord> getQuestions(){return questions;}
}