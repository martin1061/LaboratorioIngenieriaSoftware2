package edu.unicauca.questionbank.infrastructure.jpa.record;
import edu.unicauca.questionbank.domain.*;
import jakarta.persistence.*;
import java.util.*;
/** JPA-specific record; domain classes do not depend on Jakarta Persistence. */
@Entity @Table(name="questions")
@Inheritance(strategy=InheritanceType.JOINED)
public class QuestionRecord {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,length=4000) private String context;
 @Column(nullable=false,length=2000) private String statement;
 @Column(length=4000) private String justification;
 @Column(length=4000) private String bibliography;
 @Enumerated(EnumType.STRING) @Column(nullable=false) private Difficulty difficulty;
 @Enumerated(EnumType.STRING) @Column(nullable=false) private QuestionStatus status;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="author_id",nullable=false) private UserRecord author;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="topic_id",nullable=false) private TopicRecord topic;
 @ManyToMany @JoinTable(name="question_competencies",joinColumns=@JoinColumn(name="question_id"),inverseJoinColumns=@JoinColumn(name="competency_id"))
 private Set<CompetencyRecord> competencies=new HashSet<>();
 @OneToOne(mappedBy="question",cascade=CascadeType.ALL,orphanRemoval=true) private QuestionMetadataRecord metadata;
 @OneToMany(mappedBy="question",cascade=CascadeType.ALL,orphanRemoval=true) private List<AnswerOptionRecord> options=new ArrayList<>();
 protected QuestionRecord(){}
 public QuestionRecord(String context,String statement,String justification,String bibliography,Difficulty difficulty,QuestionStatus status,UserRecord author,TopicRecord topic){
  this.context=context;this.statement=statement;this.justification=justification;this.bibliography=bibliography;this.difficulty=difficulty;this.status=status;this.author=author;this.topic=topic;
 }
 public Long getId(){return id;} public String getContext(){return context;} public String getStatement(){return statement;}
 public String getJustification(){return justification;} public String getBibliography(){return bibliography;}
 public Difficulty getDifficulty(){return difficulty;} public QuestionStatus getStatus(){return status;}
 public UserRecord getAuthor(){return author;} public TopicRecord getTopic(){return topic;}
 public Set<CompetencyRecord> getCompetencies(){return competencies;} public List<AnswerOptionRecord> getOptions(){return options;}
 public void setStatus(QuestionStatus status){this.status=status;}
}