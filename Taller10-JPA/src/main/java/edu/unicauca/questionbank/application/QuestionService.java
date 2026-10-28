package edu.unicauca.questionbank.application;
import edu.unicauca.questionbank.domain.*;
import edu.unicauca.questionbank.domain.repository.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
/** Application use cases for question creation and lifecycle transitions. */
@Service
public class QuestionService {
 private final QuestionRepository questions;
 public QuestionService(QuestionRepository questions){this.questions=questions;}
 @Transactional public Question create(String context,String statement,String justification,String bibliography,Difficulty difficulty,Long authorId,Long topicId){
  if(context==null||context.isBlank()||statement==null||statement.isBlank()) throw new IllegalArgumentException("Context and statement are required.");
  return questions.save(new Question(null,context,statement,justification,bibliography,difficulty,QuestionStatus.DRAFT,authorId,topicId));
 }
 @Transactional(readOnly=true) public Question find(Long id){return questions.findById(id).orElseThrow(()->new IllegalArgumentException("Question not found: "+id));}
 @Transactional(readOnly=true) public List<Question> findAll(){return questions.findAll();}
}