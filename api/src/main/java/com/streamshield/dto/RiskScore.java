package com.streamshield.dto;

public record RiskScore(
    double perEntityMax,
    double documentScore
) {}
