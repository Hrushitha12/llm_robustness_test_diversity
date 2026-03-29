# LLM Robustness Test Diversity

**Individual Study — ITCS 6882 | UNC Charlotte**  
**Student:** Hrushitha Goud Tigulla  
**Supervisor:** Prof. Marco Vieira  

---

## Overview

This repository contains the complete artefacts for a study investigating whether different large language models (LLMs) and prompt strategies produce *diverse* robustness tests for microservice web applications — or whether they converge on the same failures regardless of model size or prompting approach.

**Core question:** Does the choice of prompt type or model matter more for discovering failure modes?

**Short answer:** Prompt type explains more variance than model size. The Structured prompt collapsed all three models to identical FM sets (Jaccard = 1.00) in both SUTs. FewShot and CoT produced the most inter-model diversity.

---

## Systems Under Test

| SUT | Description | Endpoints tested | FMs confirmed |
|---|---|---|---|
| **TeaStore** | Java microservice e-commerce (6 containers) | 7 HTTP endpoints | 9 |
| **OTel Astronomy Shop** | Polyglot microservice e-commerce (CNCF demo) | 6 HTTP endpoints | 14 |

---

## Models and Prompts

| Model | Name | Size |
|---|---|---|
| Model C | qwen3:14b | 14B |
| Model D | qwen2.5-coder:32b | 32B |
| Model E | llama3.1:70b | 70B |

**Prompt strategies:** ZeroShot · Structured · FewShot · Chain-of-Thought (CoT) · Self-Refine

**Total runs:** 15 per SUT (14 valid TeaStore, 12 valid OTel)

---

## Repository Structure

```
llm-robustness-test-diversity/
│
├── sut_teastore/
│   ├── env/                        # Docker Compose up/down/ready scripts
│   └── test_harness/               # Maven project
│       └── src/test/java/com/example/
│           ├── TeaStoreBaseTest.java
│           ├── CanaryTest.java
│           ├── TeaStore_Manual_R1_RobustnessTest.java
│           └── TeaStore_Model*_*_RobustnessTest.java  (14 runs)
│
├── sut_otel/
│   ├── env/                        # Docker Compose up/down/ready scripts
│   └── test_harness/               # Maven project
│       └── src/test/java/com/example/
│           ├── OTelShopBaseTest.java
│           ├── CanaryTest.java
│           ├── OTelShop_Manual_R1_RobustnessTest.java
│           └── OTelShop_Model*_*_RobustnessTest.java  (12 runs)
│
├── prompts/
│   ├── model_c_teastore/           # 5 prompt files per model per SUT
│   ├── model_d_teastore/
│   ├── model_e_teastore/
│   ├── model_c_otel/
│   ├── model_d_otel/
│   └── model_e_otel/
│
├── results/
│   ├── teastore/                   # JSONL files (1 per run)
│   └── otel/                       # JSONL files (1 per run)
│
├── analysis/
│   ├── teastore/
│   │   ├── binary_matrix_final.xlsx
│   │   ├── jaccard_analysis_v2.xlsx
│   │   ├── venn_diagrams/          # 10 PNG Venn diagrams
│   │   └── diversity_analysis_report.md
│   ├── otel/
│   │   ├── otel_diversity_analysis.xlsx
│   │   ├── venn_diagrams/          # 10 PNG Venn diagrams
│   │   └── otel_diversity_analysis_report.md
│   └── cross_sut/
│       ├── cross_sut_analysis.xlsx
│       └── cross_sut_analysis.md
│
└── README.md
```

---

## Key Findings

1. **Structured prompt collapses diversity** — All models converge to identical FM sets (Jaccard = 1.00) in both SUTs. Replicated finding.

2. **Prompt strategy > model size** — Changing the prompt on the same model produces more variation than changing the model with the same prompt.

3. **D-Self = ∅ in both SUTs** — qwen2.5-coder:32b with Self-Refine consistently generates zero failures regardless of SUT.

4. **No single run covers all FMs** — TeaStore max = 5/9 (56%), OTel max = 7/14 (50%). Ensemble testing is required.

5. **Emergent security-aware generation** — CoT prompts independently produced XSS and whitespace-in-path inputs across 3 models without instruction. Stronger pattern in OTel (4 runs) than TeaStore (2 runs).

6. **FewShot discovers the rarest FMs** — FM6 in TeaStore (1/14 runs) and FM12/FM13 in OTel (1/12 runs each) were each found by exactly one FewShot run.

---

## Robustness Oracle

```
HTTP status < 500  → PASS  (system handled input gracefully)
HTTP status ≥ 500  → FAIL  (Abort class — robustness failure)
```

Classification follows the CRASH taxonomy (Catastrophic, Restart, Abort, Silent, Hindering). This study detects Abort-class failures only.

---

## Reproduce

**TeaStore:**
```bash
cd sut_teastore && docker compose up -d
python env/env_ready.py --timeout 180
mvn test -Dtest="TeaStore_Manual_R1_RobustnessTest" -f test_harness/pom.xml
```

**OTel Astronomy Shop:**
```bash
cd opentelemetry-demo  # clone from github.com/open-telemetry/opentelemetry-demo
docker compose -f docker-compose.minimal.yml up --force-recreate --remove-orphans --detach
cd ../llm-robustness-test-diversity
python sut_otel/env/env_ready.py --timeout 300
mvn test -Dtest="OTelShop_Manual_R1_RobustnessTest" -f sut_otel/test_harness/pom.xml
```

---

## Notes

- Model C (`qwen3:14b`) generates `<think>` blocks — strip before compiling with `sed -n '/^package com.example/,$p' raw.txt > cleaned.java`
- Currency and Shipping services are unavailable in OTel minimal compose (always return HTTP 504). Requires full `docker-compose.yml` on a Linux host with `vm.max_map_count=262144`.
- All test source files were verified against JSONL results before FM assignment.
