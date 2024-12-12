/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import Controller.ServerController;
import Model.BO.accountBO;
import Model.BO.chitietBO;
import Model.BO.khachhangBO;
import Model.BO.maytinhBO;
import Model.BO.nhacungcapBO;
import Model.Bean.Account;
import Model.Bean.AddDataToTable;
import Model.Bean.ClientSocketModel;
import Model.Bean.Custom;
import Model.Bean.KhachHang;
import Model.Bean.MayTinh;
import Model.Bean.NhaCungCap;
import java.awt.Color;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Windows
 */
public final class ServerJFrame extends javax.swing.JFrame  implements AddDataToTable {
    ServerSocket server;
    String localHost;

    DefaultTableModel modelAccounts;
    DefaultTableModel modelHistory;
    
    DefaultTableModel modelProduct;
    DefaultTableModel modelSupplier;
    DefaultTableModel modelCustomer;

    ServerController serverController;
    
    accountBO accBO;
    maytinhBO mtBO;
    nhacungcapBO nccBO;
    khachhangBO khBO;
    chitietBO ctBO;
    /**
     * Creates new form ServerJFrame
     */
    public ServerJFrame() {
        initComponents();
        accBO = new accountBO();
        mtBO = new maytinhBO();
        nccBO = new nhacungcapBO();
        khBO = new khachhangBO();
        ctBO = new chitietBO();
        setLocationRelativeTo(null);
        
        Online.setVisible(false);
        editHistory.setVisible(false);
        
        lbLogin.setText("0");
        modelHistory = createModelHistory();
        setListAccountModel(getListAccounts());
        setListProductModel(getListProducts());
        setListSupplierModel(getListSuppliers());
        setListCustomerModel(getListCustomers(), getListTotalOfCustomers());
        loadTableOnl(modelAccounts);
    }
    
    List<Float> getListTotalOfCustomers() {
        List<Float> list = new ArrayList<>();
        for (KhachHang khachhang : getListCustomers()) {
            float total = ctBO.TotalBillofCustomer(khachhang.getIdkhachhang());
            list.add(total);
        }
        return list;
    }
    
    List<Account> getListAccounts() {
        return accBO.listAccount();
    }

    List<MayTinh> getListProducts() {
        return mtBO.listmaytinh();
    }

    List<NhaCungCap> getListSuppliers() {
        return nccBO.listNhaCungCap();
    }
    
    List<KhachHang> getListCustomers() {
        return khBO.listKhachHang();
    }
    
    public DefaultTableModel getModelAccounts() {
        return modelAccounts;
    }

    public void setModelAccounts(DefaultTableModel modelAccounts) {
        this.modelAccounts = modelAccounts;
    }

    public DefaultTableModel getModelProduct() {
        return modelProduct;
    }

    public DefaultTableModel getModelSupplier() {
        return modelSupplier;
    }

    public DefaultTableModel getModelCustomer() {
        return modelCustomer;
    }

    public void setModelProduct(DefaultTableModel modelProduct) {
        this.modelProduct = modelProduct;
    }

    public void setModelSupplier(DefaultTableModel modelSupplier) {
        this.modelSupplier = modelSupplier;
    }

    public void setModelCustomer(DefaultTableModel modelCustomer) {
        this.modelCustomer = modelCustomer;
    }
    
