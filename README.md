# StreamShield – Privacy-Preserving Text Transformer (Java)

Java-native, low-latency service that **detects, scores and anonymizes** sensitive information in text streams using **hybrid NLP** (regex + Lucene analyzers + DJL/HF NER + OpenNLP fallback). Includes **policy engine** (redact/mask/synthesize), **differential privacy** counts, **risk scoring**, and **PostgreSQL audit trail**. Comes with a **modern React UI** (dark/light) to demo the pipeline.

## Quick Start (Docker)

```bash
# from repo root
docker compose build
docker compose up -d

# API health
curl -s http://localhost:8080/api/health | jq .

# UI
# open http://localhost:5173
