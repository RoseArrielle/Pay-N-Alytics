package org.example.dao;

import org.example.config.DatabaseConfig;
import org.example.model.Budget;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BudgetDAO {
    private Connection connection;

    public BudgetDAO() {
        connection = DatabaseConfig.getConnection();
    }

    public boolean addBudget(Budget budget) {
        try {
            String sql = "INSERT INTO budgets (user_id, budget_name) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, budget.getUser_id());
            preparedStatement.setString(2, budget.getBudget_name());
            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Budget> getBudgetsByUserId(int userId) {
        List<Budget> budgets = new ArrayList<>();
        try {
            String sql = "SELECT * FROM budgets WHERE user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Budget budget = new Budget();
                budget.setId(resultSet.getInt("id"));
                budget.setUser_id(resultSet.getInt("user_id"));
                budget.setBudget_name(resultSet.getString("budget_name"));
                budgets.add(budget);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return budgets;
    }

    public boolean deleteBudget(int budgetId) {
        try {
            String sql = "DELETE FROM budgets WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, budgetId);
            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int createNewBudget(String budgetName) {
        try {
            // Insert a new budget into the database and retrieve its generated ID
            String sql = "INSERT INTO budgets (budget_name) VALUES (?) RETURNING id";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, budgetName);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            } else {
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }



}
