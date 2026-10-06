import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * CodeAlpha Task 3 - Artificial Intelligence Chatbot
 * Java Swing GUI application.
 */
public class ChatbotApp extends JFrame {

    private final ChatbotEngine chatbot;
    private final JTextArea chatArea;
    private final JTextField inputField;

    public ChatbotApp() {
        chatbot = new ChatbotEngine();

        setTitle("CodeAlpha - Java AI Chatbot");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(760, 600);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(new EmptyBorder(12, 12, 12, 12));
        root.setBackground(new Color(245, 247, 250));
        setContentPane(root);

        JLabel title = new JLabel("🤖 CodeAlpha AI Chatbot");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setBorder(new EmptyBorder(4, 4, 8, 4));
        root.add(title, BorderLayout.NORTH);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatArea.setFont(new Font("SansSerif", Font.PLAIN, 15));
        chatArea.setBackground(Color.WHITE);
        chatArea.setBorder(new EmptyBorder(12, 12, 12, 12));

        JScrollPane scrollPane = new JScrollPane(chatArea);
        root.add(scrollPane, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new BorderLayout(8, 8));
        bottom.setOpaque(false);

        inputField = new JTextField();
        inputField.setFont(new Font("SansSerif", Font.PLAIN, 15));
        inputField.setToolTipText("Type your question here...");
        inputField.addActionListener(this::sendMessage);

        JButton sendButton = new JButton("Send");
        sendButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        sendButton.addActionListener(this::sendMessage);

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> {
            chatArea.setText("");
            showWelcomeMessage();
        });

        bottom.add(inputField, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        buttons.setOpaque(false);
        buttons.add(clearButton);
        buttons.add(sendButton);

        bottom.add(buttons, BorderLayout.EAST);
        root.add(bottom, BorderLayout.SOUTH);

        showWelcomeMessage();
    }

    private void showWelcomeMessage() {
        appendBot("Hello! 👋\nI am a Java-based AI chatbot using simple NLP and TF-IDF similarity.\n\nTry:\n• What is Java?\n• Explain OOP\n• What is NLP?\n• How can I prepare for internship?\n• What is a chatbot?");
    }

    private void sendMessage(ActionEvent event) {
        String message = inputField.getText().trim();

        if (message.isEmpty()) {
            return;
        }

        appendUser(message);
        inputField.setText("");

        String response = chatbot.getResponse(message);
        appendBot(response);
    }

    private void appendUser(String message) {
        chatArea.append("\nYou: " + message + "\n");
        chatArea.setCaretPosition(chatArea.getDocument().getLength());
    }

    private void appendBot(String message) {
        chatArea.append("\nBot: " + message + "\n");
        chatArea.setCaretPosition(chatArea.getDocument().getLength());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChatbotApp app = new ChatbotApp();
            app.setVisible(true);
        });
    }
}
