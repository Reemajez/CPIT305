package code;
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */



import java.util.Scanner;

public class AddExpenseTask {
    public static void main(String[] args) {
        // Create a Scanner object to read user input
        Scanner scanner = new Scanner(System.in);

        // Input for monthly budget
        System.out.print("Enter your monthly budget: ");
        double monthlyBudget = scanner.nextDouble();

        // Input for saving percentage
        System.out.print("Enter your saving percentage (%): ");
        double savingPercentage = scanner.nextDouble();

        // Create an instance of UserBudget class
        UserBudget userBudget = new UserBudget(monthlyBudget, savingPercentage);

        // Loop for each day of the month (1 to 30)
        for (int day = 1; day <= 30; day++) {
            System.out.println("\nDay " + day + ":");

            while (true) {
                try {
                    System.out.println("thread : "+Thread.currentThread());
                    System.out.println("Enter the category (Food, Transport, Entertainment) or 'exit' to quit:");
                    String category = scanner.next();

                    if (category.equalsIgnoreCase("exit")) {
                        break;
                    }

                    System.out.print("Enter the amount: ");
                    double amount = scanner.nextDouble();

                    // Add daily expense directly
                    userBudget.addDailyExpense(category, amount);
                    // Add expense to the database (assuming you have DatabaseHelper class)
                    DatabaseHelper.addExpense(category, amount, day);

                    // Create a thread for checking the remaining budget and displaying the warning if necessary
                    Thread checkBudgetThread = new Thread(() -> {
                        double remainingBudget = userBudget.getRemainingBudget();
                        if (remainingBudget <= 10) {
                            System.out.println("thread : "+Thread.currentThread());
                            System.out.println("Warning: You have less then 10 remaining in your budget!");
                        }
                    });

                    // Start the thread for checking budget
                    checkBudgetThread.start();

                    // Wait for the thread to finish
                    checkBudgetThread.join();

                    // Print the updated expenses
                    userBudget.printExpenses();

                    // Save daily expenses to file
                    userBudget.saveDailyExpensesToFile("daily.txt");

                    // Send the report to the server
                    userBudget.sendReportToServer("127.0.0.1", 8800);

                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                    scanner.nextLine(); // Clear buffer
                }

//                // Check if the user wants to view the remaining budget at any point
//                System.out.println("Enter 'r' to view remaining budget, or continue entering expenses.");
//                String input = scanner.next();
//                if (input.equalsIgnoreCase("r")) {
//                    System.out.println("Remaining budget: " + userBudget.getRemainingBudget());
//                }
            }
        }

        // Close the scanner
        scanner.close();
    }
}

class UserBudget {
    private double monthlyBudget;
    private double totalExpenses;
    private double savingPercentage;

    public UserBudget(double monthlyBudget, double savingPercentage) {
        this.monthlyBudget = monthlyBudget;
        this.savingPercentage = savingPercentage;
        this.totalExpenses = 0.0;
    }

    public synchronized void addDailyExpense(String category, double amount) {
        totalExpenses += amount;
    }

    public double getMonthlyBudget() {
        return monthlyBudget;
    }

    public double getRemainingBudget() {
        return monthlyBudget - totalExpenses;
    }

    public void printExpenses() {
        System.out.println("Total Expenses: " + totalExpenses);
    }

    public void saveDailyExpensesToFile(String filename) {
        // Code to save expenses to file (not implemented)
    }

    public void sendReportToServer(String ipAddress, int port) {
        // Code to send report to server (not implemented)
    }
}

class DatabaseHelper {
    public static void addExpense(String category, double amount, int day) {
        // Code to add expense to database (not implemented)
    }
}

