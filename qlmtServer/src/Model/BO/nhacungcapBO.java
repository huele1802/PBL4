/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.BO;

import java.util.List;
import Model.Bean.NhaCungCap;
import Model.DAO.nhacungcapDAO;

/**
 *
 * @author Windows
 */
public class nhacungcapBO {

    nhacungcapDAO nccDAO = new nhacungcapDAO();
    
    public List<String> getAllMaNhaCungCap() {
        return nccDAO.getAllMaNhaCungCap();
    }

    public List<NhaCungCap> listNhaCungCap() {
        return nccDAO.listNhaCungCap();
    }

    public Boolean Add(String maNhaCungCap, String tenNhaCungCap, String sdt, String diachi) {
        return nccDAO.Add(maNhaCungCap, tenNhaCungCap, sdt, diachi);
    }

    public Boolean Update(String maNhaCungCap, String tenNhaCungCap, String sdt, String diachi) {
        return nccDAO.Update(maNhaCungCap, tenNhaCungCap, sdt, diachi);
    }

    public Boolean Delete(String maNhaCungCap) {
        return nccDAO.Delete(maNhaCungCap);
    }

    public List<String> getMaNhaCungCap() {
        return nccDAO.getMaNhaCungCap();
    }

    public NhaCungCap selectById(String t) {
        return nccDAO.selectById(t);
    }
}
