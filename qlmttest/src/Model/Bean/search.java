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
public class search {

    public DefaultTableModel searchProduct(DefaultTableModel table, String luachon, String searchContent) {
        DefaultTableModel result = new DefaultTableModel();
        String[] columnNames = {"ID", "SupplierID", "Name", "Quantity", "Price", "Type", "Size", "Capacity", "Origin", "Edit By"};
        result.setColumnIdentifiers(columnNames);
        
        for (int i = 0; i < table.getRowCount(); i++) {
            switch (luachon) {
                case "All":
                    all(result, table, i, searchContent);
                    break;
                case "ID":
                    mamay(result, table, i, searchContent);
                    break;
                case "Supplier ID":
                    manhacungcap(result, table, i, searchContent);
                    break;
                case "Name":
                    tenmay(result, table, i, searchContent);
                    break;
                case "Quantity":
                    soluong(result, table, i, searchContent);
                    break;
                case "Price":
                    gia(result, table, i, searchContent);
                    break;
                case "Type":
                    loaimay(result, table, i, searchContent);
                    break;
                case "Size":
                    kichthuoc(result, table, i, searchContent);
                    break;
                case "Capacity":
                    dungluongpin(result, table, i, searchContent);
                    break;
                case "Origin":
                    xuatxu(result, table, i, searchContent);
                    break;
            }
        }
        return result;
    }
    
    public DefaultTableModel searchSupplier(DefaultTableModel table, String luachon, String searchContent) {
        DefaultTableModel result = new DefaultTableModel();
        String[] columnNames = {"ID", "Name", "Phone Number", "Address", "Edit By"};
        result.setColumnIdentifiers(columnNames);
        
        for (int i = 0; i < table.getRowCount(); i++) {
            switch (luachon) {
                case "All":
                    allNCC(result, table, i, searchContent);
                    break;
                case "ID":
                    MaNCC(result, table, i, searchContent);
                    break;
                case "Phone Number":
                    SDTNCC(result, table, i, searchContent);
                    break;
                case "Name":
                    TenNCC(result, table, i, searchContent);
                    break;
                case "Address":
                    DiachiNCC(result, table, i, searchContent);
                    break;
            }
        }
        return result;
    }

    //result: moi, table: cu, i:row cua table
    public void all(DefaultTableModel result, DefaultTableModel table, int i, String searchContent) {
        if (table.getValueAt(i, 0).toString().toLowerCase().contains(searchContent.toLowerCase())
                || table.getValueAt(i, 1).toString().toLowerCase().contains(searchContent.toLowerCase())
                || table.getValueAt(i, 2).toString().toLowerCase().contains(searchContent.toLowerCase())
                || table.getValueAt(i, 3).toString().toLowerCase().contains(searchContent.toLowerCase())
                || table.getValueAt(i, 4).toString().toLowerCase().contains(searchContent.toLowerCase())
                || table.getValueAt(i, 5).toString().toLowerCase().contains(searchContent.toLowerCase())
                || table.getValueAt(i, 6).toString().toLowerCase().contains(searchContent.toLowerCase())
                || table.getValueAt(i, 7).toString().toLowerCase().contains(searchContent.toLowerCase())
                || table.getValueAt(i, 8).toString().toLowerCase().contains(searchContent.toLowerCase())) {
            result.addRow(new Object[]{
                table.getValueAt(i, 0),
                table.getValueAt(i, 1),
                table.getValueAt(i, 2),
                table.getValueAt(i, 3),
                table.getValueAt(i, 4),
                table.getValueAt(i, 5),
                table.getValueAt(i, 6),
                table.getValueAt(i, 7),
                table.getValueAt(i, 8),
                table.getValueAt(i, 9)
            });
        }
    }

