/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.DAO;

import java.util.ArrayList;
import java.util.List;
import Model.Bean.DbConnect;
import Model.Bean.MayTinh;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author PC
 */
public class maytinhDAO {

    MayTinh maytinh = new MayTinh();

    public List<MayTinh> listmaytinh() {
        List<MayTinh> list = new ArrayList<>();
        Connection con = DbConnect.openConnection();

        try {
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM maytinh");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String maMay = rs.getString(1);
                String maNhaCungCap = rs.getString(2);
                String tenMay = rs.getString(3);
                int soLuong = rs.getInt(4);
                float gia = rs.getFloat(5);
                String loaiMay = rs.getString(6);
                float kichThuoc = rs.getFloat(7);
                String dungLuongPin = rs.getString(8);
                String xuatXu = rs.getString(9);

                MayTinh mt = new MayTinh(maMay, maNhaCungCap, tenMay, soLuong, gia, loaiMay, kichThuoc, dungLuongPin, xuatXu);
                list.add(mt);
            }
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(nhacungcapDAO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } finally {
            DbConnect.closeConnection(con);
        }
        return list;
    }

    public Boolean Add(String maMay, String maNhaCungCap, String tenmay, int soluong, float gia, String loaimay, float kichthuoc, String dungluongpin, String xuatxu) {
        Connection con = DbConnect.openConnection();
        try {
            if (maNhaCungCap == null || maNhaCungCap.isEmpty()) {
                System.out.println("Mã nhà cung cấp không hợp lệ.");
                return false;
            }
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO maytinh(maMay, maNhaCungCap, tenmay, soluong, gia, loaimay, kichthuoc, dungluongpin, xuatxu) VALUES (?,?,?,?,?,?,?,?,?)");
            pstmt.setString(1, maMay);
            pstmt.setString(2, maNhaCungCap);
            pstmt.setString(3, tenmay);
            pstmt.setInt(4, soluong);
            pstmt.setFloat(5, gia);
            pstmt.setString(6, loaimay);
            pstmt.setFloat(7, kichthuoc);
            pstmt.setString(8, dungluongpin);
            pstmt.setString(9, xuatxu);
            int i = pstmt.executeUpdate();
            if (i > 0) {
                System.out.println("Thêm sản phẩm thành công.");
                return true;
            } else {
                System.out.println("Thêm sản phẩm thất bại.");
            }
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(maytinhDAO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } finally {
            DbConnect.closeConnection(con);
        }
        return false;
    }

    public Boolean Update(String maMay, String maNhaCungCap, String tenmay, int soluong, float gia, String loaimay, float kichthuoc, String dungluongpin, String xuatxu) {
        Connection con = DbConnect.openConnection();
        try {
            PreparedStatement pstmt = con.prepareStatement("UPDATE maytinh SET maMay=?, maNhaCungCap=?, tenmay=?, soluong=?, gia=?, loaimay=?, kichthuoc=?, dungluongpin=?, xuatxu=? WHERE maMay=?");
            pstmt.setString(1, maMay);
            pstmt.setString(2, maNhaCungCap);
            pstmt.setString(3, tenmay);
            pstmt.setInt(4, soluong);
            pstmt.setFloat(5, gia);
            pstmt.setString(6, loaimay);
            pstmt.setFloat(7, kichthuoc);
            pstmt.setString(8, dungluongpin);
            pstmt.setString(9, xuatxu);
            pstmt.setString(10, maMay);
            int i = pstmt.executeUpdate();
            if (i > 0) {
                return true;
            }
        } catch (SQLException ex) {
            System.err.println("Lỗi: " + ex.getMessage());
        } finally {
            DbConnect.closeConnection(con);
        }
        return false;
    }

    public Boolean Delete(String maMay) {
        Connection con = DbConnect.openConnection();
        try {

            PreparedStatement pstmtMaytinh = con.prepareStatement("DELETE FROM maytinh WHERE maMay=? ");
            pstmtMaytinh.setString(1, maMay);
            pstmtMaytinh.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Lỗi: " + ex.getMessage());
        } finally {

            DbConnect.closeConnection(con);
        }
        return true;
    }

    public MayTinh selectById(String t) {
        MayTinh mt = null;
        try {
            Connection con = DbConnect.openConnection();
            String sql = "SELECT * FROM maytinh WHERE maMay=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String maMay = rs.getString("maMay");
                String maNhaCungCap = rs.getString("maNhaCungCap");
                String tenmay = rs.getString("tenmay");
                int soluong = rs.getInt("soluong");
                String sl = String.valueOf(soluong);
                float gia = rs.getFloat("gia");
                String g = String.valueOf(gia);
                String loaimay = rs.getString("loaimay");
                float kichthuoc = rs.getFloat("kichthuoc");
                String kt = String.valueOf(kichthuoc);
                String dungluongpin = rs.getString("dungluongpin");
                String xuatxu = rs.getString("xuatxu");
                mt = new MayTinh(maMay, maNhaCungCap, tenmay, soluong, gia, loaimay, kichthuoc, dungluongpin, xuatxu);
            }
            DbConnect.closeConnection(con);
        } catch (SQLException e) {
            // TODO: handle exception       
            System.err.println("Lỗi: " + e.getMessage());
        }
        return mt;
    }
    
