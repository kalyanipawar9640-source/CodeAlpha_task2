import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * ChatbotEngine
 * A lightweight NLP + machine-learning-style intent matcher implemented
 * using TF-IDF vectors and cosine similarity. No external libraries required.
 */
public class ChatbotEngine {

    private static class TrainingExample {
        String intent;
        String text;

        TrainingExample(String intent, String text) {
            this.intent = intent;
            this.text = text;
        }
    }

    private final List<TrainingExample> trainingData = new ArrayList<>();
    private final Map<String, Double> idf = new HashMap<>();
    private final Set<String> stopWords = new HashSet<>(Arrays.asList(
            "a", "an", "the", "is", "are", "am", "to", "of", "in", "on",
            "for", "and", "or", "i", "me", "my", "you", "your", "can",
            "could", "would", "please", "tell", "about", "what", "how",
            "do", "does", "did", "be", "it", "this", "that", "with",
            "from", "as", "at", "we", "our"
    ));

    private final Map<String, List<String>> responses = new HashMap<>();
    private final Random random = new Random();

    public ChatbotEngine() {
        loadTrainingData();
        train();
    }

    /**
     * Adds FAQ examples. Adding more examples here "trains" the matcher
     * because the TF-IDF model is rebuilt from all training examples.
     */
    private void addExamples(String intent, String... examples) {
        for (String example : examples) {
            trainingData.add(new TrainingExample(intent, example));
        }
    }

    private void loadTrainingData() {
        addExamples("greeting",
                "hello", "hi", "hey", "good morning", "good evening",
                "good afternoon", "hello chatbot", "hi bot");

        addExamples("java",
                "what is java", "tell me about java",
                "what is java programming", "why learn java",
                "what are the features of java", "java programming language");

        addExamples("oop",
                "what is oops", "explain object oriented programming",
                "what are the four pillars of oops", "explain encapsulation",
                "explain inheritance", "explain polymorphism", "explain abstraction");

        addExamples("jvm",
                "what is jvm", "what is jre", "what is jdk",
                "difference between jdk jre and jvm");

        addExamples("nlp",
                "what is nlp", "what is natural language processing",
                "explain natural language processing", "how does nlp work");

        addExamples("chatbot",
                "what is a chatbot", "how does a chatbot work",
                "what is an ai chatbot", "explain chatbot");

        addExamples("codealpha",
                "what is codealpha", "tell me about codealpha",
                "what is this task", "what is the java programming task");

        addExamples("internship",
                "how can i prepare for internship", "how to get a software internship",
                "what skills are needed for internship", "how can i get a coding internship");

        addExamples("cse",
                "what is cse", "what does computer science mean",
                "what should a cse student learn", "important skills for cse students");

        addExamples("programming",
                "how do i learn programming", "best way to learn coding",
                "how can i improve coding", "how to practice programming");

        addExamples("help",
                "help", "what can you do", "show available questions",
                "what can i ask you", "give me help");

        addExamples("thanks",
                "thank you", "thanks", "thank you so much", "thanks bot");

        addExamples("goodbye",
                "bye", "goodbye", "see you", "exit", "quit");

        addExamples("time",
                "what is the time", "tell me the current time", "current time");

        addExamples("date",
                "what is today's date", "tell me today's date", "current date");

        responses.put("greeting", Arrays.asList(
                "Hello! 👋 I am your Java AI Chatbot. How can I help you?",
                "Hi! Nice to meet you. Ask me a Java, CSE, NLP, or internship question."
        ));

        responses.put("java", Arrays.asList(
                "Java is a high-level, object-oriented, class-based programming language designed to be portable and platform independent through the JVM.",
                "Java is widely used for desktop applications, web applications, Android development, enterprise software, and backend systems."
        ));

        responses.put("oop", Arrays.asList(
                "OOP means Object-Oriented Programming. Its four main pillars are Encapsulation, Inheritance, Polymorphism, and Abstraction.",
                "In Java, OOP organizes programs around classes and objects, making code reusable, modular, and easier to maintain."
        ));

        responses.put("jvm", Arrays.asList(
                "JVM (Java Virtual Machine) runs Java bytecode. JRE provides the JVM and libraries needed to run Java programs. JDK contains development tools plus the JRE."
        ));

        responses.put("nlp", Arrays.asList(
                "NLP (Natural Language Processing) is a branch of AI that helps computers understand and process human language. This project uses text normalization, tokenization, stop-word removal, TF-IDF, and cosine similarity."
        ));

        responses.put("chatbot", Arrays.asList(
                "A chatbot is software that communicates with users through natural language. This project matches a user's message with trained FAQ examples and returns the most suitable response."
        ));

        responses.put("codealpha", Arrays.asList(
                "CodeAlpha is an internship/training platform that provides project-based tasks. This project is designed as a Java Programming Task: an interactive AI chatbot with NLP, FAQ training, and a GUI."
        ));

        responses.put("internship", Arrays.asList(
                "For a software internship, focus on Java or Python, data structures, SQL, Git/GitHub, problem solving, communication, and 2–3 practical projects.",
                "Build projects that you can explain clearly. Keep the source code, README, screenshots, and a short project report in your GitHub repository."
        ));

        responses.put("cse", Arrays.asList(
                "For CSE, useful skills include programming, DSA, DBMS, operating systems, computer networks, Git/GitHub, SQL, web development, and basic cloud/AI concepts."
        ));

        responses.put("programming", Arrays.asList(
                "Learn programming by writing small programs daily. Start with variables, conditions, loops, methods, arrays, OOP, debugging, and then build real projects."
        ));

        responses.put("help", Arrays.asList(
                "You can ask me about Java, OOP, JVM/JDK/JRE, NLP, chatbots, CodeAlpha, internships, CSE skills, programming, date, or time. Try: 'What is Java?'"
        ));

        responses.put("thanks", Arrays.asList(
                "You're welcome! 😊", "Happy to help!"
        ));

        responses.put("goodbye", Arrays.asList(
                "Goodbye! Keep coding and keep learning. 🚀",
                "See you later! All the best for your project."
        ));
    }

