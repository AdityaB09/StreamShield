package com.streamshield.dto;

public record EntityHit(
    String type,
    String text,
    int start,
    int end,
    double confidence,
    String source // REGEX | DJL | OPENNLP
) {}
