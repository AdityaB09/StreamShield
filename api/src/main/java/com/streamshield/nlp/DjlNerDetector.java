package com.streamshield.nlp;

import com.streamshield.dto.EntityHit;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Placeholder "ML" detector.
 * 
 * For now, this does a very lightweight heuristic:
 * - Finds sequences of 2+ capitalized words ("Alex Lee", "New York")
 *   and tags them as PERSON or LOCATION-ish types.
 *
 * OpenNLP + regex are still the main detection layers; this just
 * adds a bit of extra context for demo purposes without DJL.
 */
@Component
public class DjlNerDetector {

    public List<EntityHit> detect(String text) {
        List<EntityHit> out = new ArrayList<>();
        if (text == null || text.isBlank()) return out;

        // ultra simple tokenization on spaces
        String[] tokens = text.split("\\s+");
        int[] offsets = new int[tokens.length];

        int idx = 0;
        for (int i = 0; i < tokens.length; i++) {
            int pos = text.indexOf(tokens[i], idx);
            offsets[i] = pos;
            idx = pos + tokens[i].length();
        }

        int startIdx = -1;
        for (int i = 0; i < tokens.length; i++) {
            String t = tokens[i];
            if (looksCapitalized(t)) {
                if (startIdx == -1) startIdx = i;
            } else {
                if (startIdx != -1 && i - startIdx >= 2) {
                    addSpan(text, tokens, offsets, startIdx, i - 1, out);
                }
                startIdx = -1;
            }
        }
        if (startIdx != -1 && tokens.length - startIdx >= 2) {
            addSpan(text, tokens, offsets, startIdx, tokens.length - 1, out);
        }

        return out;
    }

    private boolean looksCapitalized(String t) {
        if (t.isEmpty()) return false;
        char c = t.charAt(0);
        return Character.isUpperCase(c) && t.substring(1).chars().anyMatch(Character::isLowerCase);
    }

    private void addSpan(String fullText, String[] tokens, int[] offsets,
                         int startToken, int endToken, List<EntityHit> out) {
        int start = offsets[startToken];
        int end = offsets[endToken] + tokens[endToken].length();
        if (start < 0 || end > fullText.length()) return;

        String span = fullText.substring(start, end);
        // quick guess: if has "Inc." or "Ltd" call it ORG, else PERSON-ish
        String type = (span.matches(".*\\b(Inc\\.|Ltd|LLC|Corp\\.)\\b.*")) ? "ORG" : "PERSON";

        out.add(new EntityHit(type, span, start, end, 0.6, "HEURISTIC"));
    }
}
