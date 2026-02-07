# Autonomous Insurance Claims Processing Agent (Lite)

This project is a lightweight backend agent that processes FNOL (First Notice of Loss) documents and recommends an appropriate claim workflow based on extracted information.

The goal is **clarity over complexity**, focusing on clean logic, defensive extraction, and explainable decisions.

---

## 🧠 What This Project Does

Given an FNOL document (TXT or text-based PDF), the system:

1. Extracts key insurance fields
2. Identifies missing or inconsistent information
3. Applies simple routing logic
4. Returns a JSON response explaining the decision

---

## 🔍 Extracted Fields

The agent attempts to extract:

- Policy Number
- Policy Holder Name
- Incident Date
- Incident Description
- Claim Type (vehicle / injury)
- Estimated Damage Amount

Extraction is **best-effort** and designed to work with semi-structured FNOL documents.

---

## 🚦 Routing Logic

The claim is routed using the following priority:

1. **Manual Review**
   - Any mandatory field is missing
   - Low extraction confidence (e.g. blank templates)

2. **Investigation Flag**
   - Description contains fraud indicators (e.g. "fraud", "staged", "inconsistent")

3. **Specialist Queue**
   - Claim type is `injury`

4. **Fast-track**
   - Estimated damage < 25,000
   - All mandatory fields present

5. **Standard Processing**
   - All other cases

Each response includes a short explanation of why the route was chosen.

---

## 📁 Sample Files (Provided for Testing)

All sample documents are located in the `samples/` folder.

### 1️⃣ `1_fast_track.txt`
**Expected Route:** `Fast-track`  
**Why:**  
- Damage < 25,000  
- No missing fields  
- Non-injury claim  

---

### 2️⃣ `2_manual_review.txt`
**Expected Route:** `Manual Review`  
**Why:**  
- Mandatory fields missing  

---

### 3️⃣ `3_investigation_flag.txt`
**Expected Route:** `Investigation Flag`  
**Why:**  
- Accident description contains fraud indicators  

---

### 4️⃣ `4_specialist_queue.txt`
**Expected Route:** `Specialist Queue`  
**Why:**  
- Claim type is `injury`  

---

### 5️⃣ `5_standard_processing.txt`
**Expected Route:** `Standard Processing`  
**Why:**  
- Damage ≥ 25,000  
- No special conditions  

---

### 6️⃣ `acord_blank.pdf`
**Expected Route:** `Manual Review`  
**Why:**  
- This is an unfilled ACORD FNOL template  
- Contains labels and instructional text but no actual values  
- The system correctly detects missing fields and low extraction confidence  

---

## 🧪 How to Run the Project

### Prerequisites
- Java 17+
- Maven

### Run
```bash
mvn spring-boot:run
