package edu.unicauca.questionbank.infrastructure.jpa.record;
import jakarta.persistence.*;
/** Specialized persistence record for multiple-choice questions. */
@Entity @Table(name="multiple_choice_questions")
@PrimaryKeyJoinColumn(name="question_id")
public class MultipleChoiceQuestionRecord extends QuestionRecord {
 protected MultipleChoiceQuestionRecord(){}
 public MultipleChoiceQuestionRecord(String context,String statement,String justification,String bibliography,
   edu.unicauca.questionbank.domain.Difficulty difficulty,edu.unicauca.questionbank.domain.QuestionStatus status,UserRecord author,TopicRecord topic){
   super(context,statement,justification,bibliography,difficulty,status,author,topic);
 }
}