package com.streamshield.dto;

public record EntityHit(
    String type,
    String text,
    int start,
    int end,
    double confidence,
    String category, // PII / PCI / PHI / OTHER
    String source    // REGEX | HEURISTIC | OPENNLP | ...
) {}
