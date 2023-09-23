package org.example.gui;


import org.example.dao.BillsDAO;
import org.example.dao.BudgetDAO;
import org.example.dao.UsersDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


public class CreateBudgetFrame extends JFrame {
    private BillsDAO billsDAO;
    private BudgetDAO budgetDAO;
    private JLabel imageLabel;
    private JButton inputPaycheckButton;
    private JButton addApproxNetPayButton;

    private JButton addBillsButton;
    private JTextField billNameField;
    private JTextField billAmountField;
    private JButton saveButton;
    private JLabel remainingAmountLabel;
    private double remainingAmount;
    private List<Expense> expenses;
    private Double approximateNetPay;
    private int currentBudgetId;



    public CreateBudgetFrame(int currentBudgetId) {
        this.currentBudgetId = currentBudgetId;

        billsDAO = new BillsDAO();
        budgetDAO = new BudgetDAO();

        setTitle("Create Budget");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;

        // Initialize variables
        expenses = new ArrayList<>();
        remainingAmount = 0;

        // Image section
        ImageIcon imageIcon = new ImageIcon("src/main/resources/images/check-bills.png");
        Image image = imageIcon.getImage();
        Image resizedImage = image.getScaledInstance(400, 200, Image.SCALE_SMOOTH);
        ImageIcon resizedImageIcon = new ImageIcon(resizedImage);
        imageLabel = new JLabel(resizedImageIcon);
        panel.add(imageLabel);

        JPanel paycheckButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Use FlowLayout for left alignment
        inputPaycheckButton = new JButton("Get Approx. Paycheck Details");

        inputPaycheckButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String paycheckCalculatorUrl = "https://www.paycheckcity.com/calculator/hourly";

                try {
                    Desktop.getDesktop().browse(new URI(paycheckCalculatorUrl));
                } catch (IOException | URISyntaxException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(CreateBudgetFrame.this, "Failed to open the URL", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        paycheckButtonPanel.add(inputPaycheckButton);

        gbc.gridy++;
        panel.add(paycheckButtonPanel, gbc);


        // Add Approx Net Pay button section (next to Paycheck button)
        addApproxNetPayButton = new JButton("Add Approx Net Pay");

        addApproxNetPayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Prompt the user to input the approximate net pay
                String netPayInput = JOptionPane.showInputDialog(CreateBudgetFrame.this, "Enter Approximate Net Pay:", "Input Net Pay", JOptionPane.PLAIN_MESSAGE);

                try {
                    approximateNetPay = Double.parseDouble(netPayInput);
                    addBillsButton.setVisible(true);
                    remainingAmount = approximateNetPay;
                    remainingAmountLabel.setText("Remaining Amount: $" + remainingAmount+ " (Net Pay: $" + (approximateNetPay != null ? approximateNetPay : 0.00) + " )");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(CreateBudgetFrame.this, "Invalid input. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        paycheckButtonPanel.add(addApproxNetPayButton);

        addBillsButton = new JButton("Add Bills");
        gbc.gridy++;
        panel.add(addBillsButton, gbc);


        addBillsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create a dialog for adding bill details
                JDialog billDialog = new JDialog(CreateBudgetFrame.this, "Add Bill", true);
                billDialog.setLayout(new BorderLayout());

                // Declare final variables for billName and billAmountStr
                final JTextField billNameField = new JTextField(20);
                final JTextField billAmountField = new JTextField(10);

                // Bill name input field
                JPanel billNamePanel = new JPanel();
                billNamePanel.add(new JLabel("Bill Name: "));
                billNamePanel.add(billNameField);

                // Bill amount input field
                JPanel billAmountPanel = new JPanel();
                billAmountPanel.add(new JLabel("Amount: "));
                billAmountPanel.add(billAmountField);

                // Create a panel to hold the bill name and amount input fields
                JPanel inputPanel = new JPanel();
                inputPanel.setLayout(new GridLayout(1, 1));
                inputPanel.add(billNamePanel);
                inputPanel.add(billAmountPanel);

                // Add the input panel to the dialog
                billDialog.add(inputPanel, BorderLayout.CENTER);

                JButton addButton = new JButton("Add");
                JPanel buttonPanel = new JPanel();
                buttonPanel.add(addButton);

                // Add ActionListener for the "Add" button
                addButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String billName = billNameField.getText();
                        String billAmountStr = billAmountField.getText();

                        try {
                            double billAmount = Double.parseDouble(billAmountStr);
                            // Add the bill to the list of expenses
                            expenses.add(new Expense(billName, billAmount));
                            // Update the remaining amount
                            remainingAmount -= billAmount;
                            remainingAmountLabel.setText("Remaining Amount: $" + remainingAmount + " (Net Pay: $" + (approximateNetPay != null ? approximateNetPay : 0.00) + " )");
                            billDialog.dispose(); // Close the dialog after adding the bill
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(CreateBudgetFrame.this, "Invalid amount. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
                billDialog.add(buttonPanel, BorderLayout.SOUTH);

                billDialog.pack();
                billDialog.setLocationRelativeTo(CreateBudgetFrame.this);
                billDialog.setVisible(true);
            }
        });

        // Create an "Add" button
        JButton addButton = new JButton("Add");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String billName = billNameField.getText();
                String billAmountStr = billAmountField.getText();

                try {
                    double billAmount = Double.parseDouble(billAmountStr);
                    expenses.add(new Expense(billName, billAmount));
                    boolean addedToBudget = billsDAO.addBill(currentBudgetId, billName, billAmount);
                    if (!addedToBudget) {
                        JOptionPane.showMessageDialog(CreateBudgetFrame.this, "Failed to add bill to the budget.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    remainingAmount -= billAmount;
                    remainingAmountLabel.setText("Remaining Amount: $" + remainingAmount+ " (Net Pay: $" + (approximateNetPay != null ? approximateNetPay : 0.00) + " )");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(CreateBudgetFrame.this, "Invalid amount. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        remainingAmountLabel = new JLabel("Remaining Amount: $" + remainingAmount + " (Net Pay: $" + (approximateNetPay != null ? approximateNetPay : 0.00) + " )");
        remainingAmountLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center the text
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER; // Center the component
        panel.add(remainingAmountLabel, gbc);


        JPanel saveButtonPanel = new JPanel();
        saveButton = new JButton("Save");
        saveButtonPanel.add(saveButton);

        saveButton.setVisible(true); // Set the visibility to true

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement logic to save the budget and add it to the saved budget frame
            }
        });

        add(panel);

    }

    // Expense class to store bill information
    private class Expense {
        String name;
        double amount;

        public Expense(String name, double amount) {
            this.name = name;
            this.amount = amount;
        }
    }
}

