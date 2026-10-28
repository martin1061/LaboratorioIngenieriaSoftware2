package edu.unicauca.questionbank.infrastructure.jpa.record;
import jakarta.persistence.*;
/** JPA record for one answer option. */
@Entity @Table(name="answer_options")
public class AnswerOptionRecord {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,length=1000) private String text;
 @Column(nullable=false) private boolean correct;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="question_id",nullable=false) private QuestionRecord question;
 protected AnswerOptionRecord(){}
 public AnswerOptionRecord(String text,boolean correct,QuestionRecord question){this.text=text;this.correct=correct;this.question=question;}
 public Long getId(){return id;} public String getText(){return text;} public boolean isCorrect(){return correct;}
}