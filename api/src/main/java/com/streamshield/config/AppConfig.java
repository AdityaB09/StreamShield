package com.streamshield.config;

import org.springframework.context.annotation.Configuration;

/**
 * Placeholder config.
 *
 * DJL can be wired here later as a ZooModel bean if you want real Java-native
 * Transformer NER. For now, StreamShield runs with:
 *  - Regex detector (Lucene + curated patterns)
 *  - OpenNLP detector
 */
@Configuration
public class AppConfig {
  // No-op for now – keeps the build simple and stable
}
