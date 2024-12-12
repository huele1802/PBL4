/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change table license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit table template
 */
package Model.Bean;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Windows
 */
public class Custom {
    public void CustomTable(JTable table) {
        table.setFocusable(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setRowHeight(25);
        table.setSelectionBackground(new Color(189, 247, 255));
        table.setSelectionForeground(Color.BLACK);
        table.setFont(new Font("Arial", Font.PLAIN, 16));
        table.setAutoscrolls(true);
        table.setGridColor(Color.BLACK);

        JTableHeader header = table.getTableHeader();
        header.setBackground(table.getSelectionBackground());
        header.setBackground(new Color(102,204,255));
        header.setFont(new Font("Arial", Font.BOLD, 16));
        header.setForeground(Color.BLACK);
        header.setReorderingAllowed(false);
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);  // Căn giữa tiêu đề
        header.setDefaultRenderer(headerRenderer);
    }
    
    public void CustomTableAfterSetModel(JTable table, DefaultTableModel model){
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            if (table.getRowCount() > 0) {
                if (!(table.getValueAt(0, i) instanceof Float)) {
                    table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
                }
            }
        }
        
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
    }
    
    public void adjustColumnWidths(JTable table) {
        TableColumnModel columnModel = table.getColumnModel();

        for (int col = 0; col < table.getColumnCount(); col++) {
            int maxWidth = 0;

            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer cellRenderer = table.getCellRenderer(row, col);
                Object value = table.getValueAt(row, col);
                Component comp = cellRenderer.getTableCellRendererComponent(table, value, false, false, row, col);
                maxWidth = Math.max(comp.getPreferredSize().width, maxWidth);
            }

            TableColumn column = columnModel.getColumn(col);
            column.setPreferredWidth(maxWidth + 10); // Thêm một khoảng trống cho độ rộng
        }
    }
}
