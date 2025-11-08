package com.streamshield.repo;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "audit_records")
public class AuditRecordEntity {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(columnDefinition="TEXT")
  private String originalText;

  @Column(columnDefinition="TEXT")
  private String transformedText;

  @Column(columnDefinition="TEXT")
  private String entitiesJson;

  private double documentScore;

  private Instant createdAt = Instant.now();

  // getters/setters
  public Long getId(){return id;}
  public String getOriginalText(){return originalText;}
  public void setOriginalText(String s){this.originalText=s;}
  public String getTransformedText(){return transformedText;}
  public void setTransformedText(String s){this.transformedText=s;}
  public String getEntitiesJson(){return entitiesJson;}
  public void setEntitiesJson(String s){this.entitiesJson=s;}
  public double getDocumentScore(){return documentScore;}
  public void setDocumentScore(double d){this.documentScore=d;}
  public Instant getCreatedAt(){return createdAt;}
  public void setCreatedAt(Instant t){this.createdAt=t;}
}
