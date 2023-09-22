package org.example.gui;

import org.example.dao.UsersDAO;
import org.example.model.Users;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    private static boolean isLoggedIn = false;
    private static String loggedInUsername;

    private static UsersDAO usersDAO;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton backButton;
    private JLabel imageLabel; // Add the label for the image

    public static boolean isLoggedIn() {
        return isLoggedIn;
    }
    public static void setLoggedIn(boolean value) {
        isLoggedIn = value;
    }

    public LoginFrame() {
        usersDAO = new UsersDAO();

        setTitle("Login");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600); // Increase the frame height
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;

        // Create a JLabel for the image
        ImageIcon imageIcon = resizeImage("src/main/resources/images/welcome-back.png",500,250); // Replace with actual image path
        imageLabel = new JLabel(imageIcon);
        panel.add(imageLabel, gbc);

        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; // Fill horizontally
        gbc.insets = new Insets(0, 150, 0, 150); // Add spacing
        gbc.weightx = 2.0; // Allow horizontal expansion

        JPanel inputPanel = new JPanel(new GridLayout(2, 1));
        Font labelFont = new Font("Arial", Font.BOLD, 18); // Adjust the font size as needed

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(labelFont); // Set the font for the label
        usernameField = new JTextField();


        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(labelFont); // Set the font for the label
        passwordField = new JPasswordField();


        inputPanel.add(usernameLabel);
        inputPanel.add(usernameField);
        inputPanel.add(passwordLabel);
        inputPanel.add(passwordField);
        panel.add(inputPanel, gbc);

        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER; // Center-align the login button
        loginButton = new JButton("Login");
        backButton = new JButton("Back");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Left-align the back button
        buttonPanel.add(backButton);
        buttonPanel.add(Box.createHorizontalGlue()); // Add horizontal space
        buttonPanel.add(loginButton);
        panel.add(buttonPanel, gbc);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Users user;
                // Retrieve the entered username and password
                String enteredUsername = usernameField.getText();
                char[] enteredPasswordChars = passwordField.getPassword();
                String enteredPassword = new String(enteredPasswordChars);

                // Use the getUserByUsername method to fetch the user's data
                user = usersDAO.getUserByUsername(enteredUsername);

                if (user != null) {
                    // User with the entered username exists
                    // Check if the entered password matches the stored password
                    String storedPassword = user.getPassword();

                    if (enteredPassword.equals(storedPassword)) {
                        // Passwords match, successful login
                        isLoggedIn = true;
                        loggedInUsername = enteredUsername; // Store the logged-in username
                        dispose(); // Close the login frame
                        BudgetFrame budgetFrame = new BudgetFrame();
                        budgetFrame.setVisible(true);
                    } else {
                        // Passwords do not match, show an error message
                        JOptionPane.showMessageDialog(LoginFrame.this, "Invalid password", "Login Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    // User with the entered username does not exist, show an error message
                    JOptionPane.showMessageDialog(LoginFrame.this, "User not found", "Login Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Add code to go back to the main frame
                dispose(); // Close the current frame
                MainFrame mainFrame = new MainFrame();
                mainFrame.setVisible(true);
            }
        });

        add(panel);
    }

    private ImageIcon resizeImage(String imagePath, int width, int height) {
        try {
            ImageIcon originalIcon = new ImageIcon(imagePath);
            Image image = originalIcon.getImage();
            Image resizedImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(resizedImage);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getLoggedInUsername() {
        return loggedInUsername; // Return the logged-in username
    }
}