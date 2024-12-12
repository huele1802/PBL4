/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.BO;

import java.util.List;
import Model.Bean.MayTinh;
import Model.DAO.maytinhDAO;

/**
 *
 * @author Windows
 */
public class maytinhBO {
    maytinhDAO mtDAO = new maytinhDAO();
    public List<MayTinh> listmaytinh() {
        return mtDAO.listmaytinh();
    }

    public Boolean Add(String maMay, String maNhaCungCap, String tenmay, int soluong, float gia, String loaimay, float kichthuoc, String dungluongpin, String xuatxu) {
        return mtDAO.Add(maMay, maNhaCungCap, tenmay, soluong, gia, loaimay, kichthuoc, dungluongpin, xuatxu);
    }

    public Boolean Update(String maMay, String maNhaCungCap, String tenmay, int soluong, float gia, String loaimay, float kichthuoc, String dungluongpin, String xuatxu) {
        return mtDAO.Update(maMay, maNhaCungCap, tenmay, soluong, gia, loaimay, kichthuoc, dungluongpin, xuatxu);
    }

    public Boolean Delete(String maMay) {
        return mtDAO.Delete(maMay);
    }

    public MayTinh selectById(String t) {
        return mtDAO.selectById(t);
    }
    
    public float getGiaByMaMay(String mamay) {
        return mtDAO.getGiaByMaMay(mamay);
    }
    
    public List<MayTinh> searchTatCa(String text) {
        return mtDAO.searchMaMay(text);
    }

    public List<MayTinh> searchMaMay(String text) {
        return mtDAO.searchMaMay(text);
    }

    public List<MayTinh> searchKichThuoc(String text) {
        return mtDAO.searchKichThuoc(text);
    }

    public List<MayTinh> searchTenMay(String text) {
        return mtDAO.searchTenMay(text);
    }

    public List<MayTinh> searchSoLuong(String text) {
        return mtDAO.searchSoLuong(text);
    }

    public List<MayTinh> searchDonGia(String text) {
        return mtDAO.searchDonGia(text);
    }

    public List<MayTinh> searchmaNhaCungCap(String text) {
        return mtDAO.searchmaNhaCungCap(text);
    }

    public List<MayTinh> searchLoaiMay(String text) {
        return mtDAO.searchLoaiMay(text);
    }

    public List<MayTinh> searchDungLuong(String text) {
        return mtDAO.searchDungLuong(text);
    }

    public List<MayTinh> searchXuatXu(String text) {
        return mtDAO.searchXuatXu(text);
    }

    public MayTinh searchId(String text) {
        return mtDAO.searchId(text);
    }
}
