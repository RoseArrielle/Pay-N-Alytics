package org.example.dao;

import org.example.config.DatabaseConfig;
import org.example.model.Bills;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BillsDAO {
    private Connection connection;

    public BillsDAO() {
        connection = DatabaseConfig.getConnection();
    }

    public boolean addBill(Bills bills) {
        try {
            String sql = "INSERT INTO bills (budget_id, name, amount) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, bills.getBudget_Id());
            preparedStatement.setString(2, bills.getName());
            preparedStatement.setDouble(3, bills.getAmount());
            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Bills> getBillsByBudgetId(int budgetId) {
        List<Bills> bill = new ArrayList<>();
        try {
            String sql = "SELECT * FROM bills WHERE budget_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, budgetId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Bills bills = new Bills();
                bills.setId(resultSet.getInt("id"));
                bills.setBudget_Id(resultSet.getInt("budget_id"));
                bills.setName(resultSet.getString("name"));
                bills.setAmount(resultSet.getDouble("amount"));
                bill.add(bills);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bill;
    }

    public boolean deleteBill(int billId) {
        try {
            String sql = "DELETE FROM bills WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, billId);
            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update a bill's information
    public boolean updateBill(Bills bill) {
        try {
            String sql = "UPDATE bills SET name = ?, amount = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, bill.getName());
            preparedStatement.setDouble(2, bill.getAmount());
            preparedStatement.setInt(3, bill.getId());
            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



}
