/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.DAO;

import Model.Bean.ChiTiet;
import Model.Bean.DbConnect;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Windows
 */
public class chitietDAO {

    ChiTiet chitiet = new ChiTiet();

    public List<ChiTiet> listChiTietBill(String maKhachHang) {
        List<ChiTiet> list = new ArrayList<>();

        try {
            Connection con = DbConnect.openConnection();
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM chitiet where idkhachhang=?");
            pstmt.setString(1, maKhachHang);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int idchitiet = rs.getInt("idchitiet");
                String idkhachhang = rs.getString("idkhachhang");
                String maMay = rs.getString("maMay");
                int soluong = rs.getInt("soluong");
                Date ngaymua = rs.getDate("ngaymua");
//                String dateString = rs.getString("ngaymua");
//                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//                Date ngaymua = dateFormat.parse(dateString);

                chitiet = new ChiTiet(idchitiet, idkhachhang, maMay, soluong, ngaymua);
                list.add(chitiet);
            }
        } catch (SQLException ex) {
            System.err.println("Lỗi: " + ex.getMessage());
        }
        return list;
    }
    
    public Boolean Add(ChiTiet chitiet) {
        Connection con = DbConnect.openConnection();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = format.format(chitiet.getNgaymua());
        try {
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO chitiet(idkhachhang, maMay, soluong, ngaymua) VALUES (?,?,?,?)");
            pstmt.setString(1, chitiet.getIdkhachhang());
            pstmt.setString(2, chitiet.getMaMay());
            pstmt.setInt(3, chitiet.getSoluong());
            pstmt.setString(4, dateString);
            int i = pstmt.executeUpdate();
            if (i > 0) {
                return true;
            }
        } catch (SQLException ex) {
            System.err.println("Lỗi: " + ex.getMessage());
        }
        return false;
    }
    
    public Boolean Delete(String id) {
        Connection con = DbConnect.openConnection();
        try {
            PreparedStatement pstmt = con.prepareStatement("Delete from chitiet where idchitiet=?");
            pstmt.setString(1, id);
            int i = pstmt.executeUpdate();
            if (i > 0) {
                return true;
            }
        } catch (SQLException ex) {
            System.err.println("Lỗi: " + ex.getMessage());
        }
        return false;
    }
}
