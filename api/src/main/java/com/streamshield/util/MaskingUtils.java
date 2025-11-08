package com.streamshield.util;

public class MaskingUtils {
  public static String maskLike(String s) {
    if (s == null || s.isBlank()) return s;
    if (s.length() <= 4) return "*".repeat(s.length());
    return s.substring(0, 2) + "*".repeat(Math.max(0, s.length()-4)) + s.substring(s.length()-2);
  }
}
