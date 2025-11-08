package com.streamshield.util;

/**
 * Maps low-level entity types (EMAIL, CREDIT_CARD, MRN, etc.)
 * to higher-level buckets: PII, PCI, PHI, OTHER.
 */
public class CategoryUtils {

    public static String categoryFor(String type) {
        if (type == null) return "OTHER";
        String t = type.toUpperCase();

        return switch (t) {
            // --- PCI (payment) ---
            case "CREDIT_CARD", "CVV", "IBAN" -> "PCI";

            // --- PHI (health) ---
            case "MRN", "ICD10", "PHI_GENERIC" -> "PHI";

            // --- PII (personal identifiable info) ---
            case "SSN", "EMAIL", "PHONE", "PERSON", "PER",
                 "ORG", "ORGANIZATION", "LOCATION", "LOC",
                 "ADDRESS", "DOB", "IP_ADDRESS" -> "PII";

            default -> "OTHER";
        };
    }
}
