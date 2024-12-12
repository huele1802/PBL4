/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.DAO;

import Model.Bean.DbConnect;
import Model.Bean.KhachHang;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Windows
 */
public class khachhangDAO {
    KhachHang khachhang = new KhachHang();

//    public List<String> getAllMaKhachHang() {
//        List<String> maKhachHangList = new ArrayList<>();
//        Connection con = DbConnect.openConnection();
//
//        try {
//            PreparedStatement pstmt = con.prepareStatement("SELECT maNhaCungCap FROM nhacungcap");
//            ResultSet rs = pstmt.executeQuery();
//
//            while (rs.next()) {
//                maKhachHangList.add(rs.getString("maNhaCungCap"));
//            }
//
//        } catch (SQLException ex) {
//            System.err.println("Lỗi: " + ex.getMessage());
//        } finally {
//            DbConnect.closeConnection(con);
//        }
//
//        return maKhachHangList;
//    }

    public List<KhachHang> listKhachHang() {
        List<KhachHang> list = new ArrayList<>();
        Connection con = DbConnect.openConnection();
        try {
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM khachhang");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                KhachHang ncc = new KhachHang(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
                list.add(ncc);
            }
        } catch (SQLException ex) {
        }
        return list;
    }

    public Boolean Add(String maKhachHang, String tenKhachHang, String sdt, String diachi) {
        Connection con = DbConnect.openConnection();
        try {
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO khachhang(idkhachhang, tenkhachhang, sdt, diachi) VALUES (?,?,?,?)");
            pstmt.setString(1, maKhachHang);
            pstmt.setString(2, tenKhachHang);
            pstmt.setString(3, sdt);
            pstmt.setString(4, diachi);
            int i = pstmt.executeUpdate();
            if (i > 0) {
                return true;
            }
        } catch (SQLException ex) {
            System.err.println("Lỗi: " + ex.getMessage());
        }
        return false;
    }

    public Boolean Update(String maKhachHang, String tenKhachHang, String sdt, String diachi) {
        Connection con = DbConnect.openConnection();
        try {
            PreparedStatement pstmt = con.prepareStatement("UPDATE khachhang SET tenkhachhang=?, sdt=?, diachi=? WHERE idkhachhang=?");
            pstmt.setString(1, tenKhachHang);
            pstmt.setString(2, sdt);
            pstmt.setString(3, diachi);
            pstmt.setString(4, maKhachHang);
            int i = pstmt.executeUpdate();
            if (i > 0) {
                return true;
            }
        } catch (SQLException ex) {
            System.err.println("Lỗi: " + ex.getMessage());
        }
        return false;
    }

    public Boolean Delete(String maKhachHang) {
        Connection con = DbConnect.openConnection();
        try {
            PreparedStatement pstmt = con.prepareStatement("Delete from khachhang where idkhachhang=?");
            pstmt.setString(1, maKhachHang);
            int i = pstmt.executeUpdate();
            if (i > 0) {
                return true;
            }
        } catch (SQLException ex) {
            System.err.println("Lỗi: " + ex.getMessage());
        }
        return false;
    }

//    public List<String> getMaKhachHang() {
//        List<String> maKhachHangList = new ArrayList<>();
//        Connection con = DbConnect.openConnection();
//        try {
//            Statement stmt = con.createStatement();
//            ResultSet rs = stmt.executeQuery("SELECT idkhachhang FROM khachhang");
//            while (rs.next()) {
//                maKhachHangList.add(rs.getString("maNhaCungCap"));
//            }
//        } catch (SQLException ex) {
//            System.err.println("Lỗi: " + ex.getMessage());
//        } finally {
//            DbConnect.closeConnection(con);
//        }
//        return maKhachHangList;
//    }

    public KhachHang selectById(String t) {
        KhachHang ncc = null;
        try {
            Connection con = DbConnect.openConnection();
            String sql = "SELECT * FROM khachhang WHERE idkhachhang=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String maKhachHang = rs.getString("idkhachhang");
                String tenKhachHang = rs.getString("tenkhachhang");
                String sdt = rs.getString("sdt");
                String diachi = rs.getString("diachi");

                ncc = new KhachHang(maKhachHang, tenKhachHang, sdt, diachi);
            }
            DbConnect.closeConnection(con);
        } catch (SQLException e) {
            // TODO: handle exception    
            System.err.println("Lỗi: " + e.getMessage());
        }
        return ncc;
    }
}
