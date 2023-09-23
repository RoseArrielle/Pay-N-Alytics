package org.example.gui;

import org.example.dao.UsersDAO;
import org.example.model.Users;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterFrame extends JFrame {
    private static boolean isRegistered = false;
    private static String registeredUsername;

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton backButton;
    private JButton registerButton;
    private JLabel imageLabel;

    public RegisterFrame() {
        setTitle("Registration");
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

        ImageIcon imageIcon = resizeImage("src/main/resources/images/register.png", 500, 250);
        imageLabel = new JLabel(imageIcon);
        panel.add(imageLabel, gbc);

        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(2);
        panel.add(usernameLabel, gbc);
        gbc.gridy++;
        panel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(2);
        gbc.gridy++;
        panel.add(passwordLabel, gbc);
        gbc.gridy++;
        panel.add(passwordField, gbc);

        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordField = new JPasswordField(2);
        gbc.gridy++;
        panel.add(confirmPasswordLabel, gbc);
        gbc.gridy++;
        panel.add(confirmPasswordField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        backButton = new JButton("Back");
        buttonPanel.add(backButton);
        registerButton = new JButton("Register");
        buttonPanel.add(registerButton);
        gbc.gridy++;
        panel.add(buttonPanel, gbc);

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                MainFrame mainFrame = new MainFrame();
                mainFrame.setVisible(true);
            }
        });

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                char[] passwordChars = passwordField.getPassword();
                char[] confirmPasswordChars = confirmPasswordField.getPassword();

                if (username.isEmpty() || passwordChars.length == 0 || confirmPasswordChars.length == 0) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields.", "Registration Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String password = new String(passwordChars);
                String confirmPassword = new String(confirmPasswordChars);

                if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(null, "Passwords do not match.", "Registration Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                UsersDAO userDAO = new UsersDAO();
                if (userDAO.doesUsernameExist(username)) {
                    JOptionPane.showMessageDialog(null, "Username is already taken.", "Registration Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Users newUser = new Users();
                newUser.setUsername(username);
                newUser.setPassword(password);

                int userId = userDAO.addUser(newUser);

                if (userId != -1) {
                    JOptionPane.showMessageDialog(null, "Registration successful!", "Registration Success", JOptionPane.INFORMATION_MESSAGE);
                    isRegistered = true;
                    registeredUsername = username;
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
        return registeredUsername;
    }

}