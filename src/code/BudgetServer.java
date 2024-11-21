/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package code;

/**
 *
 * @author reemajez
 */

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

public class BudgetServer {

    public void receiveReportFromClient(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is waiting for a connection...");
            try (Socket socket = serverSocket.accept();
                 ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
                
                // Receive the object (daily expenses) sent by the client
                Map<String, Double> receivedExpenses = (Map<String, Double>) ois.readObject();
                System.out.println("Report received from client:");
                for (Map.Entry<String, Double> entry : receivedExpenses.entrySet()) {
                    System.out.println("الفئة: " + entry.getKey() + " | المبلغ: " + entry.getValue());
                }
            } catch (ClassNotFoundException e) {
                System.out.println("Error in deserializing the object: " + e.getMessage());
            }
        } catch (IOException e) {
            System.out.println("Error setting up server: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        BudgetServer server = new BudgetServer();
        server.receiveReportFromClient(8800);  // Listening on port 12345
    }
}

