package com.streamshield.policy;

public enum ActionPolicy {
  REDACT, MASK, SYNTHESIZE, NONE;

  public static ActionPolicy from(String s) {
    try { return ActionPolicy.valueOf(s == null ? "NONE" : s.toUpperCase()); }
    catch (Exception e) { return NONE; }
  }
}
