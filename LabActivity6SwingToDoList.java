import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class LabActivity6SwingToDoList {
    public static void main(String[] args) {
            JFrame mainFrame = new JFrame("My To-Do List");
            mainFrame.setSize(700, 400);
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            // This is the main table
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Task");
            model.addColumn("Description");
            model.addColumn("Status");
            
            JTable table = new JTable(model);
            JScrollPane scrollPane = new JScrollPane(table);
            
            // This is the add task button
            JButton addButton = new JButton("Add Task");
            addButton.setPreferredSize(new Dimension(100, 30));
            addButton.addActionListener(e -> {
                showAddTaskDialog(mainFrame, model);
            });
            
            // this just centers the button
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            buttonPanel.add(addButton);
            
            // Layout
            JPanel panel = new JPanel(new BorderLayout());
            panel.add(buttonPanel, BorderLayout.NORTH);
            panel.add(scrollPane, BorderLayout.CENTER);
            
            mainFrame.add(panel);
            mainFrame.setVisible(true);
        ;
    }
    
    private static void showAddTaskDialog(JFrame parent, DefaultTableModel model) {
        JDialog dialog = new JDialog(parent, "Add New Task", true);
        dialog.setSize(400, 300);
        
        // Main panel with box layout
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
    //This section contains the codes for the pop up window of the add task
        // Task name
        JLabel nameLabel = new JLabel("Task Name:");
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        JTextField nameField = new JTextField();
        nameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        nameField.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Task description
        JLabel descLabel = new JLabel("Description:");
        descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        JTextArea descArea = new JTextArea(3, 20);
        descArea.setLineWrap(true);
        JScrollPane descScroll = new JScrollPane(descArea);
        descScroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        descScroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        
        // Status
        JLabel statusLabel = new JLabel("Status:");
        statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Not Started", "Ongoing", "Completed"});
        statusCombo.setAlignmentX(Component.LEFT_ALIGNMENT);
        statusCombo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        
        // Save button
        JButton saveButton = new JButton("Save");
        saveButton.setPreferredSize(new Dimension(80, 25));
        
        // Add the components with spacing
        panel.add(nameLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(nameField);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(descLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(descScroll);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(statusLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(statusCombo);
        panel.add(Box.createRigidArea(new Dimension(0, 25)));
        
        // Create a separate panel for the centered button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttonPanel.add(saveButton);
        panel.add(buttonPanel);
        
        //ion know how to add the red octagon to this message T-T
        saveButton.addActionListener(e -> {
            if (nameField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Please enter task name!");
                return;
            }
            
            model.addRow(new Object[]{
                nameField.getText(),
                descArea.getText(),
                statusCombo.getSelectedItem()
            });
            
            dialog.dispose();
        });
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
}