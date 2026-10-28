package edu.unicauca.questionbank.infrastructure.jpa.record;
import jakarta.persistence.*;
@Entity @Table(name="quiz_questions",uniqueConstraints=@UniqueConstraint(columnNames={"quiz_id","question_id"}))
/** Associative entity between a quiz and a question. */
public class QuizQuestionRecord {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="quiz_id",nullable=false) private QuizRecord quiz;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="question_id",nullable=false) private QuestionRecord question;
 @Column(nullable=false) private Integer position;
 protected QuizQuestionRecord(){}
 public QuizQuestionRecord(QuizRecord quiz,QuestionRecord question,Integer position){this.quiz=quiz;this.question=question;this.position=position;}
}