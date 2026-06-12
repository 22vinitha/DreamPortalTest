# 🌙 Dream Portal – Automated Testing Suite

> **Selenium (Java) + TestNG** | Page Object Model | ExtentReports | AI-Based Validation | Screenshot Capture

[![Author](https://img.shields.io/badge/Author-Vinitha%20M-blueviolet?style=flat-square)](mailto:vinitha.m@email.com)
[![Framework](https://img.shields.io/badge/Framework-Selenium-43B02A?style=flat-square)](https://www.selenium.dev/)
[![Language](https://img.shields.io/badge/Language-Java-ED8B00?style=flat-square)](https://www.java.com/)
[![Testing](https://img.shields.io/badge/Testing-TestNG-FF6C37?style=flat-square)](https://testng.org/)
[![Reports](https://img.shields.io/badge/Reports-ExtentReports-0078D4?style=flat-square)](https://www.extentreports.com/)
[![Build](https://img.shields.io/badge/Build-Maven-C71A36?style=flat-square)](https://maven.apache.org/)

---

## 📖 Overview

This project automates end-to-end UI testing of the **Dream Portal** web application — a dream journaling website. Built with **Selenium WebDriver (Java)** and **TestNG**, it follows the **Page Object Model (POM)** design pattern for clean, maintainable test code.

The suite covers loader animation validation, dream diary table integrity checks, statistics verification, and includes a **bonus AI-based dream classifier** that cross-validates dream types against what the website displays.

🌐 **Website Under Test:** [https://arjitnigam.github.io/myDreams/](https://arjitnigam.github.io/myDreams/)

---

## ✨ Features

| Feature | Description |
|---|---|
| 🔍 Automated UI Testing | End-to-end browser automation via Selenium WebDriver |
| 🏗️ Page Object Model | Modular, maintainable structure (POM pattern) |
| 🧪 Data Validation | Verifies diary rows, columns, dream types, and field integrity |
| 🤖 AI-Based Validation | Keyword-based AI classifier cross-validates dream types |
| 📸 Screenshot Capture | Auto-captures screenshots on test completion |
| 📊 ExtentReports | Rich HTML reports with step-level details |
| 🔁 Recurring Dream Detection | Identifies and asserts recurring dreams from diary data |

---

## 📁 Project Structure

```
DreamPortalTest/
├── src/
│   ├── main/java/
│   │   ├── pages/
│   │   │   ├── HomePage.java           # Home page interactions & loader validation
│   │   │   ├── DiaryPage.java          # Dream diary table parsing
│   │   │   └── SummaryPage.java        # Statistics computation from diary data
│   │   └── utils/
│   │       ├── AIValidator.java         # AI-based dream type classifier
│   │       ├── ExtentReportManager.java # ExtentReports setup
│   │       ├── ExtentTestListener.java  # TestNG listener for report generation
│   │       └── ScreenshotUtil.java      # Screenshot capture utility
│   └── test/java/
│       ├── base/
│       │   └── BaseTest.java            # Browser setup & teardown
│       └── tests/
│           └── DreamTest.java           # Main test specification
├── testng.xml                           # TestNG suite configuration
├── pom.xml                              # Maven dependencies
├── .gitignore
└── README.md
```

> 📌 `reports/`, `screenshots/`, `target/`, and `test-output/` are auto-generated at runtime and are excluded from version control via `.gitignore`.

---

## 🛠️ Setup & Installation

### Prerequisites

- **Java** JDK 11 or higher
- **Maven** 3.6+
- **Google Chrome** browser (latest)
- Internet connection (ChromeDriver auto-managed via WebDriverManager)

---

### 1. Clone the Repository

```bash
git clone https://github.com/vinitham/DreamPortalTestW.git
cd DreamPortalTestW
```

### 2. Install Dependencies

```bash
mvn clean install -DskipTests
```

### 3. Run the Test Suite

```bash
mvn test
```

Or run via TestNG XML directly:

```bash
mvn test -DsuiteXmlFile=testng.xml
```

---

## 📊 View the Report

After tests run, an HTML report is auto-generated at:

```
reports/TestReport_<timestamp>.html
```

Open it in any browser — no additional setup needed.

---

## ✅ Test Cases Covered

### 🔄 TC-01 – Home Page Validation (`testHomePage`)
- Navigates to the Dream Portal home page
- Verifies the loading animation appears and disappears
- Asserts the **"My Dreams"** button becomes visible after load
- Clicks **"My Dreams"** button and verifies new tab opens
- Captures a screenshot on completion

---

### 📋 TC-02 – Dream Diary Validation (`testDiaryPage`)

| Check | Expected |
|---|---|
| Total rows | **10** |
| Columns per row | **3** (Dream Name, Days Ago, Dream Type) |
| Empty fields | **None** |
| Valid dream types | `"Good"` or `"Bad"` only |
| Recurring dreams | **2** (`"Flying over mountains"`, `"Lost in maze"`) |
| AI validation | AI classifier cross-checked against table values |

---

### 📈 TC-03 – Dream Statistics Validation (`testSummaryPage`)

| Metric | Expected Value |
|---|---|
| 😊 Good Dreams | **6** |
| 😟 Bad Dreams | **4** |
| 📊 Total Dreams | **10** |
| 🔁 Recurring Dreams | **2** |
| Good + Bad = Total | ✅ Asserted |

---


## 🤖 AI Response

This project uses **OpenAI API** to classify each dream name as `Good` or `Bad`.

**How it works:**
1. Dream name is taken from the UI table
2. Sent to OpenAI API for classification
3. AI response is compared with expected value
4. If they match → ✅ Pass | If not → ❌ Fail

> 📝 **Note:** OpenAI API was integrated and called during the test run.
> However, due to **quota exceeded / no credits**, the API response failed.
> As a result, **fallback logic automatically handled** the classification
> to ensure tests continued without breaking.

---

## ⚠️ Fallback Logic

When AI is unavailable, a simple keyword-based classifier takes over
so the tests never break.

**Bad dream keywords:** `monster`, `lost`, `chase`, `late`, `exam`, `maze`
→ Anything else is classified as `Good`

<sub>This ensures **stable test execution** even without API access.</sub>---

## 🧩 Tech Stack

| Tool | Purpose |
|---|---|
| [Selenium WebDriver](https://www.selenium.dev/) | Browser automation |
| [TestNG](https://testng.org/) | Test runner & assertions |
| [WebDriverManager](https://bonigarcia.dev/webdrivermanager/) | Auto ChromeDriver management |
| [ExtentReports](https://www.extentreports.com/) | HTML test reports |
| [OpenAI API](https://platform.openai.com/) | AI dream classification |
| Java 11+ | Language & runtime |
| Maven | Build & dependency management |

---

## 👤 Author

**Vinitha M**
📧 [vinithant22@gmail.com](mailto:vinithant22@gmail.com)
🔗 [github.com/22vinitha/DreamPortalTest](https://github.com/22vinitha/DreamPortalTest)

---

