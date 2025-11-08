package com.streamshield.service;

import com.streamshield.dto.EntityHit;
import com.streamshield.dto.RiskScore;
import com.streamshield.nlp.DetectorPipeline;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProcessingService {

    private final DetectorPipeline pipeline;

    public ProcessingService(DetectorPipeline pipeline) {
        this.pipeline = pipeline;
    }

    public List<EntityHit> detect(String text) {
        return pipeline.detectAll(text);
    }

    public RiskScore score(String text, List<EntityHit> hits) {
        double perMax = 0.0;
        Map<String, Long> counts = new HashMap<>();
        Map<String, Double> categoryScores = new HashMap<>();

        for (EntityHit h : hits) {
            counts.merge(h.type(), 1L, Long::sum);

            double severity = severityOf(h.type());
            double surface = Math.log(1 + (h.end() - h.start()));
            double s = severity * h.confidence() * (1 + surface / 5.0);

            perMax = Math.max(perMax, s);

            String cat = h.category() == null ? "OTHER" : h.category();
            categoryScores.merge(cat, s, Double::sum);
        }

        double docScore = Math.min(
            1.0,
            perMax / 10.0 + Math.log(1 + hits.size()) / 10.0
        );

        return new RiskScore(perMax, docScore, categoryScores);
    }

    private double severityOf(String type) {
        if (type == null) return 0.4;
        return switch (type.toUpperCase()) {
            case "SSN", "CREDIT_CARD", "CVV", "IBAN" -> 1.0;          // PCI / highly sensitive PII
            case "EMAIL", "PHONE", "DOB", "IP_ADDRESS" -> 0.8;        // PII
            case "MRN", "ICD10", "PHI_GENERIC" -> 0.9;                // PHI
            case "PERSON", "PER", "ORG", "LOCATION", "LOC",
                 "ADDRESS" -> 0.5;
            default -> 0.4;
        };
    }
}
