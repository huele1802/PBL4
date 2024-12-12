/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.BO;

import Model.Bean.ChiTiet;
import Model.DAO.chitietDAO;
import java.util.List;

/**
 *
 * @author Windows
 */
public class chitietBO {
    chitietDAO ctDAO = new chitietDAO();
    maytinhBO mtBO = new maytinhBO();
    
    public List<ChiTiet> listChiTietBill(String maKhachHang) {
        return ctDAO.listChiTietBill(maKhachHang);
    }
    
    public float TotalBillofCustomer(String maKhachHang) {
        float giaMay = 0;
        for (ChiTiet chitiet : listChiTietBill(maKhachHang)) {
            giaMay += mtBO.getGiaByMaMay(chitiet.getMaMay()) * chitiet.getSoluong();
        }
        return giaMay;
    }
    
    public Boolean Add(ChiTiet chitiet) {
        return ctDAO.Add(chitiet);
    }
    
    public Boolean Delete(String id) {
        return ctDAO.Delete(id);
    }
}
