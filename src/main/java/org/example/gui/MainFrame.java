package org.example.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainFrame extends JFrame {
    private JButton loginButton;
    private JButton registerButton;
    private JButton continueAsGuestButton;

    public MainFrame() {
        setTitle("Budget App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        // Create a JLabel for the image
        ImageIcon imageIcon = resizeImage("src/main/resources/images/home.png", 400,400); // Replace with the actual image path
        JLabel imageLabel = new JLabel(imageIcon);

        // Create a JPanel for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1));

        loginButton = new JButton("Login");
        registerButton = new JButton("Register");
        continueAsGuestButton = new JButton("Continue as Guest");

        Dimension buttonSize = new Dimension(150, 40);
        loginButton.setPreferredSize(buttonSize);
        registerButton.setPreferredSize(buttonSize);
        continueAsGuestButton.setPreferredSize(buttonSize);

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        buttonPanel.add(continueAsGuestButton);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current frame
                LoginFrame loginFrame = new LoginFrame();
                loginFrame.setVisible(true);
            }
        });

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Add code to open the registration frame
                dispose(); // Close the current frame
                RegisterFrame registrationFrame = new RegisterFrame();
                registrationFrame.setVisible(true);
            }
        });

        continueAsGuestButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Add code to continue as a guest user
                dispose();
                BudgetFrame budgetFrame = new BudgetFrame();
                budgetFrame.setVisible(true);
            }
        });

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.add(buttonPanel);

        // Add the image label to the top and button panel to the center of the main panel
        panel.add(imageLabel, BorderLayout.NORTH);
        panel.add(centerPanel, BorderLayout.CENTER);

        add(panel);
    }

    private ImageIcon resizeImage(String imagePath, int width, int height) {
        try {
            BufferedImage originalImage = ImageIO.read(new File(imagePath));
            Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
