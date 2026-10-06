# CodeAlpha Task 3 – Artificial Intelligence Chatbot

A complete Java-based chatbot project for the **CodeAlpha Java Programming Task 3**.

## Features

- Java-based interactive chatbot
- GUI using Java Swing
- NLP preprocessing:
  - lowercase normalization
  - punctuation removal
  - tokenization
  - stop-word removal
  - basic stemming
- TF-IDF text representation
- Cosine similarity for intent matching
- FAQ/training dataset with multiple examples
- Confidence threshold for unknown questions
- Dynamic date and time responses
- No external libraries required

## Technologies

- Java
- Java Swing
- OOP
- NLP concepts
- TF-IDF
- Cosine Similarity
- Collections Framework

## Project Structure

```text
CodeAlpha_Java_AI_Chatbot/
│
├── src/
│   ├── ChatbotApp.java
│   └── ChatbotEngine.java
│
├── PROJECT_REPORT.md
├── README.md
└── .gitignore
```

## Requirements

- JDK 8 or newer
- Windows, Linux, or macOS
- Command Prompt / Terminal

Check Java:

```bash
java -version
javac -version
```

## Run the Project

Open terminal inside the project folder.

### Windows

```bash
javac -d out src\ChatbotEngine.java src\ChatbotApp.java
java -cp out ChatbotApp
```

### Linux/macOS

```bash
javac -d out src/ChatbotEngine.java src/ChatbotApp.java
java -cp out ChatbotApp
```

A GUI window will open.

## Example Questions

- Hello
- What is Java?
- Explain OOP
- What is JVM?
- What is NLP?
- What is a chatbot?
- What is CodeAlpha?
- How can I prepare for internship?
- What skills should a CSE student learn?
- What is the current time?
- What is today's date?

## How the AI/NLP Part Works

1. The project stores multiple training sentences for each intent.
2. The user's message is cleaned and tokenized.
3. Common stop words are removed.
4. TF-IDF weights are calculated.
5. The user's vector is compared with each training sentence using cosine similarity.
6. The highest-scoring intent is selected.
7. A response from that intent's response bank is displayed.
8. If the score is below the confidence threshold, the chatbot gives a fallback response.

## Adding More Training Data

Open `ChatbotEngine.java` and add more examples:

```java
addExamples("java",
        "what is java",
        "tell me about java",
        "why should I learn java",
        "new question about java");
```

Then rebuild the project.

## Important Note

This is a lightweight educational AI/NLP chatbot. It does not use a large language model or an online AI API. Its intelligence comes from the local training examples, TF-IDF representation, and similarity matching. This makes the project easy to run and explain during a viva.

## GitHub Submission

Recommended repository name:

`CodeAlpha-Java-AI-Chatbot`

Upload:

- `src/`
- `README.md`
- `PROJECT_REPORT.md`

Do not upload generated `.class` files or the `out/` folder.
