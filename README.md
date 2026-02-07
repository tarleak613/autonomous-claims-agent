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
```

### Steps to Run

1. **Clone the repository**
```bash
git clone <your-github-repo-url>
cd autonomous-claims-agent
```
2. **Start the Spring Boot application**
```bash
mvn spring-boot:run
```
Once the application starts successfully, it runs on:

http://localhost:8080

🌐 Using the Application via Browser

1. Open a browser
2. Navigate to:
```bash
http://localhost:8080/index.html
```
3. Upload an FNOL file (.txt or .pdf)
4. Submit the form
The response will be displayed directly in the browser as JSON.

🔌 Using an API Testing Tool (Postman / Curl)

You can also test the service using Postman or any API testing tool.
```Endpoint
POST http://localhost:8080/claims/upload
```

Request Details

Method: POST
Body → form-data
Key: file
Type: File
Value: Upload FNOL document

```Example using curl
curl -X POST http://localhost:8080/claims/upload \
  -F "file=@samples/1_fast_track.txt"
```
The API returns a JSON response containing:

- Extracted fields
- Missing fields (if any)
- Recommended route
- Reasoning


📝 Notes

- TXT and text-based PDFs are supported
- Scanned PDFs or blank templates are routed to Manual Review
- The system is designed to degrade gracefully on low-quality input

🤖 Use of AI Tools

- AI tools (ChatGPT) were used to:
- Reason about edge cases
- Improve defensive extraction
- Refine routing logic
- Improve code clarity and documentation

🎯 Focus of This Assessment

This project emphasizes:

- Logical problem breakdown
- Clean and readable code
- Realistic handling of messy input
- Explainable decision-making

Clarity > complexity.
