package com.streamshield.policy;

import com.streamshield.dto.EntityHit;
import com.streamshield.util.MaskingUtils;

import java.util.List;

public class PolicyEngine {
  public static String apply(String text, List<EntityHit> hits, ActionPolicy policy) {
    return switch (policy) {
      case REDACT -> Redactor.apply(text, hits);
      case MASK -> mask(text, hits);
      case SYNTHESIZE -> Synthesizer.apply(text, hits);
      case NONE -> text;
    };
  }

  private static String mask(String text, List<EntityHit> hits) {
    StringBuilder sb = new StringBuilder(text);
    int shift = 0;
    for (EntityHit h : hits) {
      int s = h.start()+shift, e = h.end()+shift;
      String masked = MaskingUtils.maskLike(text.substring(h.start(), h.end()));
      sb.replace(s, e, masked);
      shift += (masked.length() - (e - s));
    }
    return sb.toString();
  }
}
