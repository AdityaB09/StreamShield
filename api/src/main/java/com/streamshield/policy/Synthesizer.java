package com.streamshield.policy;

import com.streamshield.dto.EntityHit;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Synthesizer {
  // ultra-light faker-ish replacements (no extra libs)
  private static final String[] FIRST = {"Alex","Taylor","Sam","Jordan","Riley","Casey"};
  private static final String[] LAST = {"Lee","Patel","Kim","Garcia","Nguyen","Smith"};

  public static String apply(String text, List<EntityHit> hits) {
    StringBuilder sb = new StringBuilder(text);
    int shift = 0;
    for (EntityHit h : hits) {
      int s = h.start()+shift, e = h.end()+shift;
      String repl = switch (h.type()) {
        case "EMAIL" -> "user"+rnd(1000,9999)+"@example.com";
        case "CREDIT_CARD" -> "4"+rndStr(15);
        case "SSN" -> rnd(100,999)+"-"+rnd(10,99)+"-"+rnd(1000,9999);
        case "PHONE" -> "+1-555-"+rnd(100,999)+"-"+rnd(1000,9999);
        case "PERSON","PER" -> FIRST[rnd(0,FIRST.length-1)]+" "+LAST[rnd(0,LAST.length-1)];
        default -> "[SYNTH:"+h.type()+"]";
      };
      sb.replace(s, e, repl);
      shift += (repl.length() - (e - s));
    }
    return sb.toString();
  }

  private static int rnd(int a, int b){ return ThreadLocalRandom.current().nextInt(a, b+1);}
  private static String rndStr(int n){ StringBuilder x=new StringBuilder(); for(int i=0;i<n;i++) x.append(rnd(0,9)); return x.toString();}
}
