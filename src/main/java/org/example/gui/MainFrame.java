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

        ImageIcon imageIcon = resizeImage("src/main/resources/images/home.png", 400,400);
        JLabel imageLabel = new JLabel(imageIcon);

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
                dispose();
                RegisterFrame registrationFrame = new RegisterFrame();
                registrationFrame.setVisible(true);
            }
        });

        continueAsGuestButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                BudgetFrame budgetFrame = new BudgetFrame();
                budgetFrame.setVisible(true);
            }
        });

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.add(buttonPanel);

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
