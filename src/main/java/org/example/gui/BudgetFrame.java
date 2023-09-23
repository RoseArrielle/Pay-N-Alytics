package org.example.gui;

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

    public BudgetFrame() {
        usersDAO = new UsersDAO();

        setTitle("Budget Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); // Center the frame on the screen

        JPanel mainContentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding

        // Create a welcome label at the top right with a larger font and space on top
        String username = getUserFromSessionOrAuthentication();
        welcomeLabel = new JLabel("Hello " + (username != null ? username : "Guest"));
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Increase font size
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        mainContentPanel.add(welcomeLabel, gbc);

        // Create a panel for the image
        imagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        // Load and resize the image
        ImageIcon imageIcon = new ImageIcon("src/main/resources/images/budget.jpg"); // Set the image path
        Image image = imageIcon.getImage();
        Image resizedImage = image.getScaledInstance(400, 200, Image.SCALE_SMOOTH); // Set desired width and height
        ImageIcon resizedImageIcon = new ImageIcon(resizedImage);
        imageLabel = new JLabel(resizedImageIcon);
        imagePanel.add(imageLabel);
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainContentPanel.add(imagePanel, gbc);

        // Create a panel for directions on how to create a new budget
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

        // Create buttons for creating a new budget and viewing saved budgets, centered
        createBudgetButton = new JButton("Create a New Budget");
        viewBudgetsButton = new JButton("View Saved Budgets");
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Center the buttons horizontally
        buttonsPanel.add(createBudgetButton);
        buttonsPanel.add(viewBudgetsButton);
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainContentPanel.add(buttonsPanel, gbc);

        createBudgetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // gotta add
            }
        });

        viewBudgetsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // gotta add
            }
        });

        // Create a footer panel for change password, log out, and delete account buttons, centered
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
                    // Delete the user's account
                    String username = getUsernameBasedOnAuthentication();
                    boolean isDeleted = usersDAO.deleteUser(usersDAO.getIdByUsername(username));

                    if (isDeleted) {
                        // Account deleted successfully
                        JOptionPane.showMessageDialog(null, "Your account has been deleted.", "Account Deleted", JOptionPane.INFORMATION_MESSAGE);

                        // Add code to navigate back to the login or main frame, as needed
                        // For example, if you want to return to the login frame:
                        dispose(); // Close the current frame
                        MainFrame mainFrame = new MainFrame();
                        mainFrame.setVisible(true);
                    } else {
                        // Account deletion failed
                        JOptionPane.showMessageDialog(null, "Failed to delete your account. Please try again.", "Deletion Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Create a JScrollPane to contain the main content panel with horizontal scrolling
        JScrollPane scrollPane = new JScrollPane(mainContentPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Add the scroll pane to the frame
        add(scrollPane);
    }

    // Example: Implement a method to retrieve the username or guest status based on your application's logic
    private String getUserFromSessionOrAuthentication() {
        boolean isAuthenticated = false;

        // Check if the user logged in through the LoginFrame
        if (LoginFrame.isLoggedIn()) {
            isAuthenticated = true;
        }

        // Check if the user registered through the RegisterFrame
        if (RegisterFrame.isRegistered()) {
            isAuthenticated = true;
        }

        if (isAuthenticated) {
            // User is authenticated, return the username or "Guest" based on your logic
            return getUsernameBasedOnAuthentication();
        } else {
            // User is not authenticated, return null or "Guest"
            return "Guest";
        }
    }

    private String getUsernameBasedOnAuthentication() {
        if (LoginFrame.isLoggedIn()) {
            // User is logged in, return the logged-in username
            return LoginFrame.getLoggedInUsername();
        } else if (RegisterFrame.isRegistered()) {
            // User is registered, return the registered username
            return RegisterFrame.getRegisteredUsername();
        } else {
            // If neither logged in nor registered, return "Guest" or handle it as needed
            return "Guest";
        }
    }

    private void clearUserAuthentication(){
        LoginFrame.setLoggedIn(false);
    }
}
