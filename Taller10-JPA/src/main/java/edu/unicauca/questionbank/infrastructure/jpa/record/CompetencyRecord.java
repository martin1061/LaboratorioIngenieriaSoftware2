package edu.unicauca.questionbank.infrastructure.jpa.record;

import jakarta.persistence.*;

@Entity
@Table(name = "competencies")
public class CompetencyRecord {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    protected CompetencyRecord() {}

    public CompetencyRecord(String name) {
        this.name = name;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
}