package com.streamshield.nlp;

import com.streamshield.dto.EntityHit;
import com.streamshield.util.CategoryUtils;
import opennlp.tools.namefind.*;
import opennlp.tools.util.Span;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class OpenNlpDetector {

    @Value("${OPENNLP_ENABLED:true}")
    private boolean enabled;

    private NameFinderME person, org, loc;

    @PostConstruct
    public void init() {
        if (!enabled) return;
        try {
            person = new NameFinderME(new TokenNameFinderModel(
                new FileInputStream("/app/models/en-ner-person.bin")));
            org = new NameFinderME(new TokenNameFinderModel(
                new FileInputStream("/app/models/en-ner-organization.bin")));
            loc = new NameFinderME(new TokenNameFinderModel(
                new FileInputStream("/app/models/en-ner-location.bin")));
        } catch (Exception ignored) {
        }
    }

    public List<EntityHit> detect(String text) {
        List<EntityHit> out = new ArrayList<>();
        if (!enabled || person == null) return out;

        String[] tokens = text.split("\\s+");
        int[] offsets = new int[tokens.length];

        int idx = 0;
        for (int i = 0; i < tokens.length; i++) {
            int pos = text.indexOf(tokens[i], idx);
            offsets[i] = pos;
            idx = pos + tokens[i].length();
        }

        add(out, text, tokens, offsets, person.find(tokens), "PERSON");
        add(out, text, tokens, offsets, org.find(tokens), "ORG");
        add(out, text, tokens, offsets, loc.find(tokens), "LOCATION");
        return out;
    }

    private void add(
        List<EntityHit> out,
        String text,
        String[] tokens,
        int[] offsets,
        Span[] spans,
        String type
    ) {
        for (Span s : spans) {
            int start = offsets[s.getStart()];
            int end = offsets[s.getEnd() - 1] + tokens[s.getEnd() - 1].length();
            String span = text.substring(Math.max(0, start), Math.min(text.length(), end));
            String category = CategoryUtils.categoryFor(type);
            out.add(new EntityHit(type, span, start, end, 0.75, category, "OPENNLP"));
        }
    }
}
