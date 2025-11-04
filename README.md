# NonSenseGenerator

<h3 align="center">A “nonsense” sentence generator with syntax analysis and toxicity evaluation</h3>

<p align="center">
  <strong>NonSenseGenerator</strong> is a Java/Spring Boot web app built for the Software Engineering course (UniPD). It:
  analyzes the syntactic structure of a sentence, generates “nonsense” sentences based on templates/verb tenses, and evaluates text toxicity via external services.
</p>

<p align="center">
  <img src="https://img.shields.io/badge/status-completed-brightgreen" alt="Project Status">
  <img src="https://img.shields.io/badge/license-MIT-blue" alt="License">
  <img src="https://img.shields.io/badge/built%20with-Java-red?style=for-the-badge&logo=java" alt="Built with Java">
  <img src="https://img.shields.io/badge/built%20with-SpringBoot-green?style=for-the-badge&logo=springboot" alt="Built with Spring Boot">
</p>

## 🚀 Demo

- Documentation site: https://liamtoaldo.github.io/NonSenseGenerator/
- Screenshot (toxicity analysis example):  
  <img src="https://liamtoaldo.github.io/NonSenseGenerator/images/tutorial/toxicity-analysis.png" alt="Toxicity Analysis" width="640"/>

## ✨ Features

- Syntax analysis
  - Enter a sentence and get the visual parse tree (Chart.js).
  - Error handling when an external service is unavailable.
- “Nonsense” sentence generation
  - Extracts terms from input, reuses saved terms, and generates grammatically valid sentences.
  - Choose predefined templates or random; select verb tense (Present/Past/Future).
- Toxicity evaluation
  - Analyzes the generated sentence and returns per-category scores (Derogatory, Sexual, Violent, etc.).
- History
  - Endpoints to retrieve the history of generated sentences.
- REST API
  - Public endpoints for generation, dictionary/templates, toxicity, and history (see Manual/API).
- Modern UI
  - Bootstrap 5, jQuery 3, Chart.js and plugins for labels/graphs.

## 🛠️ Tech Stack

- Backend
  - Java 17+ (recommended)
  - Spring Boot
  - Maven (build/test)
  - JUnit 5 + Maven Surefire (test report)
  - Useful deps: JSON (org.json 20250517), Dotenv 3.0.0
- Frontend
  - Bootstrap 5.3.3
  - jQuery 3.7.1
  - Chart.js 4.5.0
  - Plugins: Chart.js Data Labels 2.2.0, Chart.js Chart Graphs 1.0.1
- Documentation
  - MkDocs + ReadTheDocs theme

Repository references:
- UI template: [nonsense/src/main/resources/templates/index.html](nonsense/src/main/resources/templates/index.html)
- UI script: [nonsense/src/main/resources/static/js/index.js](nonsense/src/main/resources/static/js/index.js)
- Manual (source): [docs/mkdocs/NonSenseGenerator/docs/manual.md](docs/mkdocs/NonSenseGenerator/docs/manual.md)
- Surefire test report (local copy): [docs/mkdocs/NonSenseGenerator/site/surefire-report/surefire-report.html](docs/mkdocs/NonSenseGenerator/site/surefire-report/surefire-report.html)

## Getting Started

Run the project locally.

### Prerequisites

- Java 17+ (JDK)
- Maven 3.9+
- (Optional/Recommended) Credentials/API keys for external NLP/Moderation services  
  Note: configuration details are in the Manual.

### ⚙️ Installation & Running

1. Clone the repository:
    ```sh
    git clone https://github.com/liamtoaldo/NonSenseGenerator.git
    cd NonSenseGenerator
    ```
2. Configure environment variables (if you use external services):
    - Create a `.env` file in the project root and set the keys required by the NLP/Moderation services.
    - For exact variable names and requirements, see the Manual:
      - Online: https://liamtoaldo.github.io/NonSenseGenerator/manual/
      - Local: docs/mkdocs/NonSenseGenerator/site/manual/index.html
    - Example (placeholder names, adjust to your setup):
      ```env
      NLP_API_KEY=your_key_here
      MODERATION_API_KEY=your_key_here
      ```
3. Run the application (recommended):
    ```sh
    mvn spring-boot:run
    ```
    Or build + run the JAR:
    ```sh
    mvn clean package
    java -jar target/*.jar
    ```
4. Open your browser at:
    ```
    http://localhost:8080
    ```

### ▶️ Usage (UI)

- Enter your sentence in “MY SENTENCE”.
- (Optional) Pick a Template or “Random Template”.
- (Optional) Select the Verb Tense.
- Run the analysis to see the parse tree.
- Generate a “nonsense” sentence in “GENERATED SENTENCE”.
- Run toxicity analysis and inspect per-category scores.

### 🧪 Testing

Run tests:
```sh
mvn test
```

Local reports:
- Surefire: docs/mkdocs/NonSenseGenerator/site/surefire-report/surefire-report.html  
  Example tests: `TemplateTest`, `SentenceGeneratorTest`, `APIClientTest`, `VerbTest`, `ModerationCategoryTest`.
- Unit Test Report: docs/mkdocs/NonSenseGenerator/site/unit_test_report/index.html

## 📚 Documentation

Complete documentation (architecture, design, user stories, system tests, test reports):
- Online: https://liamtoaldo.github.io/NonSenseGenerator/
- Local index: docs/mkdocs/NonSenseGenerator/site/index.html

Useful sections:
- Manual: docs/mkdocs/NonSenseGenerator/site/manual/index.html
- Design Document: docs/mkdocs/NonSenseGenerator/site/design_document/index.html
- User Stories: docs/mkdocs/NonSenseGenerator/site/user_stories/index.html
- System Test Document: docs/mkdocs/NonSenseGenerator/site/system_test_document/index.html
- Test Report (Surefire): docs/mkdocs/NonSenseGenerator/site/surefire-report/surefire-report.html

### 🧭 Build the docs locally (optional)

- Preview with MkDocs:
  ```sh
  pip install mkdocs
  mkdocs serve -f docs/mkdocs/NonSenseGenerator/mkdocs.yml
  ```
  Then open http://127.0.0.1:8000/

- Build static site:
  ```sh
  mkdocs build -f docs/mkdocs/NonSenseGenerator/mkdocs.yml -d docs/mkdocs/NonSenseGenerator/site
  ```

## 🤝 The Team (Group 4FB)

- [Liam Toaldo](https://github.com/liamtoaldo)
- [Matteo Zanon](https://github.com/sysnuts)
- [Juri Mason](https://github.com/jurimasonStudent)
- [Pietro Nicolini](https://github.com/Pleui)

## 📄 License

This project is distributed under the MIT License. See `LICENSE.txt` for details.