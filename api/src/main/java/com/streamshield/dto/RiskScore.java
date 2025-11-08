package com.streamshield.dto;

import java.util.Map;

public record RiskScore(
    double perEntityMax,
    double documentScore,
    Map<String, Double> categoryScores // e.g. {"PII": 1.2, "PCI": 0.8}
) {}
