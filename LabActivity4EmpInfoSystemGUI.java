import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LabActivity4EmpInfoSystemGUI extends JFrame {
    // these just define(?) the text fields so that they can be used in the frame
    JTextField tfFirstName, tfLastName, tfAge, tfHoursWorked, tfHourlyRate;
    JLabel lbWelcome;

    public void Initialize() {
        // Form Fields
        tfFirstName = new JTextField();
        tfLastName = new JTextField();
        tfAge = new JTextField();
        tfHoursWorked = new JTextField();
        tfHourlyRate = new JTextField();

        // Form panel that sets the texts and text fields in the form
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(5, 2, 10, 10));
        formPanel.setMaximumSize(new Dimension(400, 200)); // Optional limit

        formPanel.add(new JLabel("First Name"));
        formPanel.add(tfFirstName);

        formPanel.add(new JLabel("Last Name"));
        formPanel.add(tfLastName);

        formPanel.add(new JLabel("Age"));
        formPanel.add(tfAge);

        formPanel.add(new JLabel("Hours Worked"));
        formPanel.add(tfHoursWorked);

        formPanel.add(new JLabel("Hourly Rate"));
        formPanel.add(tfHourlyRate);

        // Welcome Label
        lbWelcome = new JLabel();
        lbWelcome.setAlignmentX(Component.CENTER_ALIGNMENT);


        // Output label
        JLabel outputLabel = new JLabel("Output:");

        JTextArea outputArea = new JTextArea(5, 30); // height = 5 rows
        outputArea.setEditable(false); // read-only
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Output panel (para may layout sila)
        JPanel outputPanel = new JPanel();
        outputPanel.setLayout(new BorderLayout());
        outputPanel.add(outputLabel, BorderLayout.NORTH);
        outputPanel.add(scrollPane, BorderLayout.CENTER);


        // Add to SOUTH of main frame
        add(outputPanel, BorderLayout.SOUTH);

        // Button
        JButton btnOK = new JButton("OK");
        btnOK.setAlignmentX(Component.CENTER_ALIGNMENT); // Center sa layout

        btnOK.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
              // Get input values
              String firstname = tfFirstName.getText();
              String lastname = tfLastName.getText();
              String ageText = tfAge.getText();
              String hoursWorkedText = tfHoursWorked.getText();
              String hourlyRateText = tfHourlyRate.getText();
      
              // Check if any field is empty
              if (firstname.isEmpty() || lastname.isEmpty() || ageText.isEmpty() ||
                  hoursWorkedText.isEmpty() || hourlyRateText.isEmpty()) {
                  outputArea.setText("Please fill out all fields!");
                  return; // Stop here if any field is empty
              }
      
              // Validate if age is a number
              int age = 0;
              try {
                  age = Integer.parseInt(ageText);  // Try to convert the age to an integer
              } catch (NumberFormatException ex) {
                  outputArea.setText("Error: Age must be a valid integer."); // Error if age is not a number
                  return; // Stop if age is invalid
              }
      
              // Validate and parse the other fields (hoursWorked and hourlyRate)
              double hoursWorked = 0;
              double hourlyRate = 0;
              try {
                  hoursWorked = Double.parseDouble(hoursWorkedText);
                  hourlyRate = Double.parseDouble(hourlyRateText);
              } catch (NumberFormatException ex) {
                  outputArea.setText("Error: Hours worked and hourly wage must be valid numbers"); // Error if either is not a number
                  return; // Stop if hoursWorked or hourlyRate is invalid
              }
      
              // Compute total pay if everything is valid
              double totalPay = hoursWorked * hourlyRate;
      
              // Format the output text with currency symbol
              String outputText = String.format(
                  "Full name: %s %s\nAge: %d years old\nDaily Salary: PHP%.2f",
                  firstname, lastname, age, totalPay
              );
      
              // Display the result
              outputArea.setText(outputText);
            }
        });
  
        // Main Panel (Vertical Stack)
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        mainPanel.add(formPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Space
        mainPanel.add(btnOK);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(lbWelcome);

        add(mainPanel);

        // Frame setup
        setTitle("Welcome");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center sa screen
        setVisible(true);

    }

    public static void main(String[] args) {
        MainFrame myFrame = new MainFrame();
        myFrame.Initialize();
    }
}
