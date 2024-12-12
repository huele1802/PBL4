/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author PC
 */
public class DbConnect {

    public static Connection openConnection() {
        Connection con = null;
        String url = "jdbc:sqlserver://localhost:1433;databaseName=qlmttest;encrypt=true;trustServerCertificate=true";
        String username = "sa";
        String password = "123"; // 

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(url, username, password);

//            if (con != null) {
//                System.out.println("Kết nối database thành công");
//            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Lỗi: " + e.getMessage());
        }
        return con;
    }

    public static void closeConnection(Connection c) {
        try {
            if (c != null) {
                c.close();
            }
        } catch (SQLException e) {
            // TODO: handle exception
            System.err.println("Lỗi: " + e.getMessage());
        }
    }
}
