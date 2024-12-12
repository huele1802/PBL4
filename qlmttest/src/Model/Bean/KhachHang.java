/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Bean;

/**
 *
 * @author Windows
 */
public class KhachHang {
    private String idkhachhang;
    private String tenkhachhang;
    private String sdt;
    private String diachi;

    public KhachHang() {
    }

    public KhachHang(String idkhachhang, String tenkhachhang, String sdt, String diachi) {
        this.idkhachhang = idkhachhang;
        this.tenkhachhang = tenkhachhang;
        this.sdt = sdt;
        this.diachi = diachi;
    }

    public String getIdkhachhang() {
        return idkhachhang;
    }

    public void setIdkhachhang(String idkhachhang) {
        this.idkhachhang = idkhachhang;
    }

    public String getTenkhachhang() {
        return tenkhachhang;
    }

    public void setTenkhachhang(String tenkhachhang) {
        this.tenkhachhang = tenkhachhang;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }
}
