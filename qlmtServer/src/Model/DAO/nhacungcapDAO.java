/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import Model.Bean.DbConnect;
import java.sql.Statement;

import Model.Bean.NhaCungCap;

/**
 *
 * @author PC
 */
public class nhacungcapDAO {

    NhaCungCap nhacungcap = new NhaCungCap();

    public List<String> getAllMaNhaCungCap() {
        List<String> maNhaCungCapList = new ArrayList<>();
        Connection con = DbConnect.openConnection();

        try {
            PreparedStatement pstmt = con.prepareStatement("SELECT maNhaCungCap FROM nhacungcap");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                maNhaCungCapList.add(rs.getString("maNhaCungCap"));
            }

        } catch (SQLException ex) {
            System.err.println("Lỗi: " + ex.getMessage());
        } finally {
            DbConnect.closeConnection(con);
        }

        return maNhaCungCapList;
    }

    public List<NhaCungCap> listNhaCungCap() {
        List<NhaCungCap> list = new ArrayList<>();
        Connection con = DbConnect.openConnection();
        try {
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM nhacungcap");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                NhaCungCap ncc = new NhaCungCap(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
                list.add(ncc);
            }
        } catch (SQLException ex) {
            Logger.getLogger(nhacungcapDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public Boolean Add(String maNhaCungCap, String tenNhaCungCap, String sdt, String diachi) {
        Connection con = DbConnect.openConnection();
        try {
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO nhacungcap(maNhaCungCap, tenNhaCungCap, sdt, diachi) VALUES (?,?,?,?)");
            pstmt.setString(1, maNhaCungCap);
            pstmt.setString(2, tenNhaCungCap);
            pstmt.setString(3, sdt);
            pstmt.setString(4, diachi);
            int i = pstmt.executeUpdate();
            if (i > 0) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(nhacungcapDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public Boolean Update(String maNhaCungCap, String tenNhaCungCap, String sdt, String diachi) {
        Connection con = DbConnect.openConnection();
        try {
            PreparedStatement pstmt = con.prepareStatement("UPDATE nhacungcap SET maNhaCungCap=?, tenNhaCungCap=?, sdt=?, diachi=? WHERE maNhaCungCap=?");
            pstmt.setString(1, maNhaCungCap);
            pstmt.setString(2, tenNhaCungCap);
            pstmt.setString(3, sdt);
            pstmt.setString(4, diachi);
            pstmt.setString(5, maNhaCungCap);
            int i = pstmt.executeUpdate();
            if (i > 0) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(accountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public Boolean Delete(String maNhaCungCap) {
        Connection con = DbConnect.openConnection();
        try {
            PreparedStatement pstmt = con.prepareStatement("Delete from nhacungcap where maNhaCungCap=?");
            pstmt.setString(1, maNhaCungCap);
            int i = pstmt.executeUpdate();
            if (i > 0) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(nhacungcapDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public List<String> getMaNhaCungCap() {
        List<String> maNhaCungCapList = new ArrayList<>();
        Connection con = DbConnect.openConnection();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT maNhaCungCap FROM nhacungcap");
            while (rs.next()) {
                maNhaCungCapList.add(rs.getString("maNhaCungCap"));
            }
        } catch (SQLException ex) {
            System.err.println("Lỗi: " + ex.getMessage());
        } finally {
            DbConnect.closeConnection(con);
        }
        return maNhaCungCapList;
    }

    public NhaCungCap selectById(String t) {
        NhaCungCap ncc = null;
        try {
            Connection con = DbConnect.openConnection();
            String sql = "SELECT * FROM nhacungcap WHERE maNhaCungCap=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String maNhaCungCap = rs.getString("maNhaCungCap");
                String tenNhaCungCap = rs.getString("tenNhaCungCap");
                String sdt = rs.getString("sdt");
                String diachi = rs.getString("diachi");

                ncc = new NhaCungCap(maNhaCungCap, tenNhaCungCap, sdt, diachi);
            }
            DbConnect.closeConnection(con);
        } catch (SQLException e) {
            // TODO: handle exception    
            System.err.println("Lỗi: " + e.getMessage());
        }
        return ncc;
    }
}
