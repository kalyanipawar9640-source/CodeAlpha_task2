# PROJECT REPORT
## CodeAlpha Java Programming Task 3 – Artificial Intelligence Chatbot

### 1. Project Title
**Java-Based Artificial Intelligence Chatbot using NLP and TF-IDF**

### 2. Introduction
The Artificial Intelligence Chatbot is a Java desktop application designed for interactive communication with a user. The chatbot accepts natural-language questions through a graphical user interface and returns an appropriate response from its trained FAQ knowledge base.

The project demonstrates basic Artificial Intelligence and Natural Language Processing concepts without requiring an external API. It uses text preprocessing, TF-IDF feature representation, cosine similarity, intent matching, and a confidence threshold.

### 3. Objectives
- To develop an interactive chatbot using Java.
- To implement basic NLP techniques.
- To create a training/FAQ dataset for frequently asked questions.
- To match user questions with the most relevant intent.
- To provide a simple real-time GUI.
- To create a project that can be demonstrated easily in a Java programming internship task.

### 4. Technologies Used

| Technology | Purpose |
|---|---|
| Java | Main programming language |
| Java Swing | Graphical user interface |
| Collections Framework | Store training examples and responses |
| NLP preprocessing | Clean and normalize text |
| TF-IDF | Convert text into weighted vectors |
| Cosine Similarity | Measure similarity between questions |
| OOP | Organize application classes |

### 5. System Requirements

#### Hardware
- 4 GB RAM or more
- Any modern processor
- Around 100 MB free storage

#### Software
- JDK 8 or newer
- Command Prompt/Terminal
- Any Java IDE such as VS Code, IntelliJ IDEA, or Eclipse

### 6. Main Modules

#### Module 1 – GUI
`ChatbotApp.java` creates the Swing interface. It contains:
- Chat display area
- Input field
- Send button
- Clear button

#### Module 2 – Training Data
`ChatbotEngine.java` contains example sentences grouped into intents such as:
- greeting
- java
- oop
- jvm
- nlp
- chatbot
- codealpha
- internship
- cse
- programming
- help
- thanks
- goodbye
- time
- date

#### Module 3 – NLP Preprocessing
The input is:
1. Converted to lowercase.
2. Punctuation is removed.
3. Text is split into tokens.
4. Stop words are removed.
5. A small stemming step reduces common suffixes.

#### Module 4 – TF-IDF
TF-IDF gives a weight to words based on their frequency in a sentence and their frequency across the training collection.

In simplified form:

**TF-IDF = TF × IDF**

where TF is term frequency and IDF represents how informative a word is across the documents.

#### Module 5 – Cosine Similarity
The chatbot compares the user's TF-IDF vector with every trained example.

Cosine similarity is:

**cos(θ) = (A · B) / (|A| |B|)**

A higher value means the two texts are more similar.

#### Module 6 – Response Generation
The intent with the highest similarity score is selected. A response is then chosen from that intent's response list.

If the confidence is too low, the chatbot returns a fallback message instead of making a random guess.

### 7. Working Flow

```text
User enters question
        ↓
GUI receives input
        ↓
Text preprocessing
        ↓
Tokenization + stop-word removal
        ↓
TF-IDF vector creation
        ↓
Cosine similarity with training examples
        ↓
Best intent selected
        ↓
Response generated
        ↓
Response shown in GUI
```

### 8. Example

**User:** What is Java?

**Bot:** Java is a high-level, object-oriented, class-based programming language designed to be portable and platform independent through the JVM.

**User:** Explain OOP.

**Bot:** OOP means Object-Oriented Programming. Its four main pillars are Encapsulation, Inheritance, Polymorphism, and Abstraction.

### 9. Advantages
- Easy to run
- No internet connection required
- No external API key required
- Simple Java code
- Easy to extend with new FAQs
- Demonstrates real NLP concepts
- GUI makes the project interactive

### 10. Limitations
- The chatbot can answer only topics represented in its training examples.
- It is not a generative AI model.
- It does not learn automatically from every conversation.
- The quality of responses depends on the training examples.

### 11. Future Scope
- Add a database for FAQs.
- Add voice input and text-to-speech.
- Add a web interface.
- Use a larger NLP dataset.
- Add sentiment analysis.
- Add multilingual support.
- Connect to a machine-learning or LLM API.
- Add user login and conversation history.

### 12. Conclusion
The Java AI Chatbot successfully demonstrates an interactive chatbot using Java, Swing, NLP preprocessing, TF-IDF, cosine similarity, and a trained FAQ knowledge base. The project is lightweight, explainable, and suitable for demonstrating Java programming, OOP, GUI development, and introductory AI/NLP concepts.

### 13. Viva Questions

**Q1. What is the main purpose of this project?**  
To build an interactive Java chatbot that understands user questions using basic NLP and returns relevant FAQ responses.

**Q2. Which GUI technology is used?**  
Java Swing.

**Q3. What is NLP?**  
Natural Language Processing is a field of AI that helps computers process and understand human language.

**Q4. What is TF-IDF?**  
TF-IDF is a numerical technique that represents how important a word is in a text relative to a collection of texts.

**Q5. Why is cosine similarity used?**  
It measures the similarity between the user's text vector and the trained question vectors.

**Q6. Is this chatbot a large language model?**  
No. It is a lightweight educational chatbot using local training examples and similarity-based intent matching.

**Q7. How can you add new questions?**  
Add more training examples to the appropriate `addExamples()` call in `ChatbotEngine.java`.

**Q8. Why is a confidence threshold used?**  
It prevents the chatbot from giving an unrelated response when the user's question is too different from the training data.
