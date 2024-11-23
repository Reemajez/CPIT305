/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package mybudgetapp;

/**
 *
 * @author reemajez
 */
import java.util.*;

import java.io.*;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;


public class UserBudget {
    private double monthlyBudget;
    private double totalExpenses;
    private double savingPercentage;
//    double monthlyBudget;
//    private double savingPercentage;
    private double currentBudget;
    private Map<String, Double> dailyExpenses;
    private List<Map<String, Double>> monthlyReports;
    
//ok
    public UserBudget(double monthlyBudget, double savingPercentage) {
        this.monthlyBudget = monthlyBudget;
        this.savingPercentage = savingPercentage;
        this.currentBudget = calculateSpendingBudget();
        this.dailyExpenses = new HashMap<>();
        this.monthlyReports = new ArrayList<>();
    }
 public double getmonthlyBudget(){
 return monthlyBudget;
 }
    
//ok
    private double calculateSpendingBudget() {
        return monthlyBudget * ((100 - savingPercentage) / 100);
    }
//we will not use it
    public void resetMonthlyBudget() {
        monthlyReports.add(new HashMap<>(dailyExpenses));
        dailyExpenses.clear();
        currentBudget = calculateSpendingBudget();
    }
    private int getCurrentDay() {
    return java.time.LocalDate.now().getDayOfMonth();
}
//ok
public String addDailyExpense(String category, double amount) throws Exception {
    if (amount > currentBudget) {
        throw new Exception("You have exceeded the available monthly budget for expenses!");
    }
    DatabaseHelper.addExpense(category, amount, getCurrentDay());
    dailyExpenses.put(category, amount);

    currentBudget -= amount;
    return "Expense added successfully. Remaining budget: " + currentBudget;
}


    public double getCurrentBudget() {
        return currentBudget;
    }

    public double getSavingPercentage() {
        return savingPercentage;
    }
    public double getExpense() {
        return 100 - savingPercentage ;
    }
    
//ok
public void saveDailyExpensesToFile(String filename) throws IOException, SQLException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
        for (Map.Entry<String, Double> entry : dailyExpenses.entrySet()) {
            String line = "الفئة: " + entry.getKey() + " | المبلغ: " + entry.getValue();
            writer.write(line);
            writer.newLine();  
        }
    }
}
public void sendReportToServer(String serverAddress, int port) {
    String ConnectionURL = "jdbc:mysql://localhost:3306/BudgetAppDatabase";
    String user = "root";
    String password = "Mm@70740";

    // SQL query to retrieve expenses grouped by category
    String query = "SELECT category, SUM(amount) AS total_amount FROM expenses GROUP BY category;";

    try (Connection con = DriverManager.getConnection(ConnectionURL, user, password);
         Statement stmt = con.createStatement();
         ResultSet rs = stmt.executeQuery(query);
         Socket socket = new Socket(serverAddress, port);
         ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())) {

        // Create a map to store expenses fetched from the database
        Map<String, Double> expenses = new HashMap<>();

        // Process the result set and populate the map
        while (rs.next()) {
            String category = rs.getString("category");
            double totalAmount = rs.getDouble("total_amount");
            expenses.put(category, totalAmount);
        }

        System.out.println("Expenses fetched from database successfully.");

        // Send the map to the server
        oos.writeObject(expenses);
        System.out.println("Report sent to server.");

    } catch (SQLException e) {
        System.out.println("Database error: " + e.getMessage());
        e.printStackTrace();
    } catch (IOException e) {
        System.out.println("Failed to send report to server: " + e.getMessage());
        e.printStackTrace();
    }
}


public static void main(String[] args) {
        try {
            UserBudget userBudget = new UserBudget(1000, 20);  // Example budget and saving percentage
            userBudget.addDailyExpense("Food", 50);
            userBudget.addDailyExpense("Transport", 20);
            
            // Sending the daily expenses to the server
            userBudget.sendReportToServer("localhost", 12345);  // Example server address and port
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
  
  
  
  
   //we will not use it
    public List<Map<String, Double>> getMonthlyReports() {
        return monthlyReports;
    }
   //we will not use it 
 public void saveReportToFile(String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(monthlyReports);
        }
    }
//    public void sendReportToServer(String serverAddress, int port) {
//        try (Socket socket = new Socket(serverAddress, port);
//             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())) {
//            oos.writeObject(monthlyReports);
//            System.out.println("Report sent to server.");
//        } catch (IOException e) {
//            System.out.println("Failed to send report to server: " + e.getMessage());
//        }
//    }

   
   


    // Add expense to the total expenses
    public void addDailyExpense( double amount) {
        this.totalExpenses += amount;
    }

    // Get the monthly budget
    public double getMonthlyBudget() {
        return monthlyBudget;
    }

    // Get the total expenses
    public double getTotalExpenses() {
        return totalExpenses;
    }


public void savDailyExpensesToFile(String filename) throws IOException {
    String ConnectionURL = "jdbc:mysql://localhost:3306/BudgetAppDatabase";
    String user = "root";
    String password = "Mm@70740";

    String query = "SELECT category, amount, day FROM expenses";

    try (Connection con = DriverManager.getConnection(ConnectionURL, user, password);
         Statement stmt = con.createStatement();
         ResultSet rs = stmt.executeQuery(query);
         BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {

        while (rs.next()) {
            String category = rs.getString("category");
            double amount = rs.getDouble("amount");
            int day = rs.getInt("day");

            String line = "الفئة: " + category + " | المبلغ: " + amount + " | اليوم: " + day;
            writer.write(line);
            writer.newLine(); // Add a new line after each entry
        }
        System.out.println("Expenses saved to file: " + filename);
    } catch (SQLException e) {
        System.err.println("Error while fetching data from database: " + e.getMessage());
    }
}

}





