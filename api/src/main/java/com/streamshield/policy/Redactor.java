package com.streamshield.policy;

import com.streamshield.dto.EntityHit;

import java.util.List;

/**
 * Hard redaction: replaces each entity span with a typed marker.
 */
public class Redactor {

  public static String apply(String text, List<EntityHit> hits) {
    if (text == null || text.isEmpty() || hits == null || hits.isEmpty()) {
      return text;
    }

    StringBuilder sb = new StringBuilder(text);
    int shift = 0;

    for (EntityHit h : hits) {
      String replacement = "[REDACTED:" + h.type() + "]";

      int s = h.start() + shift;
      int e = h.end() + shift;

      if (s < 0 || e > sb.length() || s >= e) {
        continue; // safety guard
      }

      sb.replace(s, e, replacement);

      int originalLen = e - s;
      int delta = replacement.length() - originalLen;
      shift += delta;
    }

    return sb.toString();
  }
}
