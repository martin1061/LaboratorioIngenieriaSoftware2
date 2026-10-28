package edu.unicauca.questionbank.infrastructure.jpa.record;
import jakarta.persistence.*;
/** JPA record for topics. */
@Entity @Table(name="topics")
public class TopicRecord {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private String name;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="competency_id",nullable=false) private CompetencyRecord competency;
 protected TopicRecord(){}
 public TopicRecord(String name,CompetencyRecord competency){this.name=name;this.competency=competency;}
 public Long getId(){return id;} public String getName(){return name;} public CompetencyRecord getCompetency(){return competency;}
}