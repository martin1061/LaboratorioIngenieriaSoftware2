package edu.unicauca.questionbank.infrastructure.jpa.record;
import jakarta.persistence.*;
/** JPA record for system users. */
@Entity @Table(name="users")
public class UserRecord {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private String fullName;
 @Column(nullable=false,unique=true) private String email;
 @Column(nullable=false) private String role;
 protected UserRecord(){}
 public UserRecord(String fullName,String email,String role){this.fullName=fullName;this.email=email;this.role=role;}
 public Long getId(){return id;} public String getFullName(){return fullName;} public String getEmail(){return email;} public String getRole(){return role;}
}