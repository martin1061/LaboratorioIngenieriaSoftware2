package edu.unicauca.questionbank.infrastructure.jpa;
import edu.unicauca.questionbank.domain.*;
import edu.unicauca.questionbank.domain.repository.QuestionRepository;
import edu.unicauca.questionbank.infrastructure.jpa.record.*;
import edu.unicauca.questionbank.infrastructure.jpa.repository.*;
import org.springframework.stereotype.Repository;
import java.util.*;
/** JPA implementation of the domain question repository contract. */
@Repository
public class QuestionRepositoryJpa implements QuestionRepository {
 private final SpringQuestionDataRepository data;
 private final SpringUserDataRepository users;
 private final SpringTopicDataRepository topics;
 public QuestionRepositoryJpa(SpringQuestionDataRepository data,SpringUserDataRepository users,SpringTopicDataRepository topics){this.data=data;this.users=users;this.topics=topics;}
 @Override public Question save(Question q){
  UserRecord author=users.findById(q.getAuthorId()).orElseThrow(()->new IllegalArgumentException("Author not found: "+q.getAuthorId()));
  TopicRecord topic=topics.findById(q.getTopicId()).orElseThrow(()->new IllegalArgumentException("Topic not found: "+q.getTopicId()));
  QuestionRecord r=new QuestionRecord(q.getContext(),q.getStatement(),q.getJustification(),q.getBibliography(),q.getDifficulty(),q.getStatus(),author,topic);
  r=data.save(r);
  return toDomain(r);
 }
 @Override public Optional<Question> findById(Long id){return data.findById(id).map(this::toDomain);}
 @Override public List<Question> findAll(){return data.findAll().stream().map(this::toDomain).toList();}
 private Question toDomain(QuestionRecord r){return new Question(r.getId(),r.getContext(),r.getStatement(),r.getJustification(),r.getBibliography(),r.getDifficulty(),r.getStatus(),r.getAuthor().getId(),r.getTopic().getId());}
}