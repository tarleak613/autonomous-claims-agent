# Autonomous Insurance Claims Processing Agent (Lite)

A lightweight backend service that processes FNOL (First Notice of Loss) documents and recommends an appropriate insurance claim workflow.

Focus: clarity over complexity, clean logic, and explainable decisions.

---

## 🧠 Approach

Given an FNOL document (.txt or text-based .pdf), the system:

- Extracts key claim fields (best-effort)
- Identifies missing or inconsistent data
- Applies simple rule-based routing
- Returns a JSON response with reasoning

### Extracted Fields
- Policy Number
- Policy Holder Name
- Incident Date
- Incident Description
- Claim Type (vehicle / injury)
- Estimated Damage Amount

---

## 🚦 Routing Logic

Priority-based routing:

1. Manual Review
   - Mandatory fields missing
   - Low extraction confidence (e.g. blank templates)

2. Investigation Flag
   - Fraud indicators in description

3. Specialist Queue
   - Injury-related claims

4. Fast-track
   - Damage < 25,000 and all mandatory fields present

5. Standard Processing
   - All other cases

Each response includes a short explanation of the decision.

---

## 📁 Sample Files

Sample FNOL documents are available in the samples/ folder.

1_fast_track.txt  
Expected Route: Fast-track  
Reason: Low damage, complete data, non-injury claim  

2_manual_review.txt  
Expected Route: Manual Review  
Reason: Mandatory fields missing  

3_investigation_flag.txt  
Expected Route: Investigation Flag  
Reason: Fraud indicators in description  

4_specialist_queue.txt  
Expected Route: Specialist Queue  
Reason: Injury claim  

5_standard_processing.txt  
Expected Route: Standard Processing  
Reason: High damage, no special conditions  

acord_blank.pdf  
Expected Route: Manual Review  
Reason: Unfilled ACORD FNOL template with no actual values  

---

## 🧪 How to Run the Project

Prerequisites:
- Java 17+
- Maven

Run the application:

mvn spring-boot:run

Application runs at:

http://localhost:8080

---

## 🌐 Test the Application

Browser:

http://localhost:8080/index.html

Upload an FNOL file (.txt or .pdf) and submit.

API (Postman / Curl):

POST http://localhost:8080/claims/upload

Form-data:
- key: file
- type: File
- value: FNOL document

Example:

curl -X POST http://localhost:8080/claims/upload \
  -F "file=@samples/1_fast_track.txt"

---

## 🤖 Use of AI Tools

ChatGPT was used to:
- Reason about edge cases
- Improve extraction robustness
- Refine routing logic
- Improve documentation clarity

---

Clarity > complexity.
