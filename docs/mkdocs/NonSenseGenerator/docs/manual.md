# Official NonSenseGenerator manual
## Project Description
NonSenseGenerator is a web application that allows users to generate nonsensical sentences based on their input.<br>
It uses Google's Natural Language API to analyze sentence structure.<br>
It provides features for syntax analysis, sentence generation, toxicity analysis, and a public history log of generated sentences.<br>
The application is designed to be intuitive and user-friendly.<br>
## Technologies Used
The NonSenseGenerator application is built using a combination of frontend and backend technologies, libraries, and plugins. 
<br>Below is a summary of the tech stack:

| Layer              | Technology                     | Version             |
|--------------------|--------------------------------|---------------------|
| Backend            | Java                           | 21                  |
| Backend            | Spring Boot                    | 3.1.4               |
| Backend            | Thymeleaf                      | (Managed by Spring) |
| Libraries (UI/UX)  | Bootstrap                      | 5.3.3               |
| Libraries (DOM)    | jQuery                         | 3.7.1               |
| Libraries (Charts) | Chart.js                       | 4.5.0               |
| Chart.js Plugins   | Chart.js Chart Graphs Plugin   | 1.0.1               |
| Chart.js Plugins   | Chart.js Data Labels Plugin    | 2.2.0               |
| Libraries (Java)   | JSON                           | 20250517            |
| Libraries (Java)   | Dotenv                         | 3.0.0               |
| Testing            | JUnit 5                        | (Managed by Spring) |

## Installation
To install and run the NonSenseGenerator application, follow these steps:

### Prerequisites

- **Java 21 or higher**:
    - Check if you have Java installed by running:
    ```bash
    java -version
    ```
    - If not installed, download and install it from [Oracle's official website](https://www.oracle.com/java/technologies/javase-jdk21-downloads.html) or use your OS package manager.

- **Maven** (not mandatory, since it's provided in the project through the `nonsense/mvnw` wrapper for Linux and MacOS or `nonsense\mvnw.cmd` for Windows)

- A **Google Cloud account** with the Natural Language API enabled
    - Follow the instructions in the [Google Cloud documentation](https://cloud.google.com/natural-language/docs/setup) to set up your account and enable the API.
    - IMPORTANT: Create the `nonsense/.env` file in the `nonsense` directory to store your API key.
    - Obtain your API key and set it as an environment variable inside `nonsense/.env`:
    ```bash
    GOOGLE_NLP_API_KEY="your_api_key_here"
    ```

### Installation Steps
1.  **Clone the repository:**
    ```bash
    git clone https://github.com/liamtoaldo/NonSenseGenerator.git
    ```

2.  **Navigate to the project directory:**
    ```bash
    cd NonSenseGenerator
    cd nonsense
    ```
3.  **Install dependencies:**
    - If you have Maven installed, run:
    ```bash
    mvn install
    ```
    - If you are using the provided wrapper, run:
    ```bash
    ./mvnw install  # For Linux and MacOS
    mvnw.cmd install  # For Windows
    ```
#### Running without building
4.  **Run the application:**
    - If you have Maven installed, run:
    ```bash
    mvn spring-boot:run
    ```
    - If you are using the provided wrapper, run:
    ```bash
    ./mvnw spring-boot:run  # For Linux and MacOS
    mvnw.cmd spring-boot:run  # For Windows
    ```
#### Building and running the application
4. **Build the application:**
    - If you have Maven installed, run:
    ```bash
    mvn clean package
    ```
    - If you are using the provided wrapper, run:
    ```bash
    ./mvnw clean package  # For Linux and MacOS
    mvnw.cmd clean package  # For Windows
    ```
5. **Run the application:**
    ```bash
    java -jar target/nonsense-0.0.1-SNAPSHOT.jar
    ```
## Usage
Once the application is running, you can access it in your web browser at `http://localhost:8080`
### Syntax Analysis
To analyze the syntax of a sentence, enter the sentence in the input field and click the "Analyze Syntax" button. The application will display the syntax tree and part-of-speech tags for each word in the sentence.
![Syntax Analysis Tutorial](images/tutorial/syntax-analysis.png)
### Sentence Generation
To generate a nonsensical sentence, enter a sentence in the input field and click the "Generate" button.<br>
You can select a pre-defined template or let the system choose one for you.<br>
You can also choose the generated sentence's tense (present, past, or future) or use present tense by default.<br>
The application will display the generated sentence in the output field.
![Sentence Generation Tutorial 1](images/tutorial/sentence-generation1.png)
<hr>
![Sentence Generation Tutorial 2](images/tutorial/sentence-generation2.png)
### Toxicity Analysis
To analyze the toxicity of a sentence after generating it, click the "Analyze Toxicity" button inside the generated sentence field.<br>