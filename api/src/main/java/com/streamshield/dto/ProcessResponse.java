package com.streamshield.dto;

import java.util.List;
import java.util.Map;

public record ProcessResponse(
    String originalText,
    String transformedText,
    List<EntityHit> entities,
    RiskScore risk,
    Map<String, Object> audit
) {}
