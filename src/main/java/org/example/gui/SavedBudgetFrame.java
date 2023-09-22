package org.example.gui;

import javax.swing.*;
import java.util.List;

public class SavedBudgetFrame extends JFrame {
    private JList<String> savedBudgetsList;
    private DefaultListModel<String> savedBudgetsListModel;
    private List<String> savedBudgetNames;

    public SavedBudgetFrame() {

        setTitle("Saved Budgets");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);

        savedBudgetsListModel = new DefaultListModel<>();
        savedBudgetsList = new JList<>(savedBudgetsListModel);

        for (String budgetName : savedBudgetNames) {
            savedBudgetsListModel.addElement(budgetName);
        }

        add(new JScrollPane(savedBudgetsList));
    }
}