    private void train() {
        idf.clear();

        Map<String, Integer> documentFrequency = new HashMap<>();
        int totalDocuments = trainingData.size();

        for (TrainingExample example : trainingData) {
            Set<String> uniqueTokens = new HashSet<>(preprocess(example.text));
            for (String token : uniqueTokens) {
                documentFrequency.put(token, documentFrequency.getOrDefault(token, 0) + 1);
            }
        }

        for (Map.Entry<String, Integer> entry : documentFrequency.entrySet()) {
            double value = Math.log((1.0 + totalDocuments) / (1.0 + entry.getValue())) + 1.0;
            idf.put(entry.getKey(), value);
        }
    }

    private List<String> preprocess(String text) {
        String cleaned = text.toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9\\s]", " ")
                .replaceAll("\\s+", " ")
                .trim();

        if (cleaned.isEmpty()) {
            return Collections.emptyList();
        }

        List<String> tokens = new ArrayList<>();
        for (String token : cleaned.split(" ")) {
            if (token.isEmpty() || stopWords.contains(token)) {
                continue;
            }

            // Very small stemmer for beginner-friendly NLP demonstration.
            if (token.length() > 5 && token.endsWith("ing")) {
                token = token.substring(0, token.length() - 3);
            } else if (token.length() > 4 && token.endsWith("ed")) {
                token = token.substring(0, token.length() - 2);
            } else if (token.length() > 4 && token.endsWith("s")) {
                token = token.substring(0, token.length() - 1);
            }

            tokens.add(token);
        }
        return tokens;
    }

    private Map<String, Double> tfidfVector(String text) {
        List<String> tokens = preprocess(text);
        Map<String, Integer> counts = new HashMap<>();

        for (String token : tokens) {
            counts.put(token, counts.getOrDefault(token, 0) + 1);
        }

        Map<String, Double> vector = new HashMap<>();
        if (tokens.isEmpty()) {
            return vector;
        }

        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            double tf = (double) entry.getValue() / tokens.size();
            double weight = tf * idf.getOrDefault(entry.getKey(), 1.0);
            vector.put(entry.getKey(), weight);
        }

        return vector;
    }

    private double cosineSimilarity(Map<String, Double> a, Map<String, Double> b) {
        if (a.isEmpty() || b.isEmpty()) {
            return 0.0;
        }

        double dot = 0.0;
        double normA = 0.0;
        double normB = 0.0;

        for (double value : a.values()) {
            normA += value * value;
        }

        for (double value : b.values()) {
            normB += value * value;
        }

        for (String key : a.keySet()) {
            dot += a.get(key) * b.getOrDefault(key, 0.0);
        }

        if (normA == 0.0 || normB == 0.0) {
            return 0.0;
        }

        return dot / (Math.sqrt(normA) * Math.sqrt(normB));
    }

    public String getResponse(String userInput) {
        if (userInput == null || userInput.trim().isEmpty()) {
            return "Please type a message so I can help you.";
        }

        String input = userInput.trim();
        String lower = input.toLowerCase(Locale.ROOT);

        // Direct utility intents.
        if (containsWholeWord(lower, "time") && (lower.contains("current") || lower.contains("now") || lower.contains("what"))) {
            return "Current time: " + LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss a"));
        }

        if (lower.contains("today") && lower.contains("date")) {
            return "Today's date: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy"));
        }

        Map<String, Double> inputVector = tfidfVector(input);
        double bestScore = 0.0;
        String bestIntent = null;

        for (TrainingExample example : trainingData) {
            double score = cosineSimilarity(inputVector, tfidfVector(example.text));

            // Small bonus for exact normalized match.
            if (normalizeForExactMatch(input).equals(normalizeForExactMatch(example.text))) {
                score += 0.35;
            }

            if (score > bestScore) {
                bestScore = score;
                bestIntent = example.intent;
            }
        }

        // Confidence threshold avoids random answers for unrelated questions.
        if (bestIntent == null || bestScore < 0.20) {
            return "Sorry, I don't understand that yet. Try asking about Java, OOP, NLP, chatbots, CSE, internships, or programming.";
        }

        List<String> options = responses.get(bestIntent);
        if (options == null || options.isEmpty()) {
            return "I found the topic, but I don't have a response for it yet.";
        }

        return options.get(random.nextInt(options.size()));
    }

    private boolean containsWholeWord(String text, String word) {
        return text.matches(".*\\b" + word + "\\b.*");
    }

    private String normalizeForExactMatch(String text) {
        return text.toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9\\s]", " ")
                .replaceAll("\\s+", " ")
                .trim();
    }

    public int getTrainingExampleCount() {
        return trainingData.size();
    }
}