    public DefaultTableModel createModelHistory() {
        String[] columnNames = {"Action", "Username", "Category", "ID", "Start time", "End time"};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columnNames);
        return model;
    }
    
    public DefaultTableModel getModelEditHistory() {
        return modelHistory;
    }

    public JTable getTbEditHistory() {
        return tbEditHistory;
    }

    public void setListProductModel(List<MayTinh> listProduct) {
        String[] columnNames = {"ID", "SupplierID", "Name", "Quantity", "Price", "Type", "Size", "Capacity", "Origin", "Edit By"};
        modelProduct = new DefaultTableModel();
        modelProduct.setColumnIdentifiers(columnNames);
        for (int i = 0; i < listProduct.size(); i++) {
            modelProduct.addRow(new Object[]{
                listProduct.get(i).getmaMay(),
                listProduct.get(i).getmaNhaCungCap(),
                listProduct.get(i).gettenmay(),
                listProduct.get(i).getsoluong(),
                listProduct.get(i).getgia(),
                listProduct.get(i).getloaimay(),
                listProduct.get(i).getkichthuoc(),
                listProduct.get(i).getdungluongpin(),
                listProduct.get(i).getxuatxu(),
                ""
            });
        }
    }

    public void setListSupplierModel(List<NhaCungCap> listSupplier) {
        String[] columnNames = {"ID", "Name", "Phone Number", "Address", "Edit By"};
        modelSupplier = new DefaultTableModel();
        modelSupplier.setColumnIdentifiers(columnNames);
        for (int i = 0; i < listSupplier.size(); i++) {
            modelSupplier.addRow(new Object[]{
                listSupplier.get(i).getmaNhaCungCap(),
                listSupplier.get(i).gettenNhaCungCap(),
                listSupplier.get(i).getsdt(),
                listSupplier.get(i).getdiachi(),
                ""
            });
        }
    }

    public void setListCustomerModel(List<KhachHang> listCustomer, List<Float> total) {
        String[] columnNames = {"ID", "Name", "Phone Number", "Address", "Total", "Edit By"};
        modelCustomer = new DefaultTableModel();
        modelCustomer.setColumnIdentifiers(columnNames);
        for (int i = 0; i < listCustomer.size(); i++) {
            modelCustomer.addRow(new Object[]{
                listCustomer.get(i).getIdkhachhang(),
                listCustomer.get(i).getTenkhachhang(),
                listCustomer.get(i).getSdt(),
                listCustomer.get(i).getDiachi(),
                total.get(i),
                ""
            });
        }
    }

    //modelAccounts
    public void setListAccountModel(List<Account> listAcc) {
        String[] columnNames = {"STT", "ID", "Fullname", "Username", "Status", "Address", "Port", "Reset Password"};
        modelAccounts = new DefaultTableModel();
        modelAccounts.setColumnIdentifiers(columnNames);
        for (int i = 0; i < listAcc.size(); i++) {
            modelAccounts.addRow(new Object[]{i + 1,
                listAcc.get(i).getidaccount(),
                listAcc.get(i).getname(),
                listAcc.get(i).getusername(),
                "Offline",
                "",
                "",
                ""
            });
        }
    }
    
    public void loadTableOnl(DefaultTableModel model) {
        Custom custom = new Custom();
        custom.CustomTable(tbListAccount);
        DefaultTableModel currentModel = (DefaultTableModel) tbListAccount.getModel();
        currentModel.setRowCount(0);

        // Load new data into the table
        for (int i = 0; i < model.getRowCount(); i++) {
            Object[] rowData = new Object[model.getColumnCount()];
            for (int j = 0; j < model.getColumnCount(); j++) {
                rowData[j] = model.getValueAt(i, j);
            }
            currentModel.addRow(rowData);
        }
        custom.CustomTableAfterSetModel(tbListAccount, model);
    }
    
    public void setTableOnl(DefaultTableModel model) {
        modelAccounts = model;
        loadTableOnl(modelAccounts);
    }
    
    public void setLoginCount(int count) {
        lbLogin.setText(count + "");
    }

    public void setStatusAccountOnline(String username, String status, String addr, String port) {
        int rowCount = modelAccounts.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            String cellValue = modelAccounts.getValueAt(i, 3).toString();
            if (cellValue.equals(username)) {
                modelAccounts.setValueAt(status, i, 4);
                modelAccounts.fireTableCellUpdated(i, 4);
                modelAccounts.setValueAt(addr, i, 5);
                modelAccounts.fireTableCellUpdated(i, 5);
                modelAccounts.setValueAt(port, i, 6);
                modelAccounts.fireTableCellUpdated(i, 6);
            }
        }
    }
    
    public void setStatusResetPW(String username, String reset) {
        int rowCount = modelAccounts.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            String cellValue = modelAccounts.getValueAt(i, 3).toString();
            if (cellValue.equals(username)) {
                modelAccounts.setValueAt(reset, i, 7);
                modelAccounts.fireTableCellUpdated(i, 7);
            }
        }
    }
    
    private boolean checkOnl(int selectedRow) {
        if (tbListAccount.getValueAt(selectedRow, 4).equals("Online"))
            return true;
        return false;
    }
    
    private boolean checkRequest(int selectedRow) {
        if (tbListAccount.getValueAt(selectedRow, 7).equals("X"))
            return true;
        return false;
    }
    
    private ClientSocketModel checkConnect(String username) {
        for (ClientSocketModel object : serverController.getClientList()) {
            if (object.getUsername().equals(username) && !object.isLogin())
                return object;
        }
        return null;
    }
    
    @Override
    public void addDataToTable(String username) {
        Account acc = accBO.getAccountByUsername(username);

        if (acc != null) {
            modelAccounts.addRow(new Object[]{modelAccounts.getRowCount() + 1, acc.getidaccount(), acc.getname(), acc.getusername(), "Offline", "", "", ""});
            loadTableOnl(modelAccounts);
        } else {
            System.out.println("Account is null for username: " + username);
        }
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnMenu = new javax.swing.JPanel();
        lbGrocery = new javax.swing.JLabel();
        imgFrocery = new javax.swing.JLabel();
        Online = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        StatusSocket = new javax.swing.JPanel();
        lbStatusSocket = new javax.swing.JLabel();
        editHistory = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        OnlineJPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        lbLogin = new javax.swing.JLabel();
        btnResetPW = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbListAccount = new javax.swing.JTable();
        btnAddAcc = new javax.swing.JButton();
        btnDelAcc = new javax.swing.JButton();
        statusEdit = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbEditHistory = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnMenu.setBackground(new java.awt.Color(58, 62, 71));
        pnMenu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbGrocery.setFont(new java.awt.Font(".VnRevue", 1, 30)); // NOI18N
        lbGrocery.setForeground(new java.awt.Color(163, 223, 253));
        lbGrocery.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbGrocery.setText("COMPUTER");
        pnMenu.add(lbGrocery, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 240, 50));

        imgFrocery.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        imgFrocery.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Ảnh chụp màn hình 2023-12-13 000036.png"))); // NOI18N
        imgFrocery.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        pnMenu.add(imgFrocery, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 240, 200));

        Online.setBackground(new java.awt.Color(58, 62, 71));
        Online.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        Online.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Online.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                OnlineMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                OnlineMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                OnlineMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                OnlineMousePressed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("SF Pro Display", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/user-pen.png"))); // NOI18N
        jLabel2.setText(" Account");

        javax.swing.GroupLayout OnlineLayout = new javax.swing.GroupLayout(Online);
        Online.setLayout(OnlineLayout);
        OnlineLayout.setHorizontalGroup(
            OnlineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, OnlineLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                .addContainerGap())
        );
        OnlineLayout.setVerticalGroup(
            OnlineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, OnlineLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnMenu.add(Online, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 260, 240, 60));

        StatusSocket.setBackground(new java.awt.Color(58, 62, 71));
        StatusSocket.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        StatusSocket.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        StatusSocket.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                StatusSocketMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                StatusSocketMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                StatusSocketMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                StatusSocketMousePressed(evt);
            }
        });

        lbStatusSocket.setFont(new java.awt.Font("SF Pro Display", 1, 24)); // NOI18N
        lbStatusSocket.setForeground(new java.awt.Color(255, 255, 255));
        lbStatusSocket.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbStatusSocket.setText("START");

        javax.swing.GroupLayout StatusSocketLayout = new javax.swing.GroupLayout(StatusSocket);
        StatusSocket.setLayout(StatusSocketLayout);
        StatusSocketLayout.setHorizontalGroup(
            StatusSocketLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, StatusSocketLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbStatusSocket, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                .addContainerGap())
        );
        StatusSocketLayout.setVerticalGroup(
            StatusSocketLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(StatusSocketLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbStatusSocket, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnMenu.add(StatusSocket, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 240, 60));

        editHistory.setBackground(new java.awt.Color(58, 62, 71));
        editHistory.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        editHistory.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        editHistory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                editHistoryMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                editHistoryMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                editHistoryMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                editHistoryMousePressed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("SF Pro Display", 1, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/time-past.png"))); // NOI18N
        jLabel7.setText(" Edit History");

        javax.swing.GroupLayout editHistoryLayout = new javax.swing.GroupLayout(editHistory);
        editHistory.setLayout(editHistoryLayout);
        editHistoryLayout.setHorizontalGroup(
            editHistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editHistoryLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                .addContainerGap())
        );
        editHistoryLayout.setVerticalGroup(
            editHistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editHistoryLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnMenu.add(editHistory, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 320, 240, 60));

        getContentPane().add(pnMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 600));

        OnlineJPanel.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setText("Online: ");

        lbLogin.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbLogin.setText("0");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel4)
                .addGap(24, 24, 24)
                .addComponent(lbLogin)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel4)
                .addComponent(lbLogin))
        );

        btnResetPW.setBackground(new java.awt.Color(54, 164, 255));
        btnResetPW.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnResetPW.setForeground(new java.awt.Color(255, 255, 255));
        btnResetPW.setText("Reset Password");
        btnResetPW.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetPWActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        tbListAccount.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "ID", "Fullname", "Username", "Status", "Address", "Port", "Reset Password"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbListAccount.setGridColor(new java.awt.Color(153, 204, 255));
        tbListAccount.setShowGrid(true);
        jScrollPane2.setViewportView(tbListAccount);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1136, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 505, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnAddAcc.setBackground(new java.awt.Color(54, 164, 255));
        btnAddAcc.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnAddAcc.setForeground(new java.awt.Color(255, 255, 255));
        btnAddAcc.setText("Add");
        btnAddAcc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddAccActionPerformed(evt);
            }
        });

        btnDelAcc.setBackground(new java.awt.Color(54, 164, 255));
        btnDelAcc.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnDelAcc.setForeground(new java.awt.Color(255, 255, 255));
        btnDelAcc.setText("Delete");
        btnDelAcc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelAccActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout OnlineJPanelLayout = new javax.swing.GroupLayout(OnlineJPanel);
        OnlineJPanel.setLayout(OnlineJPanelLayout);
        OnlineJPanelLayout.setHorizontalGroup(
            OnlineJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OnlineJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(OnlineJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(OnlineJPanelLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(btnResetPW)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddAcc, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDelAcc)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(OnlineJPanelLayout.createSequentialGroup()
                        .addGroup(OnlineJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );

        OnlineJPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnDelAcc, btnResetPW});

        OnlineJPanelLayout.setVerticalGroup(
            OnlineJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OnlineJPanelLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(OnlineJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnResetPW, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddAcc, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelAcc, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("tab1", OnlineJPanel);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        tbEditHistory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Action", "Username", "Category", "ID", "Start time", "End time"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbEditHistory.setShowGrid(true);
        jScrollPane1.setViewportView(tbEditHistory);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel1.setText("Account's edit history");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(438, 438, 438)
                .addComponent(jLabel1)
                .addContainerGap(410, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout statusEditLayout = new javax.swing.GroupLayout(statusEdit);
        statusEdit.setLayout(statusEditLayout);
        statusEditLayout.setHorizontalGroup(
            statusEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        statusEditLayout.setVerticalGroup(
            statusEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("tab2", statusEdit);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1160, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 605, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("tab3", jPanel3);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, -40, 1160, 640));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void OnlineMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_OnlineMouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(0);
    }//GEN-LAST:event_OnlineMouseClicked

    private void OnlineMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_OnlineMouseEntered
        // TODO add your handling code here:
        Online.setBorder(BorderFactory.createLineBorder(Color.white, 2));
    }//GEN-LAST:event_OnlineMouseEntered

    private void OnlineMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_OnlineMouseExited
        // TODO add your handling code here:
        Online.setBorder(null);
    }//GEN-LAST:event_OnlineMouseExited

    private void OnlineMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_OnlineMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_OnlineMousePressed

    private void btnResetPWActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetPWActionPerformed
        // TODO add your handling code here:

        int[] selected = tbListAccount.getSelectedRows();
        int countNot = 0;
        for (int i : selected) {
            if (!checkRequest(i)) {
                countNot++;
                JOptionPane.showMessageDialog(this, "Please select accounts with password reset requests.");
            }
        }
        List<String> listReset = new ArrayList<>();
        if (countNot == 0) {
            for (int i : selected) {
                String user = tbListAccount.getValueAt(i, 3).toString();
                accountBO bo = new accountBO();
                boolean changpw = bo.changePassword(user, "123456");
                if (changpw) {
                    listReset.add(user);
                    tbListAccount.setValueAt("", i, 7);
                    setStatusResetPW(user, "");
                }
            }
        }

        for (String string : listReset) {
            ClientSocketModel client = checkConnect(string);
            if (client != null) {
                client.setUsername("client" + serverController.getClientList().size());
                client.sendMessage("Password reset request has been confirmed. Your new password is: 123456");
            }
        }
    }//GEN-LAST:event_btnResetPWActionPerformed

    private void StatusSocketMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_StatusSocketMouseClicked
        // TODO add your handling code here:
        if (this.server == null || server.isClosed()) {
            try {
                server = new ServerSocket(5555);
                JOptionPane.showMessageDialog(this, "Server is running");
                lbStatusSocket.setText("END");
                InetAddress ip = InetAddress.getLocalHost();
                setTitle("Localhost: " + ip.getHostAddress() + " , port: 5555");
                System.out.println("Server is running");
                
                Online.setVisible(true);
                editHistory.setVisible(true);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Server is not running");
                System.out.println("Server is not running");
            }
            serverController = new ServerController(server, this);
        } else {
            if (!server.isClosed()) {
                try {
                    server.close();
                    JOptionPane.showMessageDialog(this, "Server is closed");
                    lbStatusSocket.setText("START");
                    
                    Online.setVisible(false);
                    editHistory.setVisible(false);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Server is not closed");
                    Logger.getLogger(ServerJFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_StatusSocketMouseClicked

    private void StatusSocketMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_StatusSocketMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_StatusSocketMouseEntered

    private void StatusSocketMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_StatusSocketMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_StatusSocketMouseExited

    private void StatusSocketMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_StatusSocketMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_StatusSocketMousePressed

    private void editHistoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editHistoryMouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_editHistoryMouseClicked

    private void editHistoryMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editHistoryMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_editHistoryMouseEntered

    private void editHistoryMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editHistoryMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_editHistoryMouseExited

    private void editHistoryMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editHistoryMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_editHistoryMousePressed

    private void btnAddAccActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddAccActionPerformed
        // TODO add your handling code here:
        AddAccount acc = new AddAccount(this, true);
        acc.setDataAddiition(this);
        acc.setVisible(true);
    }//GEN-LAST:event_btnAddAccActionPerformed

    void updateSTTTable() {
        for (int i = 0; i < tbListAccount.getRowCount(); i++) {
            tbListAccount.setValueAt(i+1, i, 0);
        }
    }
    
    private void btnDelAccActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelAccActionPerformed
        // TODO add your handling code here:
        int[] selected = tbListAccount.getSelectedRows();
        int countNot = 0;

        // Check if any selected account is online
        for (int i : selected) {
            if (checkOnl(i)) {
                countNot++;
                JOptionPane.showMessageDialog(this, "Have an active account.");
            }
        }

        if (countNot == 0) {
            // Iterate over the selected rows in reverse order
            for (int i = selected.length - 1; i >= 0; i--) {
                String user = tbListAccount.getValueAt(selected[i], 1).toString();
                boolean del = accBO.Delete(user);
                if (del) {
                    // Remove the row from the table model
                    modelAccounts.removeRow(selected[i]);
                }
            }

            // Reload the online status after all deletions
            loadTableOnl(modelAccounts);
            updateSTTTable();
        }
    }//GEN-LAST:event_btnDelAccActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ServerJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ServerJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ServerJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ServerJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ServerJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Online;
    private javax.swing.JPanel OnlineJPanel;
    private javax.swing.JPanel StatusSocket;
    private javax.swing.JButton btnAddAcc;
    private javax.swing.JButton btnDelAcc;
    private javax.swing.JButton btnResetPW;
    private javax.swing.JPanel editHistory;
    private javax.swing.JLabel imgFrocery;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lbGrocery;
    private javax.swing.JLabel lbLogin;
    private javax.swing.JLabel lbStatusSocket;
    private javax.swing.JPanel pnMenu;
    private javax.swing.JPanel statusEdit;
    private javax.swing.JTable tbEditHistory;
    private javax.swing.JTable tbListAccount;
    // End of variables declaration//GEN-END:variables
}
