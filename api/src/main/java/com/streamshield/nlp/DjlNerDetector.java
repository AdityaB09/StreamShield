package com.streamshield.nlp;

import com.streamshield.dto.EntityHit;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * DJL-based NER detector (stub version).
 *
 * The idea:
 *  - In the future, load a HF token-classification model with DJL
 *  - Map token spans back to character offsets in the original text
 *
 * For now, this stub returns an empty list so the system builds and
 * works using RegexDetector + OpenNlpDetector.
 */
@Component
public class DjlNerDetector {

  public List<EntityHit> detect(String text) {
    // TODO: plug in DJL NER here.
    return Collections.emptyList();
  }
}
