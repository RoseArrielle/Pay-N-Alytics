package org.example.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExportFrame extends JFrame {
    private JButton exportPdfButton;
    private JButton exportJpgButton;

    public ExportFrame() {
        setTitle("Export Budget");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));

        exportPdfButton = new JButton("Export as PDF");
        exportJpgButton = new JButton("Export as JPG");

        panel.add(exportPdfButton);
        panel.add(exportJpgButton);

        exportPdfButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Add code to handle PDF export
            }
        });

        exportJpgButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Add code to handle JPG export
            }
        });

        add(panel);
    }
}
