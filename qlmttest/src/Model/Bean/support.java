/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Bean;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Windows
 */
public class support {
    public void editRowProduct(DefaultTableModel modelProduct, MayTinh product, String editby) {
        int rowCount = modelProduct.getRowCount();
        for (int i = 0; i < rowCount; i++) { //String maMay, String maNhaCungCap, String tenmay, int soluong, float gia, String loaimay, float kichthuoc, String dungluongpin, String xuatxu
            String cellValue = modelProduct.getValueAt(i, 0).toString();
            if (cellValue.equals(product.getmaMay())) {
                modelProduct.setValueAt(product.getmaNhaCungCap(), i, 1);
                modelProduct.setValueAt(product.gettenmay(), i, 2);
                modelProduct.setValueAt(product.getsoluong(), i, 3);
                modelProduct.setValueAt(product.getgia(), i, 4);
                modelProduct.setValueAt(product.getloaimay(), i, 5);
                modelProduct.setValueAt(product.getkichthuoc(), i, 6);
                modelProduct.setValueAt(product.getdungluongpin(), i, 7);
                modelProduct.setValueAt(product.getxuatxu(), i, 8);
                modelProduct.setValueAt(editby, i, 9);
                break;
            }
        }
    }
    
    public void editRowSupplier(DefaultTableModel modelSupplier, NhaCungCap ncc, String editby) {
        int rowCount = modelSupplier.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            String cellValue = modelSupplier.getValueAt(i, 0).toString();
            if (cellValue.equals(ncc.getmaNhaCungCap())) {
                modelSupplier.setValueAt(ncc.gettenNhaCungCap(), i, 1);
                modelSupplier.setValueAt(ncc.getsdt(), i, 2);
                modelSupplier.setValueAt(ncc.getdiachi(), i, 3);
                modelSupplier.setValueAt(editby, i, 4);
                break;
            }
        }
    }
    
    public void editRowCustomer(DefaultTableModel modelCustomer, KhachHang customer, String editby) {
        int rowCount = modelCustomer.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            String cellValue = modelCustomer.getValueAt(i, 0).toString();
            if (cellValue.equals(customer.getIdkhachhang())) {
                    modelCustomer.setValueAt(customer.getTenkhachhang(), i, 1);
                    modelCustomer.setValueAt(customer.getSdt(), i, 2);
                    modelCustomer.setValueAt(customer.getDiachi(), i, 3);
                modelCustomer.setValueAt(editby, i, 5);
                break;
            }
        }
    }
    
    public void editStatusEdit(DefaultTableModel model, String ma, String editBy) {
        int rowCount = model.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            String cellValue = model.getValueAt(i, 0).toString();
            if (cellValue.equals(ma)) {
                model.setValueAt(editBy, i, model.getColumnCount() - 1);
            }
        }
    }
    
    public void removeRowModel(DefaultTableModel model, String ma) {
        for (int i = 0; i < model.getRowCount(); i++) {
            String value = model.getValueAt(i, 0).toString();
            if (ma.equals(value)) {
                model.removeRow(i);
            }
        }
    }
    
    public void updateTotalBillsOfCustomer(DefaultTableModel model, String maKH, float total) {
        int rowCount = model.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            String cellValue = model.getValueAt(i, 0).toString();
            if (cellValue.equals(maKH)) {
                model.setValueAt(total, i, 4);
                break;
            }
        }
    }
}
