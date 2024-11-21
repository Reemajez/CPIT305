/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package code;

/**
 *
 * @author reemajez
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class CheckBudgetThread {
    static double currentBudget=0;

    public CheckBudgetThread(double currentBudget) {
        this.currentBudget = currentBudget;
    }
   
    
    public  void CheckBudget() {
        // قيمة الميزانية (يمكنك تعديلها أو أخذها من المستخدم)
        

        // تشغيل خيط للتحقق من الميزانية
        new Thread(() -> {
            if (currentBudget <= 10) {
                JOptionPane.showMessageDialog(
                        null,
                        "Warning: You have less than 10 remaining in your budget!\n your current Budget is"+currentBudget,
                        "Budget Warning",
                        
                        JOptionPane.WARNING_MESSAGE
                );
            }
        }, "CheckBudgetThread").start();
    }
}


