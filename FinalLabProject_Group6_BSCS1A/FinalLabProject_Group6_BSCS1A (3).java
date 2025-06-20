import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.function.DoubleUnaryOperator;

public class FinalLabProject_Group6_BSCS1A {
    private JFrame frame;
    private JTextField display;
    private JTextArea historyArea;
    private JPanel buttonPanel, topPanel;
    private JPanel mainPanel, fixedBottomPanel, themePanel, themeButtonHolder, historyControlPanel;
    private ArrayList<String> historyList = new ArrayList<>();
    private StringBuilder currentInput = new StringBuilder();
    private boolean isDarkMode = false;
    private boolean historyVisible = false;
    private JButton themeToggle, toggleHistoryButton, clearHistoryButton;
    private JScrollPane scrollPane;
    private boolean isErrorState = false;

    public FinalLabProject_Group6_BSCS1A() {
        // Initialize the main frame of the calculator
        frame = new JFrame("Calculator");
        frame.setSize(500, 740); // Set the size of the window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Define close operation
        frame.setLocationRelativeTo(null); // Center the window on the screen

        mainPanel = new JPanel(new BorderLayout()); // Use BorderLayout for overall layout

        // Panel for the calculator's display area
        topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 30, 10)); // Add padding

        // The text field where numbers and results are shown
        display = new JTextField();
        display.setFont(new Font("Segoe UI", Font.BOLD, 60)); // Set font for clear visibility
        display.setHorizontalAlignment(JTextField.RIGHT); // Align text to the right
        display.setEditable(false); // Prevent direct user editing
        display.setFocusable(false); // Remove focus border (blue box)
        topPanel.add(display, BorderLayout.CENTER);

        // Text area to show calculation history
        historyArea = new JTextArea();
        historyArea.setFont(new Font("Arial", Font.PLAIN, 16)); // Font for history entries
        historyArea.setEditable(false); // History is not editable
        historyArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Inner padding for text
        historyArea.setOpaque(true); // Ensure background color is visible
        historyArea.setCaretColor(new Color(0, 0, 0, 0)); // Make caret (text cursor) invisible

        // Add a mouse listener to historyArea to allow clicking on past results
        historyArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    // Get the character offset at the clicked point
                    int offset = historyArea.viewToModel2D(e.getPoint());
                    // Determine the line number corresponding to the offset
                    int lineNum = historyArea.getLineOfOffset(offset);
                    // Extract the full text of the clicked line
                    String clickedLine = historyArea.getText().substring(historyArea.getLineStartOffset(lineNum), historyArea.getLineEndOffset(lineNum)).trim();

                    // If the line contains '=', extract the result part
                    int equalsIndex = clickedLine.indexOf("=");
                    if (equalsIndex != -1) {
                        String result = clickedLine.substring(equalsIndex + 1).trim();
                        currentInput = new StringBuilder(result); // Set result to current input
                        display.setText(currentInput.toString()); // Update the main display
                        isErrorState = false; // Reset error state after loading history
                    } else {
                        // If no '=', use the whole line (e.g., a simple number without an operation)
                        currentInput = new StringBuilder(clickedLine);
                        display.setText(currentInput.toString());
                        isErrorState = false; // Reset error state
                    }
                } catch (Exception ex) {
                    System.err.println("Error processing history click: " + ex.getMessage());
                }
            }
        });

        // Scroll pane for the history area, allowing it to be scrollable if content overflows
        scrollPane = new JScrollPane(historyArea);
        scrollPane.setPreferredSize(new Dimension(500, 100)); // Set preferred size
        scrollPane.getViewport().setBorder(null); // Remove default viewport border
        scrollPane.setBorder(null); // Remove default scroll pane border
        scrollPane.setOpaque(true); // Ensure background color is visible
        scrollPane.getViewport().setOpaque(true); // Ensure viewport background color is visible
        scrollPane.setVisible(historyVisible); // Set initial visibility based on historyVisible flag

        // Panel for history control buttons (toggle history visibility, clear history)
        historyControlPanel = new JPanel();
        historyControlPanel.setLayout(new FlowLayout(FlowLayout.RIGHT)); // Align buttons to the right
        historyControlPanel.setOpaque(false); // Make transparent to blend with parent

        // Button to toggle history area visibility
        toggleHistoryButton = new JButton(historyVisible ? "▼" : "▶"); // Set initial icon based on visibility
        toggleHistoryButton.setFocusPainted(false); // No focus border
        toggleHistoryButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Hand cursor on hover
        toggleHistoryButton.setContentAreaFilled(false); // Transparent button background
        toggleHistoryButton.setBorderPainted(false); // No button border
        toggleHistoryButton.setOpaque(false); // Transparent
        toggleHistoryButton.addActionListener(e -> {
            historyVisible = !historyVisible; // Toggle the visibility flag
            scrollPane.setVisible(historyVisible); // Show or hide the scroll pane
            toggleHistoryButton.setText(historyVisible ? "▼" : "▶"); // Change icon based on visibility
            frame.revalidate(); // Re-layout components after visibility change
        });

        // Button to clear all history entries
        clearHistoryButton = new JButton("🗑");
        clearHistoryButton.setFocusPainted(false);
        clearHistoryButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        clearHistoryButton.setContentAreaFilled(false);
        clearHistoryButton.setBorderPainted(false);
        clearHistoryButton.setOpaque(false);
        clearHistoryButton.addActionListener(e -> {
            historyList.clear(); // Clear the underlying data list
            updateHistory(); // Refresh the history display
        });

        historyControlPanel.add(toggleHistoryButton);
        historyControlPanel.add(clearHistoryButton);

        // Panel for the calculator's numerical and operator buttons
        buttonPanel = new JPanel(new GridLayout(6, 4, 10, 10)); // Grid layout for 6 rows, 4 columns, with gaps
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 10, 10, 10)); // Padding around buttons

        // Labels for all calculator buttons
        String[] buttons = {
            "%", "CE", "C", "←",
            "1/x", "x²", "√", "÷",
            "7", "8", "9", "x",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "+/-", "0", ".", "="
        };

        // Loop to create and configure each button
        for (String text : buttons) {
            RoundedButton btn = new RoundedButton(text); // Use custom RoundedButton class
            btn.setFont(new Font("Segoe UI", Font.BOLD, 20)); // Set button font
            btn.addActionListener(this::buttonPressed); // Assign the button press handler
            btn.setFocusable(false); // Remove focus indicator
            btn.setMargin(new Insets(5, 10, 5, 10)); // Set internal button padding
            buttonPanel.add(btn); // Add button to the panel
        }

        // Bottom panel for dynamic content like history and theme toggle
        fixedBottomPanel = new JPanel(new BorderLayout());
        fixedBottomPanel.setOpaque(false); // Ensure transparent background

        // Panel containing the theme toggle button
        themePanel = new JPanel(new BorderLayout());
        themeButtonHolder = new JPanel();
        themeButtonHolder.setLayout(new BoxLayout(themeButtonHolder, BoxLayout.X_AXIS)); // Horizontal layout for the button
        themeButtonHolder.setOpaque(false); // Transparent
        themeButtonHolder.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 10)); // Padding

        // Button to toggle light/dark theme
        themeToggle = new JButton("💡");
        themeToggle.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 22)); // Use emoji for icon
        themeToggle.setContentAreaFilled(false); // Transparent background
        themeToggle.setBorderPainted(false); // No border
        themeToggle.setFocusPainted(false); // No focus indicator
        themeToggle.setOpaque(false); // Transparent
        themeToggle.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Hand cursor on hover
        themeToggle.setToolTipText("Toggle Light/Dark Mode"); // Tooltip
        themeToggle.setAlignmentX(Component.RIGHT_ALIGNMENT); // Align icon to the right
        themeToggle.setForeground(isDarkMode ? Color.WHITE : Color.BLACK); // Set initial color
        themeToggle.addActionListener(e -> {
            isDarkMode = !isDarkMode; // Toggle theme state
            applyTheme(); // Apply the new theme
        });

        themeButtonHolder.add(Box.createHorizontalGlue()); // Pushes theme toggle to the right
        themeButtonHolder.add(themeToggle);
        themePanel.add(themeButtonHolder, BorderLayout.EAST); // Add theme button to theme panel

        // Assemble the fixed bottom panel
        fixedBottomPanel.add(historyControlPanel, BorderLayout.NORTH); // History controls at the top
        fixedBottomPanel.add(scrollPane, BorderLayout.CENTER); // Scrollable history in the center
        fixedBottomPanel.add(themePanel, BorderLayout.SOUTH); // Theme toggle at the bottom

        // Add all major panels to the main frame
        mainPanel.add(topPanel, BorderLayout.NORTH); // Display at the top
        mainPanel.add(buttonPanel, BorderLayout.CENTER); // Buttons in the center
        mainPanel.add(fixedBottomPanel, BorderLayout.SOUTH); // History and theme controls at the bottom

        frame.setContentPane(mainPanel); // Set the main panel as the frame's content
        applyTheme(); // Apply the initial theme settings
        frame.setVisible(true); // Make the application window visible
    }

    /**
     * Handles the logic for each button press on the calculator.
     * It manages the current input, performs calculations, and updates the display/history.
     * @param e The ActionEvent triggered by a button click.
     */
    private void buttonPressed(ActionEvent e) {
        String command = ((JButton) e.getSource()).getText();

        // If in an error state, only allow 'C' (Clear) or 'CE' (Clear Entry) to reset.
        if (isErrorState && !command.equals("C") && !command.equals("CE")) {
            return; // Ignore other button presses until error is cleared
        }

        switch (command) {
            case "C" -> { // Clear all current input and reset error state
                currentInput.setLength(0);
                isErrorState = false;
            }
            case "CE" -> { // Clear the current entry (similar to C in this simple calculator)
                currentInput.setLength(0);
                isErrorState = false;
            }
            case "←" -> { // Backspace: remove the last character from current input
                if (currentInput.length() > 0)
                    currentInput.setLength(currentInput.length() - 1);
            }
            case "=" -> { // Evaluate the expression and display the result
                try {
                    // Prepare the expression for evaluation by replacing display symbols with math operators
                    String expr = currentInput.toString().replace("x", "*").replace("÷", "/");
                    // Add space before and after operators, but NOT if preceded by another operator or opening parenthesis
                    expr = expr.replaceAll("(?<![+\\-*/%])([+\\-*/%])", " $1 ");
                    // Handle unary minus at the beginning of the expression that might have been separated
                    if (expr.startsWith("- ")) {
                        expr = "-" + expr.substring(2);
                    }
                    expr = expr.replaceAll(" {2,}", " ").trim(); // Clean up any extra spaces

                    double result = eval(expr); // Perform the actual calculation
                    // Format the result: display as integer if no decimal, otherwise as double
                    String resultStr = (result == (long) result) ? String.format("%d", (long) result) : String.valueOf(result);
                    
                    // Format the expression for adding to history with spaces around operators
                    String formattedExpr = currentInput.toString().replace("x", " x ").replace("÷", " ÷ ");
                    formattedExpr = formattedExpr.replaceAll("([+\\-*/%])", " $1 ");
                    formattedExpr = formattedExpr.replaceAll(" {2,}", " ").trim();

                    historyList.add(formattedExpr + " = " + resultStr); // Add the calculation to history
                    updateHistory(); // Refresh the history display
                    currentInput = new StringBuilder(resultStr); // Set the result as the new current input
                    isErrorState = false; // Clear error state on successful calculation
                } catch (Exception ex) {
                    currentInput = new StringBuilder("Error"); // Display "Error" on calculation failure
                    isErrorState = true; // Set the error state
                }
            }
            case "+/-" -> { // Toggle the sign of the current number
                int len = currentInput.length();
                if (len == 0 || isErrorState) {
                    return;
                }

                int numEndIndex = len;
                int numStartIndex = len;

                // Scan backward to find the start of the number/operand itself (digits and dot)
                while (numStartIndex > 0 && 
                       (Character.isDigit(currentInput.charAt(numStartIndex - 1)) || currentInput.charAt(numStartIndex - 1) == '.')) {
                    numStartIndex--;
                }

                // Check for a unary minus immediately preceding the number
                boolean hasLeadingMinus = false;
                if (numStartIndex > 0 && currentInput.charAt(numStartIndex - 1) == '-') {
                    // This minus is unary if it's at the start of the expression, or preceded by a binary operator or opening parenthesis
                    if (numStartIndex - 1 == 0 || isOperator(currentInput.charAt(numStartIndex - 2) + "") || currentInput.charAt(numStartIndex - 2) == '(') {
                        hasLeadingMinus = true;
                        numStartIndex--; // Include this leading minus in the segment
                    }
                }
                
                String numStr = currentInput.substring(numStartIndex, numEndIndex);

                if (numStr.isEmpty() || numStr.equals(".") || numStr.equals("-")) {
                    // If the segment is empty, just a dot, or just a dangling minus, don't toggle.
                    return;
                }

                try {
                    double num = Double.parseDouble(numStr);
                    // If the number is 0, +/- 0 should remain 0.
                    if (num == 0) {
                        return;
                    }
                    
                    double toggledNum = -num;
                    String newNumStr;
                    if (toggledNum == (long) toggledNum) {
                        newNumStr = String.format("%d", (long) toggledNum);
                    } else {
                        newNumStr = String.valueOf(toggledNum);
                    }

                    currentInput.replace(numStartIndex, numEndIndex, newNumStr);

                } catch (NumberFormatException ex) {
                    System.err.println("Cannot parse part for +/-: " + numStr + " Error: " + ex.getMessage());
                }
            }
            case "√" -> { // Perform square root on the last number
                performUnaryOnLastNumber(Math::sqrt, "√("); // Using √ as prefix
            }
            case "x²" -> { // Perform squaring on the last number
                performUnaryOnLastNumber(a -> a * a, "sqr(");
            }
            case "1/x" -> { // Perform reciprocal (1 divided by x) on the last number
                performUnaryOnLastNumber(a -> 1 / a, "1/("); // Using 1/( as prefix
            }
            default -> { // For digits and other operators, append to current input
                // Allow '-' to be entered after an operator or opening parenthesis to form a negative number
                if (command.equals("-") && currentInput.length() > 0 &&
                    (isOperator(currentInput.charAt(currentInput.length() - 1) + "") || currentInput.charAt(currentInput.length() - 1) == '(')) {
                    currentInput.append(command);
                } else if (isOperator(command) && currentInput.length() > 0 && isOperator(currentInput.charAt(currentInput.length() - 1) + "")) {
                    // Replace the last operator if a new operator is pressed
                    currentInput.setCharAt(currentInput.length() - 1, command.charAt(0));
                } else {
                    currentInput.append(command);
                }
            }
        }

        display.setText(currentInput.toString()); // Update the main display text
    }

    /**
     * Performs a unary (single-operand) mathematical operation on the current input.
     * Examples include square root, squaring, or reciprocal.
     * @param op A DoubleUnaryOperator representing the operation to apply (e.g., Math::sqrt).
     */
    private void unary(java.util.function.DoubleUnaryOperator op) {
        // This method is now effectively superseded by performUnaryOnLastNumber for specific unary buttons,
        // but it's retained for completeness if there were other general unary uses.
        try {
            double val = Double.parseDouble(currentInput.toString()); // Parse current input to a double
            currentInput = new StringBuilder(Double.toString(op.applyAsDouble(val))); // Apply the operation and update input
            isErrorState = false; // Reset error state on successful unary operation
        } catch (Exception e) {
            currentInput = new StringBuilder("Error"); // Display "Error" if input is invalid
            isErrorState = true; // Set error state
        }
    }

    /**
     * Extracts the last number from the current input, applies a unary operation to it,
     * and replaces the number in the input string with the result.
     * It also logs this operation to the history panel.
     * @param op The unary operation to apply.
     * @param historyPrefix The prefix to use for history (e.g., "sqrt(").
     */
    private void performUnaryOnLastNumber(DoubleUnaryOperator op, String historyPrefix) {
        int len = currentInput.length();
        if (len == 0 || isErrorState) {
            return;
        }

        int numEndIndex = len;
        int numStartIndex = len;

        // Scan backward to find the start of the number/operand itself (digits and dot)
        while (numStartIndex > 0 && 
               (Character.isDigit(currentInput.charAt(numStartIndex - 1)) || currentInput.charAt(numStartIndex - 1) == '.')) {
            numStartIndex--;
        }

        // Check for a unary minus immediately preceding the number
        if (numStartIndex > 0 && currentInput.charAt(numStartIndex - 1) == '-') {
            // This minus is unary if it's at the start of the expression, or preceded by a binary operator or opening parenthesis
            if (numStartIndex - 1 == 0 || isOperator(currentInput.charAt(numStartIndex - 2) + "") || currentInput.charAt(numStartIndex - 2) == '(') {
                numStartIndex--; // Include this leading minus in the segment
            }
        }
        
        String numStr = currentInput.substring(numStartIndex, numEndIndex);

        if (numStr.isEmpty() || numStr.equals(".") || numStr.equals("-")) {
            // If the segment is empty, just a dot, or just a dangling minus, treat as error or ignore
            currentInput = new StringBuilder("Error");
            isErrorState = true;
            return;
        }

        try {
            double val = Double.parseDouble(numStr);
            double result = op.applyAsDouble(val);

            // Handle invalid results like sqrt of negative, or division by zero, etc.
            if (!Double.isFinite(result)) { 
                currentInput = new StringBuilder("Error");
                isErrorState = true;
                return;
            }

            String resultStr = (result == (long) result) ? String.format("%d", (long) result) : String.valueOf(result);

            // Replace the last number with the result
            currentInput.replace(numStartIndex, numEndIndex, resultStr);

            // Add to history
            historyList.add(historyPrefix + numStr + ") = " + resultStr);
            updateHistory();
            isErrorState = false;

        } catch (NumberFormatException ex) {
            currentInput = new StringBuilder("Error");
            isErrorState = true;
            System.err.println("Error parsing number for unary operation: " + numStr + " Error: " + ex.getMessage());
        }
    }


    /**
     * Refreshes the content of the historyArea JTextArea.
     * It iterates through the historyList in reverse order to show the latest calculations first.
     */
    private void updateHistory() {
        StringBuilder sb = new StringBuilder();
        // Loop from the most recent history entry to the oldest
        for (int i = historyList.size() - 1; i >= 0; i--) {
            sb.append(historyList.get(i)).append("\n"); // Append each entry followed by a newline
        }
        historyArea.setText(sb.toString()); // Set the formatted string to the history text area
    }

    /**
     * Evaluates a mathematical expression string.
     * This method implements a simple recursive descent parser to handle basic arithmetic operations
     * including addition, subtraction, multiplication, division, and modulo, respecting operator precedence.
     * It also supports unary plus/minus and parenthesized expressions.
     * @param expr The mathematical expression string to be evaluated.
     * @return The numerical result of the expression.
     * @throws RuntimeException if the expression contains unexpected characters or is syntactically invalid.
     */
    private double eval(String expr) {
        return new Object() {
            int pos = -1, ch; // 'pos' is current character position, 'ch' is the character at 'pos'

            // Advances to the next character in the expression string
            void nextChar() {
                ch = (++pos < expr.length()) ? expr.charAt(pos) : -1;
            }

            // Consumes the expected character if it matches the current character, skipping whitespace.
            // Returns true if the character was eaten, false otherwise.
            boolean eat(int charToEat) {
                while (ch == ' ') nextChar(); // Skip any whitespace
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            // Main entry point for parsing and evaluation.
            double parse() {
                nextChar(); // Start by getting the first character
                double x = parseExpression(); // Parse the highest-level expression
                // After parsing, if there are still characters left, it's an unexpected syntax error
                if (pos < expr.length()) throw new RuntimeException("Unexpected: " + (char) ch);
                return x;
            }

            // Parses an additive or subtractive expression (lowest precedence)
            double parseExpression() {
                double x = parseTerm(); // First parse a term
                while (true) {
                    if (eat('+')) x += parseTerm(); // Handle addition
                    else if (eat('-')) x -= parseTerm(); // Handle subtraction
                    else return x; // No more addition/subtraction, return current result
                }
            }

            // Parses a multiplicative, divisive, or modulo term (medium precedence)
            double parseTerm() {
                double x = parseFactor(); // First parse a factor
                while (true) {
                    if (eat('*')) x *= parseFactor(); // Handle multiplication
                    else if (eat('/')) x /= parseFactor(); // Handle division
                    else if (eat('%')) x %= parseFactor(); // Handle modulo
                    else return x; // No more multiplication/division/modulo, return current result
                }
            }

            // Parses a factor: a number, a parenthesized expression, or a unary plus/minus (highest precedence)
            double parseFactor() {
                if (eat('+')) return +parseFactor(); // Handle unary plus
                if (eat('-')) return -parseFactor(); // Handle unary minus

                double x;
                int startPos = this.pos; // Mark the start position for number/parenthesis
                if (eat('(')) { // Handle parenthesized expressions
                    x = parseExpression(); // Recursively parse the inner expression
                    if (!eat(')')) throw new RuntimeException("Missing ')'"); // Expect closing parenthesis
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // Handle numbers (integers or decimals)
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar(); // Read all digits and one decimal point
                    x = Double.parseDouble(expr.substring(startPos, this.pos)); // Convert the string part to a double
                } else {
                    throw new RuntimeException("Unexpected: " + (char) ch); // Unknown character encountered
                }
                return x;
            }
        }.parse(); // Create an anonymous object and call its parse method
    }

    /**
     * Applies the selected theme (light or dark mode) to all visual components of the calculator.
     * This method dynamically changes background and foreground colors of panels, text fields, and buttons.
     */
    private void applyTheme() {
        Color bg = isDarkMode ? new Color(30, 30, 30) : Color.WHITE; // Determine background color based on theme
        Color fg = isDarkMode ? Color.WHITE : Color.BLACK; // Determine foreground (text) color

        // Apply background colors to the main components
        frame.getContentPane().setBackground(bg);
        mainPanel.setBackground(bg);
        topPanel.setBackground(bg);
        buttonPanel.setBackground(bg);
        themePanel.setBackground(bg);
        themeButtonHolder.setBackground(bg);
        historyControlPanel.setBackground(bg);
        fixedBottomPanel.setBackground(bg);

        // Apply colors to the display text field
        display.setBackground(bg);
        display.setForeground(fg);
        display.setBorder(null); // Ensure no border on the display

        // Apply colors to the history area and its scroll pane
        historyArea.setBackground(bg);
        historyArea.setForeground(fg);
        scrollPane.setBackground(bg); // Background for scroll bar area
        scrollPane.getViewport().setBackground(bg); // Background for the visible area of the history

        // Apply colors to the control buttons for history and theme
        themeToggle.setForeground(isDarkMode ? Color.WHITE : Color.BLACK);
        clearHistoryButton.setForeground(isDarkMode ? Color.WHITE : Color.BLACK);
        toggleHistoryButton.setForeground(isDarkMode ? Color.WHITE : Color.BLACK);

        // Iterate through all calculator buttons and apply theme-specific colors
        for (Component comp : buttonPanel.getComponents()) {
            if (comp instanceof RoundedButton) {
                RoundedButton btn = (RoundedButton) comp;
                btn.setForeground(fg); // Set button text color
                // Apply different background colors for operators vs. numbers/special buttons
                if (isOperator(btn.getText())) {
                    btn.setBackground(isDarkMode ? new Color(70, 70, 70) : new Color(240, 240, 240));
                } else if (btn.getText().equals("=")) {
                    // Apply a distinct accent color for the equals button
                    btn.setBackground(isDarkMode ? new Color(0, 120, 215) : new Color(0, 120, 215));
                    btn.setForeground(Color.WHITE); // White text on the accent button
                } else {
                    btn.setBackground(isDarkMode ? new Color(50, 50, 50) : new Color(220, 220, 220));
                }
            }
        }
    }

    /**
     * Helper method to determine if a given button text represents a mathematical operator or a control button.
     * This is used to apply different styling to operator buttons.
     * @param text The string text of the button.
     * @return true if the text is an operator or control button, false otherwise (e.g., a number or decimal).
     */
    private boolean isOperator(String text) {
        return text.equals("÷") || text.equals("x") || text.equals("-") || text.equals("+") || text.equals("%") ||
               text.equals("CE") || text.equals("C") || text.equals("←") || text.equals("1/x") ||
               text.equals("x²") || text.equals("√") || text.equals("+/-");
    }

    /**
     * A custom JButton subclass that paints itself with rounded corners.
     * This provides a modern, soft aesthetic to the calculator buttons.
     */
    static class RoundedButton extends JButton {
        public RoundedButton(String label) {
            super(label);
            setContentAreaFilled(false); // Disable default content area painting
            setFocusPainted(false); // Disable painting of the focus border
            setBorderPainted(false); // Disable painting of the default border
            setBackground(new Color(220, 220, 220)); // Default background color (light mode)
            setForeground(Color.BLACK); // Default text color
        }

        @Override
        protected void paintComponent(Graphics g) {
            // Get a 2D graphics context
            Graphics2D g2 = (Graphics2D) g.create();
            // Enable anti-aliasing for smooth edges
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Set the fill color: darker when the button is pressed, otherwise its normal background
            g2.setColor(getModel().isPressed() ? getBackground().darker() : getBackground());
            // Fill a rounded rectangle for the button's shape
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30); // 30x30 for corner radius
            
            g2.dispose(); // Release graphics resources
            super.paintComponent(g); // Call superclass method to draw text and icon on top of the shape
        }
    }

    /**
     * Main method to run the CalculatorApp.
     * It ensures that the GUI creation and updates are performed on the Event Dispatch Thread (EDT)
     * for thread safety and proper Swing behavior.
     * @param args Command line arguments (not used in this application).
     */
    public static void main(String[] args) {
        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        SwingUtilities.invokeLater(FinalLabProject_Group6_BSCS1A::new);
    }
}
