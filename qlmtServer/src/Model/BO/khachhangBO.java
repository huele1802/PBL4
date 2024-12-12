/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.BO;

import Model.Bean.KhachHang;
import Model.DAO.khachhangDAO;
import java.util.List;

/**
 *
 * @author Windows
 */
public class khachhangBO {
    khachhangDAO khDAO = new khachhangDAO();
    public List<KhachHang> listKhachHang() {
        return khDAO.listKhachHang();
    }

    public Boolean Add(String maKhachHang, String tenKhachHang, String sdt, String diachi) {
        return khDAO.Add(maKhachHang, tenKhachHang, sdt, diachi);
    }

    public Boolean Update(String maKhachHang, String tenKhachHang, String sdt, String diachi) {
        return khDAO.Update(maKhachHang, tenKhachHang, sdt, diachi);
    }

    public Boolean Delete(String maKhachHang) {
        return khDAO.Delete(maKhachHang);
    }

    public KhachHang selectById(String t) {
        return khDAO.selectById(t);
    }
}
