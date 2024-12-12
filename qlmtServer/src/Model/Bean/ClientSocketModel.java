/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Bean;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

/**
 *
 * @author Windows
 */
public class ClientSocketModel implements Runnable{
    private Socket socket;
    private String username;
    private DataInputStream in;
    private DataOutputStream out;
    boolean isLogin;
    boolean continueReading;

    // Các phương thức khác

    public boolean isContinueReading() {
        return continueReading;
    }

    public void setContinueReading(boolean continueReading) {
        this.continueReading = continueReading;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }
    
    public ClientSocketModel(Socket soc, String user) {
        try {
            this.socket = soc;
            this.username = user;
            this.isLogin = false;
            this.continueReading = true;
            socket.setReceiveBufferSize(16384);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (SocketException ex) {
            System.err.println(ex.getMessage());
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    public void sendMessage(String message) {
        try {
            out.writeUTF(message);
            out.flush();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public String receiveMessage() throws IOException {
        return in.readUTF();
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    
    
    public void closeEverything() {
        try {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
    /**
     *
     */
    @Override
    public void run() {
        try {
            while (socket.isConnected()) {
                String message = in.readUTF();
                System.out.println(message);
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        } finally {
            closeEverything();
        }
    }
}
