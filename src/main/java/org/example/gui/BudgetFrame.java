package org.example.gui;

import org.example.dao.BudgetDAO;
import org.example.dao.UsersDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BudgetFrame extends JFrame {
    private static UsersDAO usersDAO;
    private JLabel welcomeLabel;
    private JPanel imagePanel;
    private JLabel imageLabel;
    private JPanel directionsPanel;
    private JButton createBudgetButton;
    private JButton viewBudgetsButton;
    private JPanel footerPanel;
    private JButton changePasswordButton;
    private JButton logOutButton;
    private JButton deleteAccountButton;
    private JTextField budgetNameField;
    private BudgetDAO budgetDAO;
    private int currentBudgetId;

    public BudgetFrame() {
        usersDAO = new UsersDAO();
        budgetDAO = new BudgetDAO();

        setTitle("Budget Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JPanel mainContentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        String username = getUserFromSessionOrAuthentication();
        welcomeLabel = new JLabel("Hello " + (username != null ? username : "Guest"));
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        mainContentPanel.add(welcomeLabel, gbc);

        imagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        ImageIcon imageIcon = new ImageIcon("src/main/resources/images/budget.jpg");
        Image image = imageIcon.getImage();
        Image resizedImage = image.getScaledInstance(400, 200, Image.SCALE_SMOOTH);
        ImageIcon resizedImageIcon = new ImageIcon(resizedImage);
        imageLabel = new JLabel(resizedImageIcon);
        imagePanel.add(imageLabel);

        gbc.gridx = 0;
        gbc.gridy = 1;
        mainContentPanel.add(imagePanel, gbc);

        directionsPanel = new JPanel();
        directionsPanel.setLayout(new BoxLayout(directionsPanel, BoxLayout.PAGE_AXIS));
        String[] directions = {
                "To create a new budget:",
                "- Click the 'Create a New Budget' button below.",
                "- Enter the details of your budget, including bill names and amounts.",
                "- The app will automatically calculate your remaining balance.",
                "- Click 'Save Budget' to keep a record of your budget."
        };
        for (String direction : directions) {
            JLabel directionLabel = new JLabel(direction);
            directionLabel.setForeground(Color.BLACK);
            directionsPanel.add(directionLabel);
        }
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainContentPanel.add(directionsPanel, gbc);

        createBudgetButton = new JButton("Create a New Budget");
        viewBudgetsButton = new JButton("View Saved Budgets");
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonsPanel.add(createBudgetButton);
        buttonsPanel.add(viewBudgetsButton);
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainContentPanel.add(buttonsPanel, gbc);

        createBudgetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (!(getUsernameBasedOnAuthentication().equals("Guest"))) {
                    String budgetName = JOptionPane.showInputDialog(BudgetFrame.this, "Enter Budget Name:", "Create New Budget", JOptionPane.PLAIN_MESSAGE);
                    if (budgetName != null && !budgetName.isEmpty()) {
                        int budgetId = budgetDAO.createNewBudget(budgetName);
                        if (budgetId != -1) {
                            currentBudgetId = budgetId; // Save the budget ID
                            dispose();
                            CreateBudgetFrame createBudgetFrame = new CreateBudgetFrame(currentBudgetId);
                            createBudgetFrame.setVisible(true);
                        } else {
                            JOptionPane.showMessageDialog(BudgetFrame.this, "Failed to create the budget.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        // Handle the case where the user cancels or provides an empty budget name
                        JOptionPane.showMessageDialog(BudgetFrame.this, "Budget name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    // User is not authenticated, display an error message or redirect to login/registration
                    JOptionPane.showMessageDialog(BudgetFrame.this, "Please log in or register to create a budget.", "Authentication Required", JOptionPane.ERROR_MESSAGE);
                    dispose();
                    MainFrame mainFrame = new MainFrame();
                    mainFrame.setVisible(true);
                }
            }
        });

        viewBudgetsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // gotta add
            }
        });

        footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        changePasswordButton = new JButton("Change Password");
        logOutButton = new JButton("Log Out");
        deleteAccountButton = new JButton("Delete Account");
        footerPanel.add(changePasswordButton);
        footerPanel.add(logOutButton);
        footerPanel.add(deleteAccountButton);
        gbc.gridx = 0;
        gbc.gridy = 4;
        mainContentPanel.add(footerPanel, gbc);

        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                ChangePasswordFrame changePasswordFrame = new ChangePasswordFrame();
                changePasswordFrame.setVisible(true);
            }
        });

        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearUserAuthentication();
                dispose();
                MainFrame mainFrame = new MainFrame();
                mainFrame.setVisible(true);
            }
        });

        deleteAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete your account?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);

                if (choice == JOptionPane.YES_OPTION) {
                    String username = getUsernameBasedOnAuthentication();
                    boolean isDeleted = usersDAO.deleteUser(usersDAO.getIdByUsername(username));

                    if (isDeleted) {
                        JOptionPane.showMessageDialog(null, "Your account has been deleted.", "Account Deleted", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                        MainFrame mainFrame = new MainFrame();
                        mainFrame.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to delete your account. Please try again.", "Deletion Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(mainContentPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        add(scrollPane);
    }

    private String getUserFromSessionOrAuthentication() {
        boolean isAuthenticated = false;

        if (LoginFrame.isLoggedIn()) {
            isAuthenticated = true;
        }

        if (RegisterFrame.isRegistered()) {
            isAuthenticated = true;
        }

        if (isAuthenticated) {
            return getUsernameBasedOnAuthentication();
        } else {
            return "Guest";
        }
    }

    private String getUsernameBasedOnAuthentication() {
        if (LoginFrame.isLoggedIn()) {
            return LoginFrame.getLoggedInUsername();
        } else if (RegisterFrame.isRegistered()) {
            return RegisterFrame.getRegisteredUsername();
        } else {
            return "Guest";
        }
    }

    private void clearUserAuthentication(){
        LoginFrame.setLoggedIn(false);
    }
}
