/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package mybudgetapp;

import code.Main;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static mybudgetapp.BudgetPreparation.userBudget;

/**
 *
 * @author Jehan Meqdad
 */
public class report extends javax.swing.JFrame {
    
public String getExpenseReport() throws IOException {
    StringBuilder report = new StringBuilder();
    String ConnectionURL = "jdbc:mysql://localhost:3306/BudgetAppDatabase";
    String user = "root";
    String password = "Mm@70740";
    String query = "SELECT category, SUM(amount) AS total_amount FROM expenses GROUP BY category;";

    try (Connection con = DriverManager.getConnection(ConnectionURL, user, password);
         Statement stmt = con.createStatement();
         ResultSet rs = stmt.executeQuery(query)) {

        while (rs.next()) {
            String category = rs.getString("category");
            double totalAmount = rs.getDouble("total_amount");
            report.append("Category: ").append(category).append(", Total Amount: ").append(totalAmount).append("\n");
        }

        // Save to file only if userBudget is not null
        if (BudgetPreparation.userBudget != null) {
            BudgetPreparation.userBudget.savDailyExpensesToFile("report2.txt");
        } else {
            report.append("\nUserBudget is not initialized. Skipping file save.");
        }

    } catch (SQLException e) {
        report.append("Error fetching data: ").append(e.getMessage());
    }

    return report.toString();
}


// Helper method to upload the report to the server


// Method to fetch expenses from the database, display them, and send them to the server
private void sendReportToServer(String serverAddress, int port) {
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

        // Construct the dialog message
        StringBuilder dialogMessage = new StringBuilder("Expenses Report:\n");
        dialogMessage.append("sent sucsessfuly to the server\n");

        // Process the result set and populate the map
        while (rs.next()) {
            String category = rs.getString("category");
            double totalAmount = rs.getDouble("total_amount");
            expenses.put(category, totalAmount);
            dialogMessage.append("Category: ").append(category).append(", Total Amount: ").append(totalAmount).append("\n");
        }

        System.out.println("Expenses fetched from database successfully.");

        // Send the map to the server
        oos.writeObject(expenses);
        System.out.println("Report sent to server.");

        // Show the dialog with the report content
        JOptionPane.showMessageDialog(this, dialogMessage.toString(), "Expenses Report", JOptionPane.INFORMATION_MESSAGE);

    } catch (SQLException e) {
        System.err.println("Database error: " + e.getMessage());
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    } catch (IOException e) {
        System.err.println("Failed to send report to server: " + e.getMessage());
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Failed to send report to server: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}


private String getContentFromServer() {
    String serverAddress = "127.0.0.1"; // Replace with your server address
    int port = 8800; // Replace with your server port
    StringBuilder serverContent = new StringBuilder();

    try (Socket socket = new Socket(serverAddress, port);
         ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {

        // Receive the content from the server
        serverContent.append((String) ois.readObject());

    } catch (IOException | ClassNotFoundException e) {
        serverContent.append("Failed to fetch data from server: ").append(e.getMessage());
    }

    return serverContent.toString();
}
    /**
     * Creates new form Frame1
     */
    public report() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(249, 237, 220));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(202, 120, 11));
        jLabel1.setText("Expense Report");

        jButton2.setBackground(new java.awt.Color(202, 120, 11));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("get Report");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(202, 120, 11));
        jButton3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Back");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(202, 120, 11));
        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("save in server ");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 526, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(125, 125, 125)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 166, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(129, 129, 129))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 169, Short.MAX_VALUE)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
            String report;
    try {
        
        report = getExpenseReport();
        JOptionPane.showMessageDialog(this, report, "Expense Report", JOptionPane.INFORMATION_MESSAGE);

    } catch (IOException ex) {
        Logger.getLogger(report.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        Homepage homepage = new Homepage();
        homepage.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
    try {
        String report = getExpenseReport();
    } catch (IOException ex) {
        Logger.getLogger(report.class.getName()).log(Level.SEVERE, null, ex);
    }
         
 String serverAddress = "127.0.0.1"; // Replace with your server address
    int port = 8800; // Replace with your server port

    // Call the sendReportToServer method
    sendReportToServer(serverAddress, port);
    // Upload the report to the server
    
    }//GEN-LAST:event_jButton4ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(report.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(report.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(report.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(report.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new report().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
