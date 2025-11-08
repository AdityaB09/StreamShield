package com.streamshield.dto;

import java.util.Map;

public record ProcessRequest(
    String text,
    String action, // REDACT | MASK | SYNTHESIZE | NONE
    Map<String, String> options // e.g. {"dp":"true"}
) {}
