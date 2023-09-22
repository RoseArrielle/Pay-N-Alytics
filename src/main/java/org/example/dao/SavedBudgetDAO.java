package org.example.dao;

import org.example.config.DatabaseConfig;
import org.example.model.SavedBudget;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SavedBudgetDAO {
    private Connection connection;

    public SavedBudgetDAO() {
        connection = DatabaseConfig.getConnection();
    }

    public boolean addSavedBudget(SavedBudget savedBudget) {
        try {
            String sql = "INSERT INTO saved_budgets (user_id, budget_name, budget_data) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, savedBudget.getUser_Id());
            preparedStatement.setString(2, savedBudget.getBudget_Name());
            preparedStatement.setString(3, savedBudget.getBudget_Data());
            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<SavedBudget> getSavedBudgetsByUserId(int userId) {
        List<SavedBudget> savedBudgets = new ArrayList<>();
        try {
            String sql = "SELECT * FROM saved_budgets WHERE user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                SavedBudget savedBudget = new SavedBudget();
                savedBudget.setId(resultSet.getInt("id"));
                savedBudget.setUser_Id(resultSet.getInt("user_id"));
                savedBudget.setBudget_Name(resultSet.getString("budget_name"));
                savedBudget.setBudget_Data(resultSet.getString("budget_data"));
                savedBudgets.add(savedBudget);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return savedBudgets;
    }

    public boolean deleteSavedBudget(int savedBudgetId) {
        try {
            String sql = "DELETE FROM saved_budgets WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, savedBudgetId);
            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateSavedBudget(SavedBudget savedBudget) {
        try {
            String sql = "UPDATE saved_budgets SET budget_name = ?, budget_data = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, savedBudget.getBudget_Name());
            preparedStatement.setString(2, savedBudget.getBudget_Data());
            preparedStatement.setInt(3, savedBudget.getId());
            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
