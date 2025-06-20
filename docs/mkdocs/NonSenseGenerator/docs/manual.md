# Official NonSenseGenerator manual
## Project Description
NonSenseGenerator is a web application that allows users to generate nonsensical sentences based on their input.<br>
It uses Google's Natural Language API to analyze sentence structure.<br>
It provides features for syntax analysis, sentence generation, toxicity analysis, and a public history log of generated sentences.<br>
The application is designed to be intuitive and user-friendly.<br>
The application is built using **HTML**, **CSS**, and **JavaScript**, with a backend powered by **Java** and **Spring Boot**.<br>
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

### Installation Steps

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/liamtoaldo/NonSenseGenerator.git
    ```

2.  **Navigate to the project directory:**
    ```bash
    cd NonSenseGenerator
    ```