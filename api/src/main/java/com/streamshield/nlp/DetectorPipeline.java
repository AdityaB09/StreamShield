package com.streamshield.nlp;

import com.streamshield.dto.EntityHit;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DetectorPipeline {
  private final RegexDetector regex;
  private final DjlNerDetector djl;
  private final OpenNlpDetector onlp;

  public DetectorPipeline(RegexDetector regex, DjlNerDetector djl, OpenNlpDetector onlp) {
    this.regex = regex; this.djl = djl; this.onlp = onlp;
  }

  public List<EntityHit> detectAll(String text) {
    List<EntityHit> hits = new ArrayList<>();
    hits.addAll(regex.detect(text));
    hits.addAll(djl.detect(text));
    hits.addAll(onlp.detect(text));
    // dedupe by span + type
    hits.sort(Comparator.comparingInt(EntityHit::start).thenComparingInt(EntityHit::end));
    Map<String, EntityHit> seen = new LinkedHashMap<>();
    for (EntityHit h : hits) {
      String key = h.type()+"|"+h.start()+"|"+h.end();
      seen.putIfAbsent(key, h);
    }
    return new ArrayList<>(seen.values());
  }
}
