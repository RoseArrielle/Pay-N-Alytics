package org.example.gui;

import org.example.dao.UsersDAO;
import org.example.model.Users;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ChangePasswordFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField currentPasswordField;
    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;
    private JButton changePasswordButton;

    private UsersDAO usersDAO; // You need to have a UsersDAO for database operations

    public ChangePasswordFrame() {
        usersDAO = new UsersDAO();

        setTitle("Change Password");
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

        ImageIcon imageIcon = new ImageIcon("src/main/resources/images/change-pass.png"); // Replace with the path to your image
        Image image = imageIcon.getImage();
        Image resizedImage = image.getScaledInstance(450, 225, Image.SCALE_SMOOTH); // Set desired width and height
        ImageIcon resizedImageIcon = new ImageIcon(resizedImage);
        JLabel imageLabel = new JLabel(resizedImageIcon);
        gbc.insets = new Insets(10, 10, 5, 10);
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(imageLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(30); // Adjust the field size as needed
        panel.add(usernameLabel, gbc);
        gbc.gridx++;
        panel.add(usernameField, gbc);

        JLabel currentPasswordLabel = new JLabel("Current Password:");
        currentPasswordField = new JPasswordField(30); // Adjust the field size as needed
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(currentPasswordLabel, gbc);
        gbc.gridx++;
        panel.add(currentPasswordField, gbc);

        JLabel newPasswordLabel = new JLabel("New Password:");
        newPasswordField = new JPasswordField(30); // Adjust the field size as needed
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(newPasswordLabel, gbc);
        gbc.gridx++;
        panel.add(newPasswordField, gbc);

        JLabel confirmPasswordLabel = new JLabel("Confirm New Password:");
        confirmPasswordField = new JPasswordField(30); // Adjust the field size as needed
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(confirmPasswordLabel, gbc);
        gbc.gridx++;
        panel.add(confirmPasswordField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        changePasswordButton = new JButton("Change Password");
        panel.add(changePasswordButton, gbc);

        Dimension buttonSize = new Dimension(150, 40);
        changePasswordButton.setPreferredSize(buttonSize);

        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement logic to change the password
                String username = usernameField.getText();
                char[] currentPasswordChars = currentPasswordField.getPassword();
                String currentPassword = new String(currentPasswordChars);
                char[] newPasswordChars = newPasswordField.getPassword();
                String newPassword = new String(newPasswordChars);
                char[] confirmPasswordChars = confirmPasswordField.getPassword();
                String confirmPassword = new String(confirmPasswordChars);

                Users user = usersDAO.getUserByUsername(username);
                if (user != null) {
                    String storedPassword = user.getPassword();

                    if (currentPassword.equals(storedPassword)) {
                        // Current password is correct, proceed to change the password
                        if (newPassword.equals(confirmPassword)) {
                            if (usersDAO.updateUserPassword(username, newPassword)) {
                                JOptionPane.showMessageDialog(ChangePasswordFrame.this, "Password changed successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                                dispose();
                                BudgetFrame budgetFrame = new BudgetFrame();
                                budgetFrame.setVisible(true);
                            } else {
                                JOptionPane.showMessageDialog(ChangePasswordFrame.this, "Password change failed", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(ChangePasswordFrame.this, "New passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(ChangePasswordFrame.this, "Current password is incorrect", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(ChangePasswordFrame.this, "User not found", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(panel);
    }


}