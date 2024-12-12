/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.DAO;

import java.sql.PreparedStatement;
import Model.Bean.Account;
import Model.Bean.DbConnect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PC
 */
public class accountDAO {

    Account account = new Account();

    public boolean login(String username, String password) {
        try (Connection con = DbConnect.openConnection()) {
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setString(1, username);
                pstmt.setString(2, password);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        account.setidaccount(rs.getString("idaccount"));
                        account.setusername(rs.getString("username"));
                        account.setpassword(rs.getString("password"));
                        account.setname(rs.getString("name"));
                        return true;
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(accountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public List<Account> listAccount() {
        List<Account> list = new ArrayList<>();
        Connection con = DbConnect.openConnection();
        try {
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM account");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Account acc = new Account(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
                list.add(acc);
            }
        } catch (SQLException ex) {
            Logger.getLogger(accountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public Boolean Add(String idaccount, String username, String password, String name) {
        Connection con = DbConnect.openConnection();
        try {
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO account(idaccount, username, password, name) VALUES (?,?,?,?)");
            pstmt.setString(1, idaccount);
            pstmt.setString(2, username);
            pstmt.setString(3, password);
            pstmt.setString(4, name);
            int i = pstmt.executeUpdate();
            if (i > 0) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(accountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public Boolean Update(String idaccount, String username, String password, String name) {
        Connection con = DbConnect.openConnection();
        try {
            PreparedStatement pstmt = con.prepareStatement("UPDATE account SET username=?, password=?,name=? WHERE idaccount=?");
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, name);
            pstmt.setString(4, idaccount);
            int i = pstmt.executeUpdate();
            if (i > 0) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(accountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public Boolean Delete(String idaccount) {
        Connection con = DbConnect.openConnection();
        try {
            PreparedStatement pstmt = con.prepareStatement("Delete from account where idaccount=?");
            pstmt.setString(1, idaccount);
            int i = pstmt.executeUpdate();
            if (i > 0) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(accountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean changePassword(String username, String pw) {
        Connection con = DbConnect.openConnection();
        try {
            PreparedStatement pstmt = con.prepareStatement("UPDATE account SET password=? WHERE username=?");
            pstmt.setString(1, pw);
            pstmt.setString(2, username);
            int i = pstmt.executeUpdate();
            if (i > 0) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(accountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean changeName(String username, String name) {
        Connection con = DbConnect.openConnection();
        try {
            PreparedStatement pstmt = con.prepareStatement("UPDATE account SET name=? WHERE username=?");
            pstmt.setString(1, name);
            pstmt.setString(2, username);
            int i = pstmt.executeUpdate();
            if (i > 0) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(accountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public Account getAccountByUsername(String user) {
        Account acc = null;
        try {
            Connection con = DbConnect.openConnection();
            String sql = "SELECT * FROM account WHERE username=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, user);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String idaccount = rs.getString("idaccount");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String name = rs.getString("name");

                acc = new Account(idaccount, username, password, name);
            }
            DbConnect.closeConnection(con);
        } catch (SQLException e) {
            // TODO: handle exception
            System.err.println("Lỗi: " + e.getMessage());
        }
        return acc;
    }
    
    public Account getLastAccount() {
        Account acc = null;
        try {
            Connection con = DbConnect.openConnection();
            String sql = "SELECT TOP 1 * FROM account ORDER BY idaccount DESC";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String idaccount = rs.getString("idaccount");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String name = rs.getString("name");

                acc = new Account(idaccount, username, password, name);
            }
            DbConnect.closeConnection(con);
        } catch (SQLException e) {
            // TODO: handle exception
            System.err.println("Lỗi: " + e.getMessage());
        }
        return acc;
    }
    
    public boolean checkUsername(String user) {
        boolean isUsernameExist = false;
        try {
            Connection con = DbConnect.openConnection();
            String sql = "SELECT username FROM account WHERE username=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, user);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                isUsernameExist = true;
            }
            DbConnect.closeConnection(con);
        } catch (SQLException e) {
            // TODO: handle exception
            System.err.println("Lỗi: " + e.getMessage());
        }
        return isUsernameExist;
    }
    
    public boolean checkidAccount(String id) {
        boolean isIDAccountExist = false;
        try {
            Connection con = DbConnect.openConnection();
            String sql = "SELECT idaccount FROM account WHERE idaccount=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                isIDAccountExist = true;
            }
            DbConnect.closeConnection(con);
        } catch (SQLException e) {
            // TODO: handle exception
            System.err.println("Lỗi: " + e.getMessage());
        }
        return isIDAccountExist;
    }
}
