///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
////donnnneeeeee
package mybudgetapp;

import java.sql.*;

public class DatabaseHelper {
    private Connection connection;

    // Constructor to initialize SQLite database connection
    public DatabaseHelper(String dbName) throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:" + dbName);
    }

    // Static method to create the MySQL database
    public static void createDatabase() {
        Connection con = null;
        try {
            // Set the path for the MySQL connection URL
            String ConnectionURL = "jdbc:mysql://localhost:3306";
            
            // Create connection
            con = DriverManager.getConnection(ConnectionURL, "root", "Mm@70740");

            // Create statement object
            Statement st = con.createStatement();

            // Execute SQL statement to create database
            st.executeUpdate("CREATE DATABASE IF NOT EXISTS BudgetAppDatabase");
            System.out.println("Database 'BudgetAppDatabase' created successfully.");

            // Close the connection
            con.close();
        } catch (SQLException s) {
            System.out.println("SQL statement was not executed: " + s.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to create the 'expenses' table in the MySQL database
    public static void createExpenseTable() throws SQLException {
        String ConnectionURL = "jdbc:mysql://localhost:3306/BudgetAppDatabase";
        String user = "root";
        String password = "Mm@70740";

        try (Connection con = DriverManager.getConnection(ConnectionURL, user, password);
             Statement stmt = con.createStatement()) {

            // SQL statement to create the 'expenses' table
            String createExpenseTable = "CREATE TABLE IF NOT EXISTS expenses (" +
                                        "id INTEGER PRIMARY KEY AUTO_INCREMENT, " +
                                        "category TEXT, " +
                                        "amount REAL, " +
                                        "day INTEGER);";

            stmt.executeUpdate(createExpenseTable);
            System.out.println("Table 'expenses' created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to add an expense record to the 'expenses' table
    public static void addExpense(String category, double amount, int day) {
        String ConnectionURL = "jdbc:mysql://localhost:3306/BudgetAppDatabase";
        String user = "root";
        String password = "Mm@70740";

        String insertExpense = "INSERT INTO expenses (category, amount, day) VALUES (?, ?, ?);";

        try (Connection con = DriverManager.getConnection(ConnectionURL, user, password);
             PreparedStatement pstmt = con.prepareStatement(insertExpense)) {

            pstmt.setString(1, category);
            pstmt.setDouble(2, amount);
            pstmt.setInt(3, day);
            pstmt.executeUpdate();
            System.out.println("Expense added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