    public float getGiaByMaMay(String mamay) {
        float gia = 0;
        try {
            Connection con = DbConnect.openConnection();
            String sql = "SELECT gia FROM maytinh WHERE maMay=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, mamay);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                gia = rs.getFloat("gia");
            }
            DbConnect.closeConnection(con);
        } catch (SQLException e) {
            // TODO: handle exception       
            System.err.println("Lỗi: " + e.getMessage());
        }
        return gia;
    }

    public List<MayTinh> searchTatCa(String text) {
        List<MayTinh> result = new ArrayList<>();
        List<MayTinh> armt = listmaytinh();
        for (var mt : armt) {

            if (mt.getmaMay().toLowerCase().contains(text.toLowerCase()) || mt.gettenmay().toLowerCase().contains(text.toLowerCase())
                    || mt.getmaNhaCungCap().toLowerCase().contains(text.toLowerCase())
                    || mt.gettenmay().toLowerCase().contains(text.toLowerCase())
                    || mt.getxuatxu().toLowerCase().contains(text.toLowerCase())) {
                result.add(mt);
            }
        }
        return result;
    }

    public List<MayTinh> searchMaMay(String text) {
        List<MayTinh> result = new ArrayList<>();
        List<MayTinh> armt = listmaytinh();
        for (var mt : armt) {

            if (mt.getmaMay().toLowerCase().contains(text.toLowerCase())) {
                result.add(mt);
            }
        }
        return result;
    }

    public List<MayTinh> searchKichThuoc(String text) {
        List<MayTinh> result = new ArrayList<>();
        List<MayTinh> armt = listmaytinh();
        for (var mt : armt) {

            if (text.length() != 0) {
                if (mt.getsoluong() > Float.parseFloat(text)) {
                    result.add(mt);
                }
            } else {
                result.add(mt);
            }
        }
        return result;
    }

    public List<MayTinh> searchTenMay(String text) {
        List<MayTinh> result = new ArrayList<>();
        List<MayTinh> armt = listmaytinh();
        for (var mt : armt) {

            if (mt.gettenmay().toLowerCase().contains(text.toLowerCase())) {
                result.add(mt);
            }

        }
        return result;
    }

    public List<MayTinh> searchSoLuong(String text) {
        List<MayTinh> result = new ArrayList<>();
        List<MayTinh> armt = listmaytinh();
        for (var mt : armt) {

            if (text.length() != 0) {
                if (mt.getsoluong() > Integer.parseInt(text)) {
                    result.add(mt);
                }
            } else {
                result.add(mt);
            }
        }

        return result;
    }

    public List<MayTinh> searchDonGia(String text) {
        List<MayTinh> result = new ArrayList<>();
        List<MayTinh> armt = listmaytinh();
        for (var mt : armt) {

            if (text.length() != 0) {
                if (mt.getgia() > Float.parseFloat(text)) {
                    result.add(mt);
                }
            } else {
                result.add(mt);
            }
        }

        return result;
    }

    public List<MayTinh> searchmaNhaCungCap(String text) {
        List<MayTinh> result = new ArrayList<>();
        List<MayTinh> armt = listmaytinh();
        for (var mt : armt) {
            if (mt.getmaNhaCungCap().toLowerCase().contains(text.toLowerCase())) {
                result.add(mt);
            }
        }
        return result;
    }

    public List<MayTinh> searchLoaiMay(String text) {
        List<MayTinh> result = new ArrayList<>();
        List<MayTinh> armt = listmaytinh();
        for (var mt : armt) {
            if (mt.getloaimay().toLowerCase().contains(text.toLowerCase())) {
                result.add(mt);
            }
        }
        return result;
    }

    public List<MayTinh> searchDungLuong(String text) {
        List<MayTinh> result = new ArrayList<>();
        List<MayTinh> armt = listmaytinh();
        for (var mt : armt) {
            if (mt.getdungluongpin().toLowerCase().contains(text.toLowerCase())) {
                result.add(mt);
            }
        }
        return result;
    }

    public List<MayTinh> searchXuatXu(String text) {
        List<MayTinh> result = new ArrayList<>();
        List<MayTinh> armt = listmaytinh();
        for (var mt : armt) {
            if (mt.getxuatxu().toLowerCase().contains(text.toLowerCase())) {
                result.add(mt);
            }
        }
        return result;
    }

    public MayTinh searchId(String text) {
        MayTinh result = new MayTinh();
        List<MayTinh> armt = listmaytinh();
        for (var mt : armt) {
            if (mt.getmaMay().toLowerCase().contains(text.toLowerCase())) {
                return mt;
            }
        }
        return null;
    }
}