    public void mamay(DefaultTableModel result, DefaultTableModel table, int i, String searchContent) {
        if (table.getValueAt(i, 0).toString().toLowerCase().contains(searchContent.toLowerCase())) {
            result.addRow(new Object[]{
                table.getValueAt(i, 0),
                table.getValueAt(i, 1),
                table.getValueAt(i, 2),
                table.getValueAt(i, 3),
                table.getValueAt(i, 4),
                table.getValueAt(i, 5),
                table.getValueAt(i, 6),
                table.getValueAt(i, 7),
                table.getValueAt(i, 8),
                table.getValueAt(i, 9)
            });
        }
    }

    public void manhacungcap(DefaultTableModel result, DefaultTableModel table, int i, String searchContent) {
        if (table.getValueAt(i, 1).toString().toLowerCase().contains(searchContent.toLowerCase())) {
            result.addRow(new Object[]{
                table.getValueAt(i, 0),
                table.getValueAt(i, 1),
                table.getValueAt(i, 2),
                table.getValueAt(i, 3),
                table.getValueAt(i, 4),
                table.getValueAt(i, 5),
                table.getValueAt(i, 6),
                table.getValueAt(i, 7),
                table.getValueAt(i, 8),
                table.getValueAt(i, 9)
            });
        }
    }

    public void tenmay(DefaultTableModel result, DefaultTableModel table, int i, String searchContent) {
        if (table.getValueAt(i, 2).toString().toLowerCase().contains(searchContent.toLowerCase())) {
            result.addRow(new Object[]{
                table.getValueAt(i, 0),
                table.getValueAt(i, 1),
                table.getValueAt(i, 2),
                table.getValueAt(i, 3),
                table.getValueAt(i, 4),
                table.getValueAt(i, 5),
                table.getValueAt(i, 6),
                table.getValueAt(i, 7),
                table.getValueAt(i, 8),
                table.getValueAt(i, 9)
            });
        }
    }

    public void soluong(DefaultTableModel result, DefaultTableModel table, int i, String searchContent) {
        if (table.getValueAt(i, 3).toString().toLowerCase().contains(searchContent.toLowerCase())) {
            result.addRow(new Object[]{
                table.getValueAt(i, 0),
                table.getValueAt(i, 1),
                table.getValueAt(i, 2),
                table.getValueAt(i, 3),
                table.getValueAt(i, 4),
                table.getValueAt(i, 5),
                table.getValueAt(i, 6),
                table.getValueAt(i, 7),
                table.getValueAt(i, 8),
                table.getValueAt(i, 9)
            });
        }
    }

    public void gia(DefaultTableModel result, DefaultTableModel table, int i, String searchContent) {
        if (table.getValueAt(i, 4).toString().toLowerCase().contains(searchContent.toLowerCase())) {
            result.addRow(new Object[]{
                table.getValueAt(i, 0),
                table.getValueAt(i, 1),
                table.getValueAt(i, 2),
                table.getValueAt(i, 3),
                table.getValueAt(i, 4),
                table.getValueAt(i, 5),
                table.getValueAt(i, 6),
                table.getValueAt(i, 7),
                table.getValueAt(i, 8),
                table.getValueAt(i, 9)
            });
        }
    }

    public void loaimay(DefaultTableModel result, DefaultTableModel table, int i, String searchContent) {
        if (table.getValueAt(i, 5).toString().toLowerCase().contains(searchContent.toLowerCase())) {
            result.addRow(new Object[]{
                table.getValueAt(i, 0),
                table.getValueAt(i, 1),
                table.getValueAt(i, 2),
                table.getValueAt(i, 3),
                table.getValueAt(i, 4),
                table.getValueAt(i, 5),
                table.getValueAt(i, 6),
                table.getValueAt(i, 7),
                table.getValueAt(i, 8),
                table.getValueAt(i, 9)
            });
        }
    }

    public void kichthuoc(DefaultTableModel result, DefaultTableModel table, int i, String searchContent) {
        if (table.getValueAt(i, 6).toString().toLowerCase().contains(searchContent.toLowerCase())) {
            result.addRow(new Object[]{
                table.getValueAt(i, 0),
                table.getValueAt(i, 1),
                table.getValueAt(i, 2),
                table.getValueAt(i, 3),
                table.getValueAt(i, 4),
                table.getValueAt(i, 5),
                table.getValueAt(i, 6),
                table.getValueAt(i, 7),
                table.getValueAt(i, 8),
                table.getValueAt(i, 9)
            });
        }
    }

