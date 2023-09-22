package org.example.gui;

import org.example.dao.UsersDAO;
import org.example.model.Users;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterFrame extends JFrame {
    private static boolean isRegistered = false;
    private static String registeredUsername; // Add this variable

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField; // Add the Confirm Password field
    private JButton backButton; // Move the back button before the register button
    private JButton registerButton;
    private JLabel imageLabel; // Add the label for the image

    public RegisterFrame() {
        setTitle("Registration");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600); // Adjust the frame size to accommodate the new field
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;

        // Load and resize the image to fit within the frame
        ImageIcon imageIcon = resizeImage("src/main/resources/images/register.png", 500, 250); // Adjust width and height as needed
        imageLabel = new JLabel(imageIcon);
        panel.add(imageLabel, gbc);

        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(2); // Set the preferred width of the username field
        gbc.insets = new Insets(5, 5, 5, 5); // Add space around components
        panel.add(usernameLabel, gbc);
        gbc.gridy++;
        panel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(2); // Set the preferred width of the password field
        gbc.gridy++;
        panel.add(passwordLabel, gbc);
        gbc.gridy++;
        panel.add(passwordField, gbc);

        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordField = new JPasswordField(2); // Set the preferred width of the Confirm Password field
        gbc.gridy++;
        panel.add(confirmPasswordLabel, gbc);
        gbc.gridy++;
        panel.add(confirmPasswordField, gbc);

        // Create a panel for the buttons and add them next to each other
        JPanel buttonPanel = new JPanel(new FlowLayout());
        backButton = new JButton("Back"); // Create the back button
        buttonPanel.add(backButton);
        registerButton = new JButton("Register");
        buttonPanel.add(registerButton);
        gbc.gridy++;
        panel.add(buttonPanel, gbc);

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Add code to go back to the main frame
                dispose(); // Close the registration frame
                MainFrame mainFrame = new MainFrame();
                mainFrame.setVisible(true);
            }
        });

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Validate the input fields
                String username = usernameField.getText();
                char[] passwordChars = passwordField.getPassword();
                char[] confirmPasswordChars = confirmPasswordField.getPassword();

                // Check if the fields are empty
                if (username.isEmpty() || passwordChars.length == 0 || confirmPasswordChars.length == 0) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields.", "Registration Error", JOptionPane.ERROR_MESSAGE);
                    return; // Exit the registration process
                }

                // Convert char arrays to strings for comparison
                String password = new String(passwordChars);
                String confirmPassword = new String(confirmPasswordChars);

                // Check if the password and confirm password match
                if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(null, "Passwords do not match.", "Registration Error", JOptionPane.ERROR_MESSAGE);
                    return; // Exit the registration process
                }

                // Check if the username already exists in the database
                UsersDAO userDAO = new UsersDAO();
                if (userDAO.doesUsernameExist(username)) {
                    JOptionPane.showMessageDialog(null, "Username is already taken.", "Registration Error", JOptionPane.ERROR_MESSAGE);
                    return; // Exit the registration process
                }

                // If all checks pass, insert the user into the database
                Users newUser = new Users();
                newUser.setUsername(username);
                newUser.setPassword(password);

                if (userDAO.addUser(newUser)) {
                    JOptionPane.showMessageDialog(null, "Registration successful!", "Registration Success", JOptionPane.INFORMATION_MESSAGE);
                    isRegistered = true;
                    registeredUsername = username; // Store the registered username
                    dispose();
                    BudgetFrame budgetFrame = new BudgetFrame();
                    budgetFrame.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Registration failed. Please try again later.", "Registration Error", JOptionPane.ERROR_MESSAGE);
                }
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
    public static boolean isRegistered() {
        return isRegistered;
    }
    public static String getRegisteredUsername() {
        return registeredUsername; // Return the registered username
    }

}