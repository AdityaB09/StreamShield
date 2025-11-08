package com.streamshield.nlp;

import com.streamshield.dto.EntityHit;
import com.streamshield.util.CategoryUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import jakarta.annotation.PostConstruct;
import java.io.InputStream;
import java.util.*;
import java.util.regex.*;

@Component
public class RegexDetector {

    @Value("${streamshield.regexConfig}")
    private Resource regexConfig;

    private Map<String, List<Pattern>> patterns = new HashMap<>();

    @PostConstruct
    public void load() throws Exception {
        Yaml yaml = new Yaml();
        try (InputStream in = regexConfig.getInputStream()) {
            Map<String, Object> root = yaml.load(in);
            @SuppressWarnings("unchecked")
            Map<String, List<String>> pats =
                (Map<String, List<String>>) root.get("patterns");

            for (var e : pats.entrySet()) {
                List<Pattern> compiled = new ArrayList<>();
                for (String p : e.getValue()) compiled.add(Pattern.compile(p));
                patterns.put(e.getKey(), compiled);
            }
        }
    }

    public List<EntityHit> detect(String text) {
        List<EntityHit> hits = new ArrayList<>();
        patterns.forEach((type, list) -> {
            for (Pattern p : list) {
                Matcher m = p.matcher(text);
                while (m.find()) {
                    String span = text.substring(m.start(), m.end());
                    String category = CategoryUtils.categoryFor(type);
                    hits.add(new EntityHit(
                        type,
                        span,
                        m.start(),
                        m.end(),
                        0.99,
                        category,
                        "REGEX"
                    ));
                }
            }
        });
        return hits;
    }
}
