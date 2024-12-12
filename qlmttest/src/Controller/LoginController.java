/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Bean.serialization;
import Model.Bean.Account;
import Model.Bean.ClientSocketModel;
//import View.Admin;
import View.AdminJFrame;
import View.Login;
import View.SignupJFrame;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Windows
 */
public class LoginController {
    private Socket socket;
    private Thread receiveThread;
    private volatile boolean isRunning = true;
    private ClientSocketModel client;
    private Login loginView;
    private SignupJFrame signup;
//    private Admin adminView;
    private serialization sd;

    public LoginController(Socket soc, Login login) {
        sd = new serialization();
        this.loginView = login;
        this.socket = soc;
        client = new ClientSocketModel(socket) {
            @Override
            public void run() {
                try {
                    while (isRunning && socket.isConnected()) {
                        System.out.println("is receiving...");
                        String receivedData = this.receiveMessage();
                        System.out.println("Received data from server: " + receivedData);
                        handleLoginMessage(receivedData);
                        if (!isRunning) {
                            break;
                        }
                    }
                } catch (IOException ex) {
//                    JOptionPane.showMessageDialog(loginView, "Server is closed.");
                    System.err.println("Error reading from server: " + ex.getMessage());
                }
            }

        };
        receiveThread = new Thread(client);
        receiveThread.start();
    }
    
    public void handleLoginMessage(String message) {
        if (message.startsWith("LoginOk")) {
            handleLoginSuccess(message);
        } else if (message.startsWith("AccountisLoggedin")) {
            loginView.setMessage("This account is active on another device.");
        } else if (message.startsWith("LoginFailed")) {
            loginView.setMessage("Incorrect username or password.");
        } else if (message.startsWith("UsernameisExist")) {
            signup.setMessage("Username has already been registered.");
        } else if (message.equals("AddAccountFail")) {
            JOptionPane.showMessageDialog(null, "Error registering a new account.");
        } else if (message.startsWith("AddAccountSuccess")) {
            handleAddAccountSuccess();
        } else {
            JOptionPane.showMessageDialog(loginView, message);
        }
    }

    private void handleLoginSuccess(String message) {
        String[] str = message.split(";");
        JOptionPane.showMessageDialog(loginView, "Logged in successfully");
        try {
            DefaultTableModel modelProduct = (DefaultTableModel) sd.StringtoObject(str[1]);
            DefaultTableModel modelSupplier = (DefaultTableModel) sd.StringtoObject(str[2]);
            DefaultTableModel modelCustomer = (DefaultTableModel) sd.StringtoObject(str[3]);
            Account account = (Account) sd.StringtoObject(str[4]);

            loginView.dispose();
            AdminJFrame ad = new AdminJFrame(socket, modelProduct, modelSupplier, modelCustomer, account);
            ad.setVisible(true);
            stopReceiving();
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    private void handleAddAccountSuccess() {
        signup.dispose();
        JOptionPane.showMessageDialog(loginView, "New account registration successful.");
    }

    public void stopReceiving() {
        isRunning = false;
    }
    
    public void sendMessage(String message) {
        client.sendMessage(message);
    }

    public void setSignup(SignupJFrame signup) {
        this.signup = signup;
    }
}
