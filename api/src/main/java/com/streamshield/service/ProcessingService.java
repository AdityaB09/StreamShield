package com.streamshield.service;

import com.streamshield.dto.EntityHit;
import com.streamshield.dto.RiskScore;
import com.streamshield.nlp.DetectorPipeline;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProcessingService {

  private final DetectorPipeline pipeline;

  public ProcessingService(DetectorPipeline pipeline){ this.pipeline = pipeline; }

  public List<EntityHit> detect(String text) {
    return pipeline.detectAll(text);
  }

  public RiskScore score(String text, List<EntityHit> hits) {
    double perMax = 0.0;
    Map<String, Long> counts = new HashMap<>();
    for (EntityHit h : hits) {
      counts.merge(h.type(), 1L, Long::sum);
      double severity = severityOf(h.type());
      double surface = Math.log(1 + (h.end()-h.start()));
      double s = severity * h.confidence() * (1 + surface/5.0);
      perMax = Math.max(perMax, s);
    }
    double docScore = Math.min(1.0, perMax / 10.0 + Math.log(1+hits.size())/10.0);
    return new RiskScore(perMax, docScore);
  }

  private double severityOf(String type) {
    return switch (type) {
      case "SSN","CREDIT_CARD","IBAN" -> 1.0;
      case "EMAIL","PHONE" -> 0.8;
      case "PERSON","PER","ORG","LOCATION","LOC" -> 0.5;
      default -> 0.4;
    };
  }
}
