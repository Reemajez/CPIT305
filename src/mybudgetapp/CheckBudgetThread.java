/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mybudgetapp;

/**
 *
 * @author reemajez
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class CheckBudgetThread implements Runnable {
    private double budgetToCheck;

    public CheckBudgetThread(double budgetToCheck) {
        this.budgetToCheck = budgetToCheck; // Pass a snapshot of the current budget
    }

    @Override
    public void run() {
        System.out.println("Thread [" + Thread.currentThread().getName() + "] started at " + java.time.LocalTime.now());

       
            System.out.println("Budget check: budgetToCheck = " + budgetToCheck + ", triggering warning...");
            JOptionPane.showMessageDialog(
                    null,
                    "Warning: You have less than 10 remaining in your budget!\nYour current budget is: " + budgetToCheck,
                    "Budget Warning",
                    JOptionPane.WARNING_MESSAGE
            );
      

        System.out.println("Thread [" + Thread.currentThread().getName() + "] finished at " + java.time.LocalTime.now());
    }
}



