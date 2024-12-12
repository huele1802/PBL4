/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Bean;

/**
 *
 * @author PC
 */
public class NhaCungCap {
    private String maNhaCungCap,tenNhaCungCap,sdt,diachi;
    public NhaCungCap(){
        
    }
    public NhaCungCap(String maNhaCungCap, String tenNhaCungCap, String sdt, String diachi) {
        this.maNhaCungCap = maNhaCungCap;
        this.tenNhaCungCap = tenNhaCungCap;
        this.sdt = sdt;
        this.diachi = diachi;
        
    }
    public String getmaNhaCungCap(){
        return maNhaCungCap;
    }
    public void setmaNhaCungCap(String maNhaCungCap){
        this.maNhaCungCap = maNhaCungCap;
    }
    public String gettenNhaCungCap(){
        return tenNhaCungCap;
    }
    public void settenNhaCungCap(String tenNhaCungCap){
        this.tenNhaCungCap = tenNhaCungCap;
    }
    public String getsdt(){
        return sdt;
    }
    public void setsdt(String sdt){
        this.sdt = sdt;
    }
    public String getdiachi(){
        return diachi;
    }
    public void setdiachi(String diachi){
        this.diachi = diachi;
    }
}
