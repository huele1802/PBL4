/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Bean;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Windows
 */
public class ChiTiet implements Serializable {
    private int idchitiet;
    private String idkhachhang;
    private String maMay;
    private int soluong;
    private Date ngaymua;

    public ChiTiet() {
    }

    public ChiTiet(int idchitiet, String idkhachhang, String maMay, int soluong, Date ngaymua) {
        this.idchitiet = idchitiet;
        this.idkhachhang = idkhachhang;
        this.maMay = maMay;
        this.soluong = soluong;
        this.ngaymua = ngaymua;
    }

    public int getIdchitiet() {
        return idchitiet;
    }

    public void setIdchitiet(int idchitiet) {
        this.idchitiet = idchitiet;
    }

    public String getIdkhachhang() {
        return idkhachhang;
    }

    public void setIdkhachhang(String idkhachhang) {
        this.idkhachhang = idkhachhang;
    }

    public String getMaMay() {
        return maMay;
    }

    public void setMaMay(String maMay) {
        this.maMay = maMay;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public Date getNgaymua() {
        return ngaymua;
    }

    public void setNgaymua(Date ngaymua) {
        this.ngaymua = ngaymua;
    }
}
