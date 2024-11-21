package code;


import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        // إنشاء خيط منفصل لتشغيل الكود الرئيسي
        
        Thread mainThread = new Thread(() -> {
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
            
            System.out.println("current budget now is "+userBudget.getCurrentBudget());     
            try {
                userBudget.savDailyExpensesToFile("savedata.txt");
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            // Loop for each day of the month (1 to 30)
            for (int day = 1; day <= 30; day++) {
                System.out.println("\nDay " + day + ":");
 
                while (true) {
                    try {
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

                     new  CheckBudgetThread(userBudget.getCurrentBudget()).CheckBudget();
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
                }
            }

            // Close the scanner
            scanner.close();
        });

        // تسمية الخيط الرئيسي الجديد
        mainThread.setName("MainLogicThread");

        // بدء تشغيل الخيط
        mainThread.start();

        // التأكيد أن الخيط يعمل بشكل منفصل
        System.out.println("Main thread has started: " + mainThread.getName());
    }
}







