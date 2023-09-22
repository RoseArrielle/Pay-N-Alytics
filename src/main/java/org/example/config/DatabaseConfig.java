package org.example.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    private static String url ="jdbc:postgresql://localhost:5433/budget";
    private static String username = "postgres";
    private static String password = "data";
    private static Connection connection = null;
    public static Connection getConnection(){

        if (connection == null){
            try {
                connection = DriverManager.getConnection(url, username, password);
            } catch (SQLException s) {
                s.printStackTrace();
            }
        }
        return connection;
    }

    public static void closeConnection(){
        if (connection != null){
            try {
                connection.close();
            } catch (SQLException s) {
                s.printStackTrace();
            }
        }
    }


}
