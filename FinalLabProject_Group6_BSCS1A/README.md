# Calculator Application

This repository contains a simple yet functional calculator application built using Java Swing. It provides standard arithmetic operations, a calculation history, and a theme toggle for a personalized user experience.

---

## Project Structure

This project consists of a single Java file, FinalLabProject_Group6_BSCS1A.java, which contains the entire source code for the calculator application.

---

## Features

The calculator offers the following key features:

* *Basic Arithmetic Operations*: Supports addition (+), subtraction (-), multiplication (x), division (÷), and modulo (%).
* *Unary Operations*: Includes functions for square root (√), squaring (x²), and reciprocal (1/x).
* *Sign Toggle*: Easily change the sign of the current number (positive/negative) using the +/- button.
* *Clear Functions*: C (Clear All) and CE (Clear Entry) buttons for input management.
* *Backspace Functionality*: ← button to remove the last entered digit or operator.
* *Calculation History*:
    * Keeps a record of previous calculations.
    * Click on any entry in the history to load its result into the main display for further calculations.
    * Toggle history visibility (▼`/`▶) and clear history (🗑) with dedicated buttons.
* *Theme Toggle*: Switch between a *Light Mode* and *Dark Mode* to suit user preference, dynamically updating the UI's look and feel.
* *Error Handling*: Displays "Error" on the screen for invalid expressions or mathematical errors (e.g., division by zero).
* *Responsive UI*: Utilizes Java Swing components and layout managers for a visually appealing and organized interface.
* *Custom Buttons*: Implements a RoundedButton class for a modern, soft aesthetic with rounded corners.

---

## How to Run

To run this calculator application, follow these steps:

1.  *Ensure Java is Installed*: Make sure you have a Java Development Kit (JDK) installed on your system. You can download it from the [Oracle website](https://www.oracle.com/java/technologies/downloads/) or use an open-source alternative like OpenJDK.

2.  *Save the Source Code*: Save the provided Java code into a file named FinalLabProject_Group6_BSCS1A.java.

3.  *Compile the Code*: Open a terminal or command prompt, navigate to the directory where you saved the file, and compile the Java code using the Java compiler:

    
    javac FinalLabProject_Group6_BSCS1A.java
    

4.  *Run the Application*: After successful compilation, run the application using the Java Virtual Machine:

    
    java FinalLabProject_Group6_BSCS1A
    

    A new window will appear displaying the calculator interface.

---

## Code Explanation

The core logic of the calculator is encapsulated within the FinalLabProject_Group6_BSCS1A class.

* **FinalLabProject_Group6_BSCS1A() (Constructor)**:
    * Initializes all Swing UI components: JFrame, JTextField (for display), JTextArea (for history), JPanels for layout (buttonPanel, topPanel, mainPanel, etc.), and JScrollPane for the history area.
    * Sets up the layout using BorderLayout and GridLayout.
    * Configures fonts, alignments, and padding for a clean appearance.
    * Adds MouseListener to the historyArea to enable clicking on past results.
    * Creates and configures control buttons for history (toggle and clear) and theme.
    * Iterates through a predefined array of button labels to create all calculator buttons, assigning a common ActionListener (buttonPressed).
    * Calls applyTheme() to set the initial look and feel.

* **buttonPressed(ActionEvent e)**:
    * This is the central event handler for all calculator button clicks.
    * It retrieves the text of the clicked button (command).
    * Handles various commands:
        * C and CE: Clears the current input and resets the error state.
        * ←: Implements backspace functionality.
        * =: Triggers the evaluation of the expression using the eval() method, adds the expression and result to historyList, updates historyArea, and sets the result as the new currentInput. Includes error handling for invalid expressions.
        * +/-: Toggles the sign (positive/negative) of the current number.
        * √, x², 1/x: Calls the unary() method to perform the respective unary operation.
        * Digits and Operators: Appends them to the currentInput
StringBuilder.
    * Updates the display
JTextField with the currentInput.
    * Prevents further operations if the calculator is in an "Error" state until C or CE is pressed.

* **unary(java.util.function.DoubleUnaryOperator op)**:
    * A helper method for unary operations (single operand, e.g., square root).
    * Parses the currentInput to a double, applies the given DoubleUnaryOperator, and updates currentInput with the result.
    * Includes error handling for invalid input.

* **updateHistory()**:
    * Refreshes the historyArea
JTextArea.
    * Iterates through the historyList in reverse order to display the most recent calculations at the top.

* **eval(String expr)**:
    * This is a crucial method responsible for parsing and evaluating mathematical expressions.
    * It uses an anonymous inner class to implement a *recursive descent parser*.
    * **nextChar()**: Advances the parser to the next character.
    * **eat(int charToEat)**: Consumes the expected character, skipping whitespace.
    * **parse()**: The entry point for parsing, starting with parseExpression().
    * **parseExpression()**: Handles addition and subtraction (lowest precedence).
    * **parseTerm()**: Handles multiplication, division, and modulo (medium precedence).
    * **parseFactor()**: Handles numbers, parenthesized expressions, and unary plus/minus (highest precedence).
    * Throws RuntimeException for unexpected characters or syntax errors during parsing, which are caught by buttonPressed to display "Error".

* **applyTheme()**:
    * Dynamically changes the background and foreground colors of various UI components based on the isDarkMode flag.
    * Applies distinct colors for operators and the equals button for visual differentiation.

* **isOperator(String text)**:
    * A utility method to identify if a button's text corresponds to an operator or control function, used for theme styling.

* **RoundedButton (Static Inner Class)**:
    * Extends JButton to create custom buttons with rounded corners.
    * Overrides paintComponent to draw a fillRoundRect for the button's background, enabling anti-aliasing for smooth edges.

* **main(String[] args)**:
    * The application's entry point.
    * Uses SwingUtilities.invokeLater() to ensure that GUI creation and updates are performed on the Event Dispatch Thread (EDT), which is crucial for thread safety and proper Swing behavior.

---

## Group 6 - BSCS1A

This project was developed by Group 6 from BSCS1A.
   * Joshua Dredd Stephen B. De Guzman
   * Charles Derrick A. Garcia
   * Marc Andrei G. Bersabe
---
## Note
During the development of this Java calculator project, we encountered a compatibility issue with the ScriptEngine API, which was not supported or caused errors under our installed JDK version. To resolve this, we used AI tools to help reconstruct the evaluation logic in a way that works properly with our environment.

We also used AI assistance in refining some design elements such as applying icons to buttons and improving layout spacing to enhance the overall user interface and user experience.

While AI was used for compatibility fixes and visual improvements, the rest of the code, including the structure, logic flow, and features, was developed solely by us as the project developers. The use of AI was limited to support and refinement purposes only, ensuring we retained full understanding and control over the project.
