package com.streamshield.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.streamshield.dto.EntityHit;
import com.streamshield.repo.AuditRecordEntity;
import com.streamshield.repo.AuditRecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditService {
  private final AuditRecordRepository repo;
  private final ObjectMapper om = new ObjectMapper();

  public AuditService(AuditRecordRepository repo){this.repo = repo;}

  public Long save(String original, String transformed, List<EntityHit> hits, double docScore){
    try {
      AuditRecordEntity e = new AuditRecordEntity();
      e.setOriginalText(original);
      e.setTransformedText(transformed);
      e.setEntitiesJson(om.writeValueAsString(hits));
      e.setDocumentScore(docScore);
      repo.save(e);
      return e.getId();
    } catch (Exception ex) {
      return -1L;
    }
  }

  public List<AuditRecordEntity> list(){ return repo.findAll(); }
}
