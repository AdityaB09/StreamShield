package com.streamshield.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.streamshield.dto.*;
import com.streamshield.policy.*;
import com.streamshield.service.AuditService;
import com.streamshield.service.ProcessingService;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ProcessController {

  private final ProcessingService svc;
  private final AuditService audit;
  private final ObjectMapper om = new ObjectMapper();

  public ProcessController(ProcessingService svc, AuditService audit){
    this.svc = svc; this.audit = audit;
  }

  @GetMapping("/health")
  public Map<String,Object> health(){ return Map.of("status","ok","service","api"); }

  @PostMapping(value="/process", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
  public Mono<ProcessResponse> process(@RequestBody ProcessRequest req){
    String text = req.text()==null ? "" : req.text();
    var hits = svc.detect(text);
    var risk = svc.score(text, hits);

    ActionPolicy policy = ActionPolicy.from(req.action());
    String transformed = PolicyEngine.apply(text, hits, policy);

    // counts per type
    Map<String, Long> counts = hits.stream().collect(Collectors.groupingBy(EntityHit::type, Collectors.counting()));

    // DP (optional)
    boolean dp = Boolean.parseBoolean(String.valueOf(req.options()==null? "false" : req.options().getOrDefault("dp","false")));
    Map<String, Object> dpCounts = dp ? DifferentialPrivacy.noisyCounts(counts, 1.0) : Map.copyOf(counts);

    Long auditId = audit.save(text, transformed, hits, risk.documentScore());
    Map<String,Object> auditMap = new HashMap<>();
    auditMap.put("auditId", auditId);
    auditMap.put("counts", counts);
    auditMap.put("dpCounts", dpCounts);

    return Mono.just(new ProcessResponse(text, transformed, hits, risk, auditMap));
  }

  @GetMapping("/audit")
  public Object listAudit() {
    return audit.list();
  }
}
