/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Bean;

/**
 *
 * @author PC
 */
public class MayTinh {
    private String maMay;
    private String maNhaCungCap;
    private String tenmay;
    private int soluong;
    private float gia;
    private String loaimay;
    private float kichthuoc;
    private String dungluongpin;
    private String xuatxu;
    public MayTinh(){
        
    }
    public MayTinh(String maMay,String maNhaCungCap,String tenmay,int soluong,float gia,String loaimay,float kichthuoc,String dungluongpin,String xuatxu){
        this.maMay = maMay;
        this.maNhaCungCap = maNhaCungCap;
        this.tenmay = tenmay;
        this.soluong = soluong;
        this.gia = gia;
        this.loaimay = loaimay;
        this.kichthuoc = kichthuoc;
        this.dungluongpin = dungluongpin;
        this.xuatxu = xuatxu;
    }
    public String getmaMay(){
        return maMay;
    }
    public void setmaMay(String maMay){
        this.maMay = maMay;
    }
    public String getmaNhaCungCap(){
        return maNhaCungCap;
    }
    public void setmaNhaCungCap(String maNhaCungCap){
        this.maNhaCungCap = maNhaCungCap;
    }
    public String gettenmay(){
        return tenmay;
    }
    public void settenmay(String tenmay){
        this.tenmay = tenmay;
    }
    public int getsoluong(){
        return soluong;
    }
    public void setsoluong(int soluong){
        this.soluong = soluong;
    }
    public float getgia(){
        return gia;
    }
    public void setgia(float gia){
        this.gia = gia;
    }
    public String getloaimay(){
        return loaimay;
    }
    public void setloaimay(String loaimay){
        this.loaimay = loaimay;
    }
    public float getkichthuoc()
    {
        return kichthuoc;
    }
    public void setkichthuoc(float kichthuoc){
        this.kichthuoc = kichthuoc;
    }
    public String getdungluongpin(){
        return dungluongpin;
    }
    public void setdungluongpin(String dungluongpin){
        this.dungluongpin = dungluongpin;
    }
    public String getxuatxu(){
        return xuatxu;
    }
    public void setxuatxu(String xuatxu){
        this.xuatxu = xuatxu;
    }
}
