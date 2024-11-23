/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mybudgetapp;

/**
 *
 * @author reemajez
 */

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;


import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

public class BudgetServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8800)) {
            System.out.println("Server is running... Waiting for connections.");
            while (true) {
                try (Socket socket = serverSocket.accept();
                     ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {

                    Map<String, Double> receivedExpenses = (Map<String, Double>) ois.readObject();
                    System.out.println("Report received from client:");
                    System.out.println(receivedExpenses);

                } catch (Exception e) {
                    System.err.println("Error processing client request: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


