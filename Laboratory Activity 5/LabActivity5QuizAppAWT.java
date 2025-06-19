import java.awt.*;
import java.awt.event.*;

public class LabActivity5QuizAppAWT extends Frame {
    // This section contains the indexing or 'storage' of text for the quiz
    private final String[] questions = {
        "What does CPU stand for?",
        "Which data structure uses LIFO (Last-In-First-Out) principle?",
        "What is the time complexity of binary search?"
    };
    
    private final String[][] choices = {
        {"A. Central Processing Unit", "B. Computer Processing Unit", "C. Central Process Unit", "D. Computer Process Unit"},
        {"A. Queue", "B. Stack", "C. Array", "D. Tree"},
        {"A. O(1)", "B. O(n)", "C. O(log n)", "D. O(n²)"}
    };
    
    private final char[] answers = {'A', 'B', 'C'}; // Correct answers
    
    // UI Components
    private Label questionLabel;
    private CheckboxGroup choicesGroup;
    private Checkbox[] choiceButtons;
    private Button nextButton;
    private Label scoreLabel;
    private Label messageLabel;
    
    // Quiz state
    private int currentQuestion = 0;
    private int score = 0;
    
    public LabActivity5QuizAppAWT() {
        // This section contains the frame setup
        setTitle("Computer Science Quiz");
        setSize(500, 300);
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(240, 240, 240)); // Light gray background
        
        // Window closing event handler
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        
        // Question label with custom font
        questionLabel = new Label(questions[currentQuestion], Label.CENTER);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        questionLabel.setForeground(new Color(0, 0, 128)); // Navy blue text
        add(questionLabel, BorderLayout.NORTH);
        
        // Score label with custom style
        scoreLabel = new Label("", Label.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        scoreLabel.setForeground(Color.DARK_GRAY);
        add(scoreLabel, BorderLayout.SOUTH);
        
        // Choice panel
        Panel choicesPanel = new Panel(new GridLayout(2, 2, 15, 15)); // Increased gaps
        choicesPanel.setBackground(new Color(240, 240, 240));
        choicesGroup = new CheckboxGroup();
        choiceButtons = new Checkbox[4];
        
        // Different colors for each choice 
        Color[] choiceColors = {new Color(0, 100, 0),   // Dark green
                              new Color(139, 0, 0),    // Dark red
                              new Color(0, 0, 139),    // Dark blue
                              new Color(128, 0, 128)}; // Purple
        
        for (int i = 0; i < 4; i++) {
            choiceButtons[i] = new Checkbox(choices[currentQuestion][i], choicesGroup, false);
            choiceButtons[i].setFont(new Font("Arial", Font.BOLD, 13)); // Bold choices
            choiceButtons[i].setForeground(choiceColors[i]); // Different colors
            choicesPanel.add(choiceButtons[i]);
        }
        
        add(choicesPanel, BorderLayout.CENTER);
        
        // Message label for valdiating user choice
        messageLabel = new Label("", Label.CENTER);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        messageLabel.setForeground(Color.BLACK);
        
        // Setup for the 'next' button
        nextButton = new Button("Next Question");
        nextButton.setFont(new Font("Arial", Font.BOLD, 14));
        nextButton.setBackground(new Color(255, 140, 0)); // Dark orange background
        nextButton.setForeground(Color.WHITE);
        
        // Add hover effect because why not
        nextButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                nextButton.setBackground(new Color(255, 165, 0)); // Lighter orange
            }
            public void mouseExited(MouseEvent e) {
                nextButton.setBackground(new Color(255, 140, 0)); // Dark orange
            }
        });
        
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Checkbox selected = choicesGroup.getSelectedCheckbox();
                
                if (selected == null) {
                    messageLabel.setText("Please select an answer!");
                    return;
                }
                
                messageLabel.setText("");
                checkAnswer();
                currentQuestion++;
                
                if (currentQuestion < questions.length) {
                    updateQuestion();
                } else {
                    showFinalScore();
                }
            }
        });
        
        // Panel organization
        Panel bottomPanel = new Panel(new BorderLayout());
        bottomPanel.setBackground(new Color(240, 240, 240));
        bottomPanel.add(messageLabel, BorderLayout.NORTH);
        bottomPanel.add(nextButton, BorderLayout.CENTER);
        
        add(bottomPanel, BorderLayout.AFTER_LAST_LINE);
    }
    
    private void updateQuestion() {
        questionLabel.setText(questions[currentQuestion]);
        choicesGroup.setSelectedCheckbox(null);
        
        for (int i = 0; i < 4; i++) {
            choiceButtons[i].setLabel(choices[currentQuestion][i]);
        }
        
        scoreLabel.setText("Current score: " + score + " of " + currentQuestion);
    }
    
    private void checkAnswer() {
        Checkbox selected = choicesGroup.getSelectedCheckbox();
        if (selected != null && selected.getLabel().charAt(0) == answers[currentQuestion]) {
            score++;
        }
    }
    
    private void showFinalScore() {
        questionLabel.setText("Quiz completed! Your Score: " + score + " of " + questions.length + "!");
        questionLabel.setForeground(new Color(0, 100, 0)); // Green color
        
        for (Checkbox cb : choiceButtons) {
            cb.setEnabled(false);
        }
        
        nextButton.setEnabled(false);
        scoreLabel.setText("");
    }
    
    public static void main(String[] args) {
        LabActivity5QuizAppAWT app = new LabActivity5QuizAppAWT();
        app.setVisible(true);
    }
}