    public void dungluongpin(DefaultTableModel result, DefaultTableModel table, int i, String searchContent) {
        if (table.getValueAt(i, 7).toString().toLowerCase().contains(searchContent.toLowerCase())) {
            result.addRow(new Object[]{
                table.getValueAt(i, 0),
                table.getValueAt(i, 1),
                table.getValueAt(i, 2),
                table.getValueAt(i, 3),
                table.getValueAt(i, 4),
                table.getValueAt(i, 5),
                table.getValueAt(i, 6),
                table.getValueAt(i, 7),
                table.getValueAt(i, 8),
                table.getValueAt(i, 9)
            });
        }
    }

    public void xuatxu(DefaultTableModel result, DefaultTableModel table, int i, String searchContent) {
        if (table.getValueAt(i, 8).toString().toLowerCase().contains(searchContent.toLowerCase())) {
            result.addRow(new Object[]{
                table.getValueAt(i, 0),
                table.getValueAt(i, 1),
                table.getValueAt(i, 2),
                table.getValueAt(i, 3),
                table.getValueAt(i, 4),
                table.getValueAt(i, 5),
                table.getValueAt(i, 6),
                table.getValueAt(i, 7),
                table.getValueAt(i, 8),
                table.getValueAt(i, 9)
            });
        }
    }
    
    //nha cung cap
    public void allNCC(DefaultTableModel result, DefaultTableModel table, int i, String searchContent) {
        if (table.getValueAt(i, 0).toString().toLowerCase().contains(searchContent.toLowerCase())
                || table.getValueAt(i, 1).toString().toLowerCase().contains(searchContent.toLowerCase())
                || table.getValueAt(i, 2).toString().toLowerCase().contains(searchContent.toLowerCase())
                || table.getValueAt(i, 3).toString().toLowerCase().contains(searchContent.toLowerCase())) {
            result.addRow(new Object[]{
                table.getValueAt(i, 0),
                table.getValueAt(i, 1),
                table.getValueAt(i, 2),
                table.getValueAt(i, 3),
                table.getValueAt(i, 4)
            });
        }
    }
    
    public void MaNCC(DefaultTableModel result, DefaultTableModel table, int i, String searchContent) {
        if (table.getValueAt(i, 0).toString().toLowerCase().contains(searchContent.toLowerCase())) {
            result.addRow(new Object[]{
                table.getValueAt(i, 0),
                table.getValueAt(i, 1),
                table.getValueAt(i, 2),
                table.getValueAt(i, 3),
                table.getValueAt(i, 4)
            });
        }
    }
    
    public void TenNCC(DefaultTableModel result, DefaultTableModel table, int i, String searchContent) {
        if (table.getValueAt(i, 1).toString().toLowerCase().contains(searchContent.toLowerCase())) {
            result.addRow(new Object[]{
                table.getValueAt(i, 0),
                table.getValueAt(i, 1),
                table.getValueAt(i, 2),
                table.getValueAt(i, 3),
                table.getValueAt(i, 4)
            });
        }
    }
    
    public void SDTNCC(DefaultTableModel result, DefaultTableModel table, int i, String searchContent) {
        if (table.getValueAt(i, 2).toString().toLowerCase().contains(searchContent.toLowerCase())) {
            result.addRow(new Object[]{
                table.getValueAt(i, 0),
                table.getValueAt(i, 1),
                table.getValueAt(i, 2),
                table.getValueAt(i, 3),
                table.getValueAt(i, 4)
            });
        }
    }
    
