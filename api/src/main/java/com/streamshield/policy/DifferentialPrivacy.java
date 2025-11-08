package com.streamshield.policy;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class DifferentialPrivacy {
  public static Map<String, Object> noisyCounts(Map<String, Long> counts, double epsilon) {
    Map<String, Object> out = new HashMap<>();
    double b = 1.0 / Math.max(0.0001, epsilon); // Laplace scale
    for (var e : counts.entrySet()) {
      double noise = laplace(0, b);
      out.put(e.getKey(), e.getValue() + noise);
    }
    return out;
  }

  private static double laplace(double mu, double b) {
    double u = ThreadLocalRandom.current().nextDouble() - 0.5;
    return mu - b * Math.signum(u) * Math.log(1 - 2 * Math.abs(u));
  }
}
