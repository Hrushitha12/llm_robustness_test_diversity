# LLM-Based Robustness Testing of Microservice Applications

**Accepted at SRDS 2026** (Practical Experience Report)
**Authors:** Hrushitha Goud Tigulla, Marco Vieira
**Affiliation:** University of North Carolina at Charlotte

---

## Overview

This repository contains the complete artefacts for a controlled empirical study investigating whether different large language models (LLMs) and prompt strategies produce *diverse* robustness tests for microservice web applications, or whether they converge on the same failures regardless of model size or prompting approach.

**Core question:** Does the choice of prompt strategy or model matter more for discovering distinct failure modes?

**Short answer:** Prompt strategy explains more variation in diversity than model size. The Structured prompt collapses all three models to identical failure-mode (FM) sets (Jaccard = 1.00) in both systems under test. A single model varied across three prompt strategies (Structured, GuidedFewShot, ZeroShot) achieves complete FM coverage on one system — outperforming any multi-model ensemble under a fixed prompt.

---

## Systems Under Test

| SUT | Description | Services | Protocol | FMs confirmed |
|---|---|---|---|---|
| **TeaStore** | Java monolingual e-commerce reference app | 6 | Form-encoded HTTP | 9 |
| **OTel Astronomy Shop** | Polyglot e-commerce demo (Go, C++, Rust, JS, .NET, Java, Python, PHP) | 27 | JSON / gRPC | 14 |

---

## Models and Prompt Strategies

| Model | Name | Size | Notes |
|---|---|---|---|
| Model C | `qwen3:14b` | 14B | General-purpose |
| Model D | `qwen2.5-coder:32b` | 32B | Code-specialized |
| Model E | `llama3.1:70b` | 70B | General-purpose, largest |

**7 prompt strategies**, in three groups:
- **Base:** ZeroShot, Structured
- **Established:** FewShot, Chain-of-Thought (CoT), Self-Refine
- **Domain-knowledge (introduced in this study):** Guided, GuidedFewShot

Guided and GuidedFewShot embed the mutation taxonomy from Vieira et al. (DSN 2007) as domain context; GuidedFewShot additionally includes the CRASH failure classification and concrete examples disambiguating key-absent vs. value-empty vs. value-null mutations.

**Total runs:** 38 valid of 42 possible (7 strategies × 3 models × 2 SUTs). Four excluded: Model C ZeroShot on both SUTs (non-English language switch mid-generation), Model C Self-Refine on OTel (malformed output), Model E Structured on OTel (repeated system crashes).

---

## Repository Structure

```
llm_robustness_test_diversity/
│
├── sut_teastore/
│   ├── env/                        # Docker Compose up/down/ready scripts
│   └── test_harness/               # Maven project (JUnit 5 test sources)
│
├── sut_otel/
│   ├── env/                        # Docker Compose up/down/ready scripts
│   └── test_harness/               # Maven project (JUnit 5 test sources)
│
├── prompts/
│   ├── model_c_teastore/  model_d_teastore/  model_e_teastore/
│   └── model_c_otel/      model_d_otel/      model_e_otel/
│                                    # Full text of all 7 prompt strategies,
│                                    # per model, per SUT
│
├── generations/
│   └── model_c/ model_d/ model_e/  # Raw LLM output prior to cleaning/compilation
│
├── results/                        # JSONL execution results, 1 file per run
│
├── analysis/
│   ├── teastore/                   # Binary coverage matrix, Jaccard analysis, Venn diagrams
│   ├── otel/                       # Binary coverage matrix, Jaccard analysis, Venn diagrams
│   └── cross_sut/                  # Cross-system comparison
│
├── tools/                          # Analysis / cleaning scripts
│
└── README.md
```

---

## Key Findings

1. **No single run exceeds 57% coverage** — max 5/9 (56%) on TeaStore, 8/14 (57%) on OTel.
2. **Prompt strategy explains more diversity variation than model size** — changing the prompt for a fixed model produces roughly twice the FM-set variation of changing the model under a fixed prompt.
3. **Structured collapse** — all three models converge to identical FM sets (Jaccard = 1.00) on both SUTs, replicated across systems.
4. **Domain knowledge needs examples, not just rules** — GuidedFewShot improves union coverage over Guided by 2 FMs on each SUT; all three models interpret "replace by null" as value-empty, never key-absent, unless shown concrete examples.
5. **Code-specialized model transformation** — `qwen2.5-coder:32b` triggers 0 FMs under Self-Refine (missing oracle assertion) but achieves the study's highest single-run coverage (56%/57%) under GuidedFewShot.
6. **ZeroShot finds state-based failures directed strategies miss** — e.g., checkout with an empty cart, unauthenticated cart removal — because parameter-focused prompts never probe application state.
7. **Emergent security-aware generation** — several runs produced XSS payloads and control characters in inputs without any instruction to do so.

Full findings table and discussion: see the paper.

---

## Robustness Oracle

```
HTTP status < 500  → PASS  (system handled input gracefully)
HTTP status ≥ 500  → FAIL  (Abort class — robustness failure)
```

Classification follows the CRASH taxonomy (Catastrophic, Restart, Abort, Silent, Hindering). This study detects Abort-class failures only, via HTTP status code.

---

## Reproduce

**TeaStore:**
```bash
cd sut_teastore && docker compose up -d
python env/env_ready.py --timeout 180
mvn test -f test_harness/pom.xml
```

**OTel Astronomy Shop:**
```bash
cd opentelemetry-demo  # clone from github.com/open-telemetry/opentelemetry-demo
docker compose -f docker-compose.yml up --force-recreate --remove-orphans --detach
cd ../llm_robustness_test_diversity
python sut_otel/env/env_ready.py --timeout 300
mvn test -f sut_otel/test_harness/pom.xml
```

---

## Notes

- `qwen3:14b` generates `<think>` reasoning blocks that must be stripped before compilation.
- Currency (`POST /api/currency`) and Shipping (`POST /api/shipping`) return HTTP 504 for all inputs under the minimal Docker Compose configuration; the full compose configuration is required to test these endpoints, and requires a Linux host with `vm.max_map_count=262144`.
- All test source files were verified against JSONL results before FM assignment; each FM maps to a specific (endpoint, parameter, mutation-type) triple confirmed against application source code.

---

## Citation

If you use this artefact, please cite:

> H. G. Tigulla and M. Vieira, "LLM-Based Robustness Testing of Microservice Applications: An Empirical Study," in *Proc. IEEE Int. Symp. Reliable Distributed Systems (SRDS)*, 2026.