    public void DiachiNCC(DefaultTableModel result, DefaultTableModel table, int i, String searchContent) {
        if (table.getValueAt(i, 3).toString().toLowerCase().contains(searchContent.toLowerCase())) {
            result.addRow(new Object[]{
                table.getValueAt(i, 0),
                table.getValueAt(i, 1),
                table.getValueAt(i, 2),
                table.getValueAt(i, 3),
                table.getValueAt(i, 4)
            });
        }
    }
    
    public DefaultTableModel searchCustomer(DefaultTableModel table, String luachon, String searchContent) {
        DefaultTableModel result = new DefaultTableModel();
        String[] columnNames = {"ID", "Name", "Phone Number", "Address", "Total", "Edit By"};
        result.setColumnIdentifiers(columnNames);
        
        for (int i = 0; i < table.getRowCount(); i++) {
            switch (luachon) {
                case "All":
                    allCustomer(result, table, i, searchContent);
                    break;
                case "ID":
                    idkhachhang(result, table, i, searchContent);
                    break;
                case "Phone Number":
                    sdtkhachhang(result, table, i, searchContent);
                    break;
                case "Name":
                    tenkhachhang(result, table, i, searchContent);
                    break;
                case "Address":
                    diachikhachhang(result, table, i, searchContent);
                    break;
            }
        }
        return result;
    }

    private void allCustomer(DefaultTableModel result, DefaultTableModel table, int i, String searchContent) {
        if (table.getValueAt(i, 0).toString().toLowerCase().contains(searchContent.toLowerCase())
                || table.getValueAt(i, 1).toString().toLowerCase().contains(searchContent.toLowerCase())
                || table.getValueAt(i, 2).toString().toLowerCase().contains(searchContent.toLowerCase())
                || table.getValueAt(i, 3).toString().toLowerCase().contains(searchContent.toLowerCase())) {
            result.addRow(new Object[]{
                table.getValueAt(i, 0),
                table.getValueAt(i, 1),
                table.getValueAt(i, 2),
                table.getValueAt(i, 3),
                table.getValueAt(i, 4),
                table.getValueAt(i, 5)
            });
        }
    }

    private void idkhachhang(DefaultTableModel result, DefaultTableModel table, int i, String searchContent) {
        if (table.getValueAt(i, 0).toString().toLowerCase().contains(searchContent.toLowerCase())) {
            result.addRow(new Object[]{
                table.getValueAt(i, 0),
                table.getValueAt(i, 1),
                table.getValueAt(i, 2),
                table.getValueAt(i, 3),
                table.getValueAt(i, 4),
                table.getValueAt(i, 5)
            });
        }
    }

    private void sdtkhachhang(DefaultTableModel result, DefaultTableModel table, int i, String searchContent) {
        if (table.getValueAt(i, 2).toString().toLowerCase().contains(searchContent.toLowerCase())) {
            result.addRow(new Object[]{
                table.getValueAt(i, 0),
                table.getValueAt(i, 1),
                table.getValueAt(i, 2),
                table.getValueAt(i, 3),
                table.getValueAt(i, 4),
                table.getValueAt(i, 5)
            });
        }
    }

    private void tenkhachhang(DefaultTableModel result, DefaultTableModel table, int i, String searchContent) {
        if (table.getValueAt(i, 1).toString().toLowerCase().contains(searchContent.toLowerCase())) {
            result.addRow(new Object[]{
                table.getValueAt(i, 0),
                table.getValueAt(i, 1),
                table.getValueAt(i, 2),
                table.getValueAt(i, 3),
                table.getValueAt(i, 4),
                table.getValueAt(i, 5)
            });
        }
    }

    private void diachikhachhang(DefaultTableModel result, DefaultTableModel table, int i, String searchContent) {
        if (table.getValueAt(i, 3).toString().toLowerCase().contains(searchContent.toLowerCase())) {
            result.addRow(new Object[]{
                table.getValueAt(i, 0),
                table.getValueAt(i, 1),
                table.getValueAt(i, 2),
                table.getValueAt(i, 3),
                table.getValueAt(i, 4),
                table.getValueAt(i, 5)
            });
        }
    }
}
