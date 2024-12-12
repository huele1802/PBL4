/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import Controller.AdminController;
import Model.Bean.Account;
import Model.Bean.CBBItem;
import Model.Bean.Custom;
import Model.Bean.Excel;
import Model.Bean.KhachHang;
import Model.Bean.MayTinh;
import Model.Bean.NhaCungCap;
import Model.Bean.search;
import java.awt.Color;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Windows
 */
public class AdminJFrame extends javax.swing.JFrame {
    Socket socket;
    AdminController adminController;

    Custom custom;
    Account account;
    DefaultTableModel modelProduct;
    DefaultTableModel modelSupplier;
    DefaultTableModel modelCustomer;

    List<CBBItem> cbbItem;
    
    Color DefaultColor = new Color(58,62,71);
    Color ClickedColor = new Color(97, 101, 108);
    /**
     * Creates new form AdminJFrame
     */
    public AdminJFrame() {
        initComponents();
    }
    
    public AdminJFrame(Socket soc, DefaultTableModel modelProduct, DefaultTableModel modelSupplier, DefaultTableModel modelCustomer, Account acc) {
        initComponents();
        custom = new Custom();
        this.socket = soc;
//        setTitle(socket.getInetAddress().getHostAddress() + ", port: " + socket.getPort());
        this.modelProduct = modelProduct;
        this.modelSupplier = modelSupplier;
        this.modelCustomer = modelCustomer;
        this.account = acc;
        NameUser.setText(account.getname());
        
        loadTable(tblProduct, modelProduct);
        loadTable(tblCustomer, modelCustomer);
        loadTable(tblSupplier, modelSupplier);
        setInf(account);
        resetForm();

        setCBBItemSupplier();
        
        adminController = new AdminController(socket, this);
    }
    
    public List<CBBItem> getCbbSupplier() {
        List<CBBItem> list = new ArrayList<>();
        for (int i = 0; i < modelSupplier.getRowCount(); i++) {
            list.add(new CBBItem(modelSupplier.getValueAt(i, 0).toString(),
                    modelSupplier.getValueAt(i, 1).toString()));
        }
        return list;
    }
    
    public void setCBBItemSupplier() {
        this.cbbItem = getCbbSupplier();
    }
    
    public void loadTable(JTable table, DefaultTableModel model) {
        custom.CustomTable(table);
        DefaultTableModel currentModel = (DefaultTableModel) table.getModel();
        currentModel.setRowCount(0);

        // Load new data into the table
        for (int i = 0; i < model.getRowCount(); i++) {
            Object[] rowData = new Object[model.getColumnCount()];
            for (int j = 0; j < model.getColumnCount(); j++) {
                rowData[j] = model.getValueAt(i, j);
            }
            currentModel.addRow(rowData);
        }
        custom.CustomTableAfterSetModel(table, currentModel);
    }

    public DefaultTableModel getModelProduct() {
        return modelProduct;
    }

    public void setModelProduct(DefaultTableModel modelProduct) {
        this.modelProduct = modelProduct;
    }
    
    public DefaultTableModel getModelSupplier() {
        return modelSupplier;
    }

    public void setModelSupplier(DefaultTableModel modelSupplier) {
        this.modelSupplier = modelSupplier;
    }

    public DefaultTableModel getModelCustomer() {
        return modelCustomer;
    }

    public void setModelCustomer(DefaultTableModel modelCustomer) {
        this.modelCustomer = modelCustomer;
    }

    public JTable getTblCustomer() {
        return tblCustomer;
    }

    public JTable getTblProduct() {
        return tblProduct;
    }

    public JTable getTblSupplier() {
        return tblSupplier;
    }

    public boolean checkEmptyArray(String[] str) {
        for (String string : str) {
            if (string.isBlank()) {
                return true;
            }
        }
        return false;
    }
    
    public void setNameAccount(String name) {
        account.setname(name);
        NameUser.setText(name);
    }
    
    public void setPWAccount(String pw) {
        account.setpassword(pw);
    }
    
    public Account getAccount() {
        return account;
    }
    
    public void resetText() {
        txtConfirmPW.setText("");
        txtNewPW.setText("");
    }
    
    public void setInf(Account acc) {
        txtFullname.setText(acc.getname());
        txtUsername.setText(acc.getusername());
        txtCurrentPW.setText(acc.getpassword());
    }
    
    public void resetForm() {
        newPW.setVisible(false);
        confirmPW.setVisible(false);
        btnChangePW.setText("Change Password");
    }
    
    public void changeForm() {
        newPW.setVisible(true);
        confirmPW.setVisible(true);
        btnChangePW.setText("Change Password");
    }
    
    public void confirmForm() {
        newPW.setVisible(false);
        confirmPW.setVisible(true);
        btnChangePW.setText("Confirm Password");
    }
    
    boolean checkEditting(JTable table, int[] selectedRows) {
        int column = table.getColumnCount();
        for (int i = 0; i < selectedRows.length; i++) {
            String editby = table.getValueAt(selectedRows[i], column - 1).toString();
            if (!editby.isBlank()) {
                return true;
            }
        }
        return false;
    }
    
    MayTinh getProductSelected(int pos) {
        String maMay = tblProduct.getValueAt(pos, 0).toString();
        String maNhaCungCap = tblProduct.getValueAt(pos, 1).toString();
        String tenmay = tblProduct.getValueAt(pos, 2).toString();
        int soluong = Integer.parseInt(tblProduct.getValueAt(pos, 3).toString());
        float gia = Float.parseFloat(tblProduct.getValueAt(pos, 4).toString());
        String loaimay = tblProduct.getValueAt(pos, 5).toString();
        float kichthuoc = Float.parseFloat(tblProduct.getValueAt(pos, 6).toString());
        String dungluongpin = tblProduct.getValueAt(pos, 7).toString();
        String xuatxu = tblProduct.getValueAt(pos, 8).toString();
        MayTinh mt = new MayTinh(maMay, maNhaCungCap, tenmay, soluong, gia, loaimay, kichthuoc, dungluongpin, xuatxu);
        return mt;
    }
    
    KhachHang getCustomerSelected(int pos) {
        String mancc = tblCustomer.getValueAt(pos, 0).toString();
        String tenncc = tblCustomer.getValueAt(pos, 1).toString();
        String sdt = tblCustomer.getValueAt(pos, 2).toString();
        String diachi = tblCustomer.getValueAt(pos, 3).toString();
        KhachHang mt = new KhachHang(mancc, tenncc, sdt, diachi);
        return mt;
    }
    
    NhaCungCap getSupplierSelected(int pos) {
        String mancc = tblSupplier.getValueAt(pos, 0).toString();
        String tenncc = tblSupplier.getValueAt(pos, 1).toString();
        String sdt = tblSupplier.getValueAt(pos, 2).toString();
        String diachi = tblSupplier.getValueAt(pos, 3).toString();
        NhaCungCap mt = new NhaCungCap(mancc, tenncc, sdt, diachi);
        return mt;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        NavbarMenu = new javax.swing.JPanel();
        SanPham = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        DangXuat = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        NhaCungCap = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        NameUser = new javax.swing.JLabel();
        TaiKhoan1 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        imgFrocery = new javax.swing.JLabel();
        KhachHang = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        pnTittle = new javax.swing.JPanel();
        lbTittle = new javax.swing.JLabel();
        tabMenu = new javax.swing.JTabbedPane();
        ProductJPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProduct = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        btnAddProduct = new javax.swing.JButton();
        btnEditProduct = new javax.swing.JButton();
        btnDeleteProduct = new javax.swing.JButton();
        btnxuatexcel = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        cbbOptionProduct = new javax.swing.JComboBox<>();
        btnResetProduct = new javax.swing.JLabel();
        txtSearchProduct = new javax.swing.JTextField();
        btnSearchProduct = new javax.swing.JButton();
        SupplierJPanel = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSupplier = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        btnAddSupplier = new javax.swing.JButton();
        btnEditSupplier = new javax.swing.JButton();
        btnDeleteSupplier = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        cbbOptionSupplier = new javax.swing.JComboBox<>();
        btnResetSupplier = new javax.swing.JLabel();
        txtSearchSupplier = new javax.swing.JTextField();
        btnSearchSupplier = new javax.swing.JButton();
        CustomerJPanel = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblCustomer = new javax.swing.JTable();
        cbbOptionCustomer = new javax.swing.JComboBox<>();
        btnAddCustomer = new javax.swing.JButton();
        btnEditCustomer = new javax.swing.JButton();
        btnDeleteCustomer = new javax.swing.JButton();
        btnDetail = new javax.swing.JButton();
        txtSearchCustomer = new javax.swing.JTextField();
        btnResetCustomer = new javax.swing.JLabel();
        btnSearchCustomer = new javax.swing.JButton();
        PersonalAccountJPanel = new javax.swing.JPanel();
        Infor2 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        txtUsername = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        txtFullname = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        txtCurrentPW = new javax.swing.JPasswordField();
        newPW = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        txtNewPW = new javax.swing.JPasswordField();
        confirmPW = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        txtConfirmPW = new javax.swing.JPasswordField();
        jPanel15 = new javax.swing.JPanel();
        btnChangePW = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnChangeName = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        NavbarMenu.setBackground(new java.awt.Color(58, 62, 71));
        NavbarMenu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        SanPham.setBackground(new java.awt.Color(58, 62, 71));
        SanPham.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        SanPham.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SanPhamMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                SanPhamMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                SanPhamMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                SanPhamMousePressed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("SF Pro Display", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/box-open (4).png"))); // NOI18N
        jLabel1.setText("  PRODUCT");

        javax.swing.GroupLayout SanPhamLayout = new javax.swing.GroupLayout(SanPham);
        SanPham.setLayout(SanPhamLayout);
        SanPhamLayout.setHorizontalGroup(
            SanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SanPhamLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel1)
                .addContainerGap(58, Short.MAX_VALUE))
        );
        SanPhamLayout.setVerticalGroup(
            SanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SanPhamLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                .addContainerGap())
        );

        NavbarMenu.add(SanPham, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, 240, 60));

        DangXuat.setBackground(new java.awt.Color(58, 62, 71));
        DangXuat.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        DangXuat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DangXuatMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                DangXuatMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                DangXuatMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                DangXuatMousePressed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("SF Pro Display", 1, 20)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/power.png"))); // NOI18N
        jLabel5.setText("  LOG OUT");

        javax.swing.GroupLayout DangXuatLayout = new javax.swing.GroupLayout(DangXuat);
        DangXuat.setLayout(DangXuatLayout);
        DangXuatLayout.setHorizontalGroup(
            DangXuatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DangXuatLayout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jLabel5)
                .addContainerGap(64, Short.MAX_VALUE))
        );
        DangXuatLayout.setVerticalGroup(
            DangXuatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DangXuatLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                .addContainerGap())
        );

        NavbarMenu.add(DangXuat, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 490, 240, 60));

        NhaCungCap.setBackground(new java.awt.Color(58, 62, 71));
        NhaCungCap.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        NhaCungCap.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        NhaCungCap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                NhaCungCapMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                NhaCungCapMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                NhaCungCapMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                NhaCungCapMousePressed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("SF Pro Display", 1, 20)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/truck-loading.png"))); // NOI18N
        jLabel6.setText("  SUPPLIER");

        javax.swing.GroupLayout NhaCungCapLayout = new javax.swing.GroupLayout(NhaCungCap);
        NhaCungCap.setLayout(NhaCungCapLayout);
        NhaCungCapLayout.setHorizontalGroup(
            NhaCungCapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(NhaCungCapLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel6)
                .addContainerGap(57, Short.MAX_VALUE))
        );
        NhaCungCapLayout.setVerticalGroup(
            NhaCungCapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, NhaCungCapLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                .addContainerGap())
        );

        NavbarMenu.add(NhaCungCap, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 310, 240, 60));

        NameUser.setFont(new java.awt.Font("SF Pro Display", 1, 18)); // NOI18N
        NameUser.setForeground(new java.awt.Color(255, 255, 255));
        NameUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        NameUser.setText("ADMIN");
        NameUser.setToolTipText("");
        NavbarMenu.add(NameUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 240, -1));

        TaiKhoan1.setBackground(new java.awt.Color(58, 62, 71));
        TaiKhoan1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        TaiKhoan1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        TaiKhoan1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TaiKhoan1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                TaiKhoan1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                TaiKhoan1MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                TaiKhoan1MousePressed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("SF Pro Display", 1, 20)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/user (1).png"))); // NOI18N
        jLabel12.setText("  ACCOUNT");

        javax.swing.GroupLayout TaiKhoan1Layout = new javax.swing.GroupLayout(TaiKhoan1);
        TaiKhoan1.setLayout(TaiKhoan1Layout);
        TaiKhoan1Layout.setHorizontalGroup(
            TaiKhoan1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TaiKhoan1Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jLabel12)
                .addContainerGap(56, Short.MAX_VALUE))
        );
        TaiKhoan1Layout.setVerticalGroup(
            TaiKhoan1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, TaiKhoan1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                .addContainerGap())
        );

        NavbarMenu.add(TaiKhoan1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 430, 240, 60));

        imgFrocery.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        imgFrocery.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/1488b4.png"))); // NOI18N
        NavbarMenu.add(imgFrocery, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 240, 200));

        KhachHang.setBackground(new java.awt.Color(58, 62, 71));
        KhachHang.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        KhachHang.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        KhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                KhachHangMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                KhachHangMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                KhachHangMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                KhachHangMousePressed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("SF Pro Display", 1, 20)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/review.png"))); // NOI18N
        jLabel13.setText("  CUSTOMER");

        javax.swing.GroupLayout KhachHangLayout = new javax.swing.GroupLayout(KhachHang);
        KhachHang.setLayout(KhachHangLayout);
        KhachHangLayout.setHorizontalGroup(
            KhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(KhachHangLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel13)
                .addContainerGap(42, Short.MAX_VALUE))
        );
        KhachHangLayout.setVerticalGroup(
            KhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, KhachHangLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                .addContainerGap())
        );

        NavbarMenu.add(KhachHang, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 370, 240, 60));

        getContentPane().add(NavbarMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 240, 750));

        pnTittle.setBackground(new java.awt.Color(58, 62, 71));
        pnTittle.setPreferredSize(new java.awt.Dimension(1200, 60));

        lbTittle.setBackground(new java.awt.Color(58, 62, 71));
        lbTittle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lbTittle.setForeground(new java.awt.Color(255, 255, 255));
        lbTittle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTittle.setText("Computer store management software");
        lbTittle.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lbTittle.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                lbTittleMouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                lbTittleMouseMoved(evt);
            }
        });

        javax.swing.GroupLayout pnTittleLayout = new javax.swing.GroupLayout(pnTittle);
        pnTittle.setLayout(pnTittleLayout);
        pnTittleLayout.setHorizontalGroup(
            pnTittleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbTittle, javax.swing.GroupLayout.DEFAULT_SIZE, 1260, Short.MAX_VALUE)
        );
        pnTittleLayout.setVerticalGroup(
            pnTittleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbTittle, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        getContentPane().add(pnTittle, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 0, 1260, 50));

        ProductJPanel.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setAutoscrolls(true);

        tblProduct.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tblProduct.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tblProduct.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "SupplierID", "Name", "Quantity", "Price", "Type", "Size", "Capacity", "Origin", "Edit By"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Float.class, java.lang.Object.class, java.lang.Float.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblProduct.setRowHeight(25);
        tblProduct.setSelectionBackground(new java.awt.Color(0, 153, 0));
        tblProduct.setShowGrid(true);
        jScrollPane1.setViewportView(tblProduct);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1236, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 609, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Function", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 2, 18))); // NOI18N
        jPanel3.setLayout(new java.awt.GridLayout(1, 0));

        btnAddProduct.setBackground(new java.awt.Color(54, 164, 255));
        btnAddProduct.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        btnAddProduct.setForeground(new java.awt.Color(255, 255, 255));
        btnAddProduct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/plus (1).png"))); // NOI18N
        btnAddProduct.setBorder(null);
        btnAddProduct.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnAddProduct.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAddProduct.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAddProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddProductActionPerformed(evt);
            }
        });
        jPanel3.add(btnAddProduct);

        btnEditProduct.setBackground(new java.awt.Color(54, 164, 255));
        btnEditProduct.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        btnEditProduct.setForeground(new java.awt.Color(255, 255, 255));
        btnEditProduct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/pencil.png"))); // NOI18N
        btnEditProduct.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEditProduct.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEditProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditProductActionPerformed(evt);
            }
        });
        jPanel3.add(btnEditProduct);

        btnDeleteProduct.setBackground(new java.awt.Color(54, 164, 255));
        btnDeleteProduct.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        btnDeleteProduct.setForeground(new java.awt.Color(255, 255, 255));
        btnDeleteProduct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/trash.png"))); // NOI18N
        btnDeleteProduct.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDeleteProduct.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDeleteProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteProductActionPerformed(evt);
            }
        });
        jPanel3.add(btnDeleteProduct);

        btnxuatexcel.setBackground(new java.awt.Color(54, 164, 255));
        btnxuatexcel.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        btnxuatexcel.setForeground(new java.awt.Color(255, 255, 255));
        btnxuatexcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/file-export.png"))); // NOI18N
        btnxuatexcel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnxuatexcel.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnxuatexcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnxuatexcelActionPerformed(evt);
            }
        });
        jPanel3.add(btnxuatexcel);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Search", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 2, 18))); // NOI18N

        cbbOptionProduct.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cbbOptionProduct.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "ID", "Supplier ID", "Name", "Quantity", "Price", "Type", "Size", "Capacity", "Origin", "" }));
        cbbOptionProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbOptionProductActionPerformed(evt);
            }
        });
        cbbOptionProduct.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                cbbOptionProductPropertyChange(evt);
            }
        });

        btnResetProduct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/refresh.png"))); // NOI18N
        btnResetProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnResetProductMouseClicked(evt);
            }
        });

        txtSearchProduct.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchProductKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchProductKeyReleased(evt);
            }
        });

        btnSearchProduct.setBackground(new java.awt.Color(54, 164, 255));
        btnSearchProduct.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSearchProduct.setForeground(new java.awt.Color(255, 255, 255));
        btnSearchProduct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/search.png"))); // NOI18N
        btnSearchProduct.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSearchProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchProductActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbbOptionProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtSearchProduct)
                .addGap(12, 12, 12)
                .addComponent(btnResetProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSearchProduct)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbbOptionProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtSearchProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnResetProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearchProduct))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnResetProduct, btnSearchProduct, cbbOptionProduct, txtSearchProduct});

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jPanel3, jPanel4});

        javax.swing.GroupLayout ProductJPanelLayout = new javax.swing.GroupLayout(ProductJPanel);
        ProductJPanel.setLayout(ProductJPanelLayout);
        ProductJPanelLayout.setHorizontalGroup(
            ProductJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        ProductJPanelLayout.setVerticalGroup(
            ProductJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        tabMenu.addTab("tab1", ProductJPanel);

        SupplierJPanel.setBackground(new java.awt.Color(255, 255, 255));
        SupplierJPanel.setPreferredSize(new java.awt.Dimension(1235, 705));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setPreferredSize(new java.awt.Dimension(1235, 705));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setAutoscrolls(true);

        jScrollPane2.setBorder(null);

        tblSupplier.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tblSupplier.setFont(new java.awt.Font("Segoe UI Semibold", 0, 15)); // NOI18N
        tblSupplier.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Phone Number", "Address", "Edit By"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSupplier.setRowHeight(25);
        tblSupplier.setSelectionBackground(new java.awt.Color(255, 153, 153));
        tblSupplier.setShowGrid(true);
        jScrollPane2.setViewportView(tblSupplier);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1236, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 604, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Function", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 2, 18))); // NOI18N
        jPanel7.setLayout(new java.awt.GridLayout(1, 0));

        btnAddSupplier.setBackground(new java.awt.Color(54, 164, 255));
        btnAddSupplier.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAddSupplier.setForeground(new java.awt.Color(255, 255, 255));
        btnAddSupplier.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/plus (1).png"))); // NOI18N
        btnAddSupplier.setBorder(null);
        btnAddSupplier.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnAddSupplier.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAddSupplier.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAddSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddSupplierActionPerformed(evt);
            }
        });
        jPanel7.add(btnAddSupplier);

        btnEditSupplier.setBackground(new java.awt.Color(54, 164, 255));
        btnEditSupplier.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnEditSupplier.setForeground(new java.awt.Color(255, 255, 255));
        btnEditSupplier.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/pencil.png"))); // NOI18N
        btnEditSupplier.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEditSupplier.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEditSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditSupplierActionPerformed(evt);
            }
        });
        jPanel7.add(btnEditSupplier);

        btnDeleteSupplier.setBackground(new java.awt.Color(54, 164, 255));
        btnDeleteSupplier.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnDeleteSupplier.setForeground(new java.awt.Color(255, 255, 255));
        btnDeleteSupplier.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/trash.png"))); // NOI18N
        btnDeleteSupplier.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDeleteSupplier.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDeleteSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteSupplierActionPerformed(evt);
            }
        });
        jPanel7.add(btnDeleteSupplier);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Search", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 2, 18))); // NOI18N

        cbbOptionSupplier.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "ID", "Name", "Phone Number", "Address" }));
        cbbOptionSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbOptionSupplierActionPerformed(evt);
            }
        });
        cbbOptionSupplier.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                cbbOptionSupplierPropertyChange(evt);
            }
        });

        btnResetSupplier.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/refresh.png"))); // NOI18N
        btnResetSupplier.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnResetSupplierMouseClicked(evt);
            }
        });

        txtSearchSupplier.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchSupplierKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchSupplierKeyReleased(evt);
            }
        });

        btnSearchSupplier.setBackground(new java.awt.Color(54, 164, 255));
        btnSearchSupplier.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSearchSupplier.setForeground(new java.awt.Color(255, 255, 255));
        btnSearchSupplier.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/search.png"))); // NOI18N
        btnSearchSupplier.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSearchSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchSupplierActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbbOptionSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSearchSupplier)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnResetSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSearchSupplier)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSearchSupplier)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbbOptionSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtSearchSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnResetSupplier, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel8Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnResetSupplier, btnSearchSupplier, cbbOptionSupplier, txtSearchSupplier});

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jPanel7, jPanel8});

        javax.swing.GroupLayout SupplierJPanelLayout = new javax.swing.GroupLayout(SupplierJPanel);
        SupplierJPanel.setLayout(SupplierJPanelLayout);
        SupplierJPanelLayout.setHorizontalGroup(
            SupplierJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 1260, Short.MAX_VALUE)
        );
        SupplierJPanelLayout.setVerticalGroup(
            SupplierJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        tabMenu.addTab("tab2", SupplierJPanel);

        CustomerJPanel.setBackground(new java.awt.Color(54, 164, 255));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/bannerNewYear1260.png"))); // NOI18N

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setAutoscrolls(true);

        tblCustomer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tblCustomer.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tblCustomer.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Phone Number", "Address", "Total", "Edit By"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Float.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCustomer.setRowHeight(25);
        tblCustomer.setSelectionBackground(new java.awt.Color(0, 153, 0));
        tblCustomer.setShowGrid(true);
        jScrollPane3.setViewportView(tblCustomer);

        cbbOptionCustomer.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "ID", "Name", "Phone Number", "Address" }));
        cbbOptionCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbOptionCustomerActionPerformed(evt);
            }
        });
        cbbOptionCustomer.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                cbbOptionCustomerPropertyChange(evt);
            }
        });

        btnAddCustomer.setBackground(new java.awt.Color(54, 164, 255));
        btnAddCustomer.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAddCustomer.setForeground(new java.awt.Color(255, 255, 255));
        btnAddCustomer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/plus (1).png"))); // NOI18N
        btnAddCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddCustomerActionPerformed(evt);
            }
        });

        btnEditCustomer.setBackground(new java.awt.Color(54, 164, 255));
        btnEditCustomer.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnEditCustomer.setForeground(new java.awt.Color(255, 255, 255));
        btnEditCustomer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/pencil.png"))); // NOI18N
        btnEditCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditCustomerActionPerformed(evt);
            }
        });

        btnDeleteCustomer.setBackground(new java.awt.Color(54, 164, 255));
        btnDeleteCustomer.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnDeleteCustomer.setForeground(new java.awt.Color(255, 255, 255));
        btnDeleteCustomer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/trash.png"))); // NOI18N
        btnDeleteCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteCustomerActionPerformed(evt);
            }
        });

        btnDetail.setBackground(new java.awt.Color(54, 164, 255));
        btnDetail.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnDetail.setForeground(new java.awt.Color(255, 255, 255));
        btnDetail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/file-circle-info.png"))); // NOI18N
        btnDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetailActionPerformed(evt);
            }
        });

        txtSearchCustomer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchCustomerKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchCustomerKeyReleased(evt);
            }
        });

        btnResetCustomer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/refresh.png"))); // NOI18N
        btnResetCustomer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnResetCustomerMouseClicked(evt);
            }
        });

        btnSearchCustomer.setBackground(new java.awt.Color(54, 164, 255));
        btnSearchCustomer.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSearchCustomer.setForeground(new java.awt.Color(255, 255, 255));
        btnSearchCustomer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/search.png"))); // NOI18N
        btnSearchCustomer.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSearchCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchCustomerCustomerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(btnAddCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeleteCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cbbOptionCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSearchCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 454, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnResetCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSearchCustomer)))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnResetCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDeleteCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbbOptionCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtSearchCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAddCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnSearchCustomer))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 469, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel10Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnAddCustomer, btnDeleteCustomer, btnDetail, btnEditCustomer, btnResetCustomer, btnSearchCustomer, cbbOptionCustomer, txtSearchCustomer});

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout CustomerJPanelLayout = new javax.swing.GroupLayout(CustomerJPanel);
        CustomerJPanel.setLayout(CustomerJPanelLayout);
        CustomerJPanelLayout.setHorizontalGroup(
            CustomerJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        CustomerJPanelLayout.setVerticalGroup(
            CustomerJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        tabMenu.addTab("tab3", CustomerJPanel);

        PersonalAccountJPanel.setBackground(new java.awt.Color(255, 255, 255));

        Infor2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));

        txtUsername.setEditable(false);
        txtUsername.setBackground(new java.awt.Color(229, 229, 229));
        txtUsername.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jLabel14.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        jLabel14.setText("Username:");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));

        txtFullname.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jLabel15.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        jLabel15.setText("Fullname:");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtFullname, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFullname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        jLabel16.setText("Current Password:");

        txtCurrentPW.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(txtCurrentPW, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtCurrentPW, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        newPW.setBackground(new java.awt.Color(255, 255, 255));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        jLabel17.setText("New Password:");

        txtNewPW.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        javax.swing.GroupLayout newPWLayout = new javax.swing.GroupLayout(newPW);
        newPW.setLayout(newPWLayout);
        newPWLayout.setHorizontalGroup(
            newPWLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(newPWLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(newPWLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(newPWLayout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(0, 202, Short.MAX_VALUE))
                    .addComponent(txtNewPW))
                .addContainerGap())
        );
        newPWLayout.setVerticalGroup(
            newPWLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, newPWLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtNewPW, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        confirmPW.setBackground(new java.awt.Color(255, 255, 255));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        jLabel18.setText("Confirm Password:");

        txtConfirmPW.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        javax.swing.GroupLayout confirmPWLayout = new javax.swing.GroupLayout(confirmPW);
        confirmPW.setLayout(confirmPWLayout);
        confirmPWLayout.setHorizontalGroup(
            confirmPWLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(confirmPWLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(confirmPWLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(confirmPWLayout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addGap(0, 173, Short.MAX_VALUE))
                    .addComponent(txtConfirmPW))
                .addContainerGap())
        );
        confirmPWLayout.setVerticalGroup(
            confirmPWLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, confirmPWLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtConfirmPW, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));

        btnChangePW.setBackground(new java.awt.Color(54, 164, 255));
        btnChangePW.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnChangePW.setForeground(new java.awt.Color(255, 255, 255));
        btnChangePW.setText("Change Password");
        btnChangePW.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangePWActionPerformed(evt);
            }
        });

        btnCancel.setBackground(new java.awt.Color(54, 164, 255));
        btnCancel.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnCancel.setForeground(new java.awt.Color(255, 255, 255));
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnChangeName.setBackground(new java.awt.Color(54, 164, 255));
        btnChangeName.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnChangeName.setForeground(new java.awt.Color(255, 255, 255));
        btnChangeName.setText("Change Name");
        btnChangeName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangeNameActionPerformed(evt);
            }
        });

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnChangePW, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                        .addComponent(btnChangeName, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnChangePW, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnChangeName, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel10.setFont(new java.awt.Font("Harrington", 1, 30)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(54, 164, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Personal account information");
        jLabel10.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(104, 104, 104)
                        .addComponent(jLabel10))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(newPW, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(confirmPW, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap(70, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel10)
                .addGap(25, 25, 25)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(newPW, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(confirmPW, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout Infor2Layout = new javax.swing.GroupLayout(Infor2);
        Infor2.setLayout(Infor2Layout);
        Infor2Layout.setHorizontalGroup(
            Infor2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Infor2Layout.createSequentialGroup()
                .addGap(296, 296, 296)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(306, Short.MAX_VALUE))
        );
        Infor2Layout.setVerticalGroup(
            Infor2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Infor2Layout.createSequentialGroup()
                .addGap(107, 107, 107)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(99, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout PersonalAccountJPanelLayout = new javax.swing.GroupLayout(PersonalAccountJPanel);
        PersonalAccountJPanel.setLayout(PersonalAccountJPanelLayout);
        PersonalAccountJPanelLayout.setHorizontalGroup(
            PersonalAccountJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Infor2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        PersonalAccountJPanelLayout.setVerticalGroup(
            PersonalAccountJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Infor2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        tabMenu.addTab("tab4", PersonalAccountJPanel);

        getContentPane().add(tabMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 10, 1260, 740));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SanPhamMouseClicked
        // TODO add your handling code here:
        tabMenu.setSelectedIndex(0);
    }//GEN-LAST:event_SanPhamMouseClicked

    private void SanPhamMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SanPhamMouseEntered
        // TODO add your handling code here:
        SanPham.setBorder(BorderFactory.createLineBorder(Color.white, 2));
    }//GEN-LAST:event_SanPhamMouseEntered

    private void SanPhamMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SanPhamMouseExited
        // TODO add your handling code here:
        SanPham.setBorder(null);
    }//GEN-LAST:event_SanPhamMouseExited

    private void SanPhamMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SanPhamMousePressed
        // TODO add your handling code here:
        SanPham.setBackground(ClickedColor);
        NhaCungCap.setBackground(DefaultColor);
        KhachHang.setBackground(DefaultColor);
        TaiKhoan1.setBackground(DefaultColor);
    }//GEN-LAST:event_SanPhamMousePressed

    private void DangXuatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DangXuatMouseClicked
        // TODO add your handling code here:
        int relly = JOptionPane.showConfirmDialog(null,"Do you want to exit the program?","Confirmation",JOptionPane.YES_NO_OPTION);
        if (relly == JOptionPane.YES_OPTION) {
            adminController.sendMessage("Close");
            try {
                socket.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            this.dispose();
        } else {
//            DangXuat.setBackground(DefaultColor);
        }
    }//GEN-LAST:event_DangXuatMouseClicked

    private void DangXuatMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DangXuatMouseEntered
        // TODO add your handling code here:
        DangXuat.setBorder(BorderFactory.createLineBorder(Color.white, 2));
    }//GEN-LAST:event_DangXuatMouseEntered

    private void DangXuatMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DangXuatMouseExited
        // TODO add your handling code here:
        DangXuat.setBorder(null);
    }//GEN-LAST:event_DangXuatMouseExited

    private void DangXuatMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DangXuatMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DangXuatMousePressed

    private void NhaCungCapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_NhaCungCapMouseClicked
        // TODO add your handling code here:
        tabMenu.setSelectedIndex(1);
    }//GEN-LAST:event_NhaCungCapMouseClicked

    private void NhaCungCapMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_NhaCungCapMouseEntered
        // TODO add your handling code here:
        NhaCungCap.setBorder(BorderFactory.createLineBorder(Color.white, 2));
    }//GEN-LAST:event_NhaCungCapMouseEntered

    private void NhaCungCapMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_NhaCungCapMouseExited
        // TODO add your handling code here:
        NhaCungCap.setBorder(null);
    }//GEN-LAST:event_NhaCungCapMouseExited

    private void NhaCungCapMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_NhaCungCapMousePressed
        // TODO add your handling code here:
        SanPham.setBackground(DefaultColor);
        NhaCungCap.setBackground(ClickedColor);
        KhachHang.setBackground(DefaultColor);
        TaiKhoan1.setBackground(DefaultColor);
    }//GEN-LAST:event_NhaCungCapMousePressed

    private void TaiKhoan1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TaiKhoan1MouseClicked
        // TODO add your handling code here:
        tabMenu.setSelectedIndex(3);
    }//GEN-LAST:event_TaiKhoan1MouseClicked

    private void TaiKhoan1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TaiKhoan1MouseEntered
        // TODO add your handling code here:
        TaiKhoan1.setBorder(BorderFactory.createLineBorder(Color.white, 2));
    }//GEN-LAST:event_TaiKhoan1MouseEntered

    private void TaiKhoan1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TaiKhoan1MouseExited
        // TODO add your handling code here:
        TaiKhoan1.setBorder(null);
    }//GEN-LAST:event_TaiKhoan1MouseExited

    private void TaiKhoan1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TaiKhoan1MousePressed
        // TODO add your handling code here:
        SanPham.setBackground(DefaultColor);
        NhaCungCap.setBackground(DefaultColor);
        KhachHang.setBackground(DefaultColor);
        TaiKhoan1.setBackground(ClickedColor);
    }//GEN-LAST:event_TaiKhoan1MousePressed

    private void KhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KhachHangMouseClicked
        // TODO add your handling code here:
        tabMenu.setSelectedIndex(2);
    }//GEN-LAST:event_KhachHangMouseClicked

    private void KhachHangMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KhachHangMouseEntered
        // TODO add your handling code here:
        KhachHang.setBorder(BorderFactory.createLineBorder(Color.white, 2));
    }//GEN-LAST:event_KhachHangMouseEntered

    private void KhachHangMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KhachHangMouseExited
        // TODO add your handling code here:
        KhachHang.setBorder(null);
    }//GEN-LAST:event_KhachHangMouseExited

    private void KhachHangMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KhachHangMousePressed
        // TODO add your handling code here:
        SanPham.setBackground(DefaultColor);
        NhaCungCap.setBackground(DefaultColor);
        KhachHang.setBackground(ClickedColor);
        TaiKhoan1.setBackground(DefaultColor);
    }//GEN-LAST:event_KhachHangMousePressed

    private void btnAddProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddProductActionPerformed
        // TODO add your handling code here:
        AddProduct a = new AddProduct(this, true, adminController, cbbItem);
        a.setVisible(true);
    }//GEN-LAST:event_btnAddProductActionPerformed

    private void btnEditProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditProductActionPerformed
        // TODO add your handling code here:
        if (tblProduct.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Please select the product you want to edit");
        } else if (tblProduct.getSelectedRowCount() > 1) {
            JOptionPane.showMessageDialog(this, "You can only edit one product at a time.");
        } else {
            int rowSelected = tblProduct.getSelectedRow();
            String editby = tblProduct.getValueAt(rowSelected, 9).toString();
            if (editby.isBlank()) {
                MayTinh mt = getProductSelected(tblProduct.getSelectedRow());
                UpdateProduct a = new UpdateProduct(this, true, adminController, cbbItem, mt);
                adminController.sendMessage("isEdittingProduct;" + tblProduct.getValueAt(tblProduct.getSelectedRow(), 0));
                a.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, editby + " is currently editing this product.");
            }
        }
    }//GEN-LAST:event_btnEditProductActionPerformed

    private void btnDeleteProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteProductActionPerformed
        // TODO add your handling code here:
        int[] selectedRows = tblProduct.getSelectedRows();
        if (selectedRows.length <= 0) {
            JOptionPane.showMessageDialog(this, "You haven't selected any products to delete.");
        } else {
            if (checkEditting(tblProduct, selectedRows)) {
                JOptionPane.showMessageDialog(this, "Some products are currently being updated.");
            } else {
                int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete these products?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    String listMaMay = "";
                    for (int i = 0; i < selectedRows.length; i++) {
                        listMaMay += tblProduct.getValueAt(selectedRows[i], 0) + ",";
                    }
                    adminController.sendMessage("deleteProduct;" + listMaMay);
                }
            }
        }
    }//GEN-LAST:event_btnDeleteProductActionPerformed

    private void btnxuatexcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnxuatexcelActionPerformed
        // TODO add your handling code here:
        Excel ex = new Excel();
        ex.xuatExcel(this, tblProduct);
    }//GEN-LAST:event_btnxuatexcelActionPerformed

    private void cbbOptionProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbOptionProductActionPerformed

    }//GEN-LAST:event_cbbOptionProductActionPerformed

    private void cbbOptionProductPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_cbbOptionProductPropertyChange

    }//GEN-LAST:event_cbbOptionProductPropertyChange

    private void btnResetProductMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnResetProductMouseClicked
        // TODO add your handling code here:
        loadTable(tblProduct, modelProduct);
    }//GEN-LAST:event_btnResetProductMouseClicked

    private void txtSearchProductKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchProductKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchProductKeyPressed

    private void txtSearchProductKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchProductKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchProductKeyReleased

    private void btnSearchProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchProductActionPerformed
        // TODO add your handling code here:
        String luachon = (String) cbbOptionProduct.getSelectedItem();
        String searchContent = txtSearchProduct.getText();

        search s = new search();
        DefaultTableModel model = s.searchProduct(modelProduct, luachon, searchContent);
        loadTable(tblProduct, model);
    }//GEN-LAST:event_btnSearchProductActionPerformed

    private void lbTittleMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbTittleMouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_lbTittleMouseDragged

    private void lbTittleMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbTittleMouseMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_lbTittleMouseMoved

    private void btnAddSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddSupplierActionPerformed
        // TODO add your handling code here:
        AddNhaCungCap a = new AddNhaCungCap(this, true, adminController);
        a.setVisible(true);
    }//GEN-LAST:event_btnAddSupplierActionPerformed

    private void btnEditSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditSupplierActionPerformed
        // TODO add your handling code here:
        if (tblSupplier.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Please select the supplier you want to edit.");
        } else if (tblSupplier.getSelectedRowCount() > 1) {
            JOptionPane.showMessageDialog(this, "You can only edit one supplier at a time.");
        } else {
            int rowSelected = tblSupplier.getSelectedRow();
            String editby = tblSupplier.getValueAt(rowSelected, 4).toString();
            if (editby.isBlank()) {
                NhaCungCap ncc = getSupplierSelected(tblSupplier.getSelectedRow());
                UpdateNhaCungCap a = new UpdateNhaCungCap(this, true, adminController, ncc);
                adminController.sendMessage("isEdittingSupplier;" + tblSupplier.getValueAt(tblSupplier.getSelectedRow(), 0));
                a.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, editby + " is currently editing this supplier.");
            }
        }
    }//GEN-LAST:event_btnEditSupplierActionPerformed

    private void btnDeleteSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteSupplierActionPerformed
        // TODO add your handling code here:
        int[] selectedRows = tblSupplier.getSelectedRows();
        if (selectedRows.length <= 0) {
            JOptionPane.showMessageDialog(this, "You haven't selected any suppliers to delete.");
        } else {
            if (checkEditting(tblSupplier, selectedRows)) {
                JOptionPane.showMessageDialog(this, "Some suppliers are currently being updated.");
            } else {
                int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete these suppliers?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    String listMaMay = "";
                    for (int i = 0; i < selectedRows.length; i++) {
                        listMaMay += tblSupplier.getValueAt(selectedRows[i], 0) + ",";
                    }

                    adminController.sendMessage("deleteSupplier;" + listMaMay);
                }
            }
        }
    }//GEN-LAST:event_btnDeleteSupplierActionPerformed

    private void cbbOptionSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbOptionSupplierActionPerformed

    }//GEN-LAST:event_cbbOptionSupplierActionPerformed

    private void cbbOptionSupplierPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_cbbOptionSupplierPropertyChange

    }//GEN-LAST:event_cbbOptionSupplierPropertyChange

    private void btnResetSupplierMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnResetSupplierMouseClicked
        // TODO add your handling code here:
        loadTable(tblSupplier, modelSupplier);
    }//GEN-LAST:event_btnResetSupplierMouseClicked

    private void txtSearchSupplierKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchSupplierKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchSupplierKeyPressed

    private void txtSearchSupplierKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchSupplierKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchSupplierKeyReleased

    private void btnSearchSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchSupplierActionPerformed
        // TODO add your handling code here:
        String luachon = (String) cbbOptionSupplier.getSelectedItem();
        String searchContent = txtSearchSupplier.getText();

        search s = new search();
        DefaultTableModel model = s.searchSupplier(modelSupplier, luachon, searchContent);
        loadTable(tblSupplier, model);
    }//GEN-LAST:event_btnSearchSupplierActionPerformed

    private void btnResetCustomerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnResetCustomerMouseClicked
        // TODO add your handling code here:
        loadTable(tblCustomer, modelCustomer);
    }//GEN-LAST:event_btnResetCustomerMouseClicked

    private void cbbOptionCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbOptionCustomerActionPerformed

    }//GEN-LAST:event_cbbOptionCustomerActionPerformed

    private void cbbOptionCustomerPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_cbbOptionCustomerPropertyChange

    }//GEN-LAST:event_cbbOptionCustomerPropertyChange

    private void txtSearchCustomerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchCustomerKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchCustomerKeyPressed

    private void txtSearchCustomerKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchCustomerKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchCustomerKeyReleased

    private void btnSearchCustomerCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchCustomerCustomerActionPerformed
        // TODO add your handling code here:
        String luachon = (String) cbbOptionCustomer.getSelectedItem();
        String searchContent = txtSearchCustomer.getText();

        search s = new search();
        DefaultTableModel model = s.searchCustomer(modelCustomer, luachon, searchContent);
        loadTable(tblCustomer, model);
    }//GEN-LAST:event_btnSearchCustomerCustomerActionPerformed

    private void btnDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetailActionPerformed
        // TODO add your handling code here:
        if (tblCustomer.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Please select a customer to view details.");
        } else if (tblCustomer.getSelectedRowCount() > 1) {
            JOptionPane.showMessageDialog(this, "You can only view details for one customer at a time.");
        } else {
            float total = Float.parseFloat(tblCustomer.getValueAt(tblCustomer.getSelectedRow(), 4).toString());
            if (total == 0) {
                int resp = JOptionPane.showConfirmDialog(this, "This customer hasn't purchased any products. Do you want to add products?", "Notification", JOptionPane.YES_NO_OPTION);
                if (resp == JOptionPane.YES_OPTION) {
                    String idKH = tblCustomer.getValueAt(tblCustomer.getSelectedRow(), 0).toString();
                    String nameKH = tblCustomer.getValueAt(tblCustomer.getSelectedRow(), 1).toString();
                    AddDetail ad = new AddDetail(this, true, adminController,modelProduct, idKH, nameKH);
                    ad.setVisible(true);
                }
            }
            else
            adminController.sendMessage("DetailBills;" + tblCustomer.getValueAt(tblCustomer.getSelectedRow(), 0));
        }
    }//GEN-LAST:event_btnDetailActionPerformed

    private void btnAddCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddCustomerActionPerformed
        // TODO add your handling code here:
        AddCustomer a = new AddCustomer(this, true, adminController);
        a.setVisible(true);
    }//GEN-LAST:event_btnAddCustomerActionPerformed

    private void btnEditCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditCustomerActionPerformed
        // TODO add your handling code here:
        if (tblCustomer.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Please select the customer you want to edit.");
        } else if (tblCustomer.getSelectedRowCount() > 1) {
            JOptionPane.showMessageDialog(this, "You can only edit one customer at a time.");
        } else {
            int rowSelected = tblCustomer.getSelectedRow();
            String editby = tblCustomer.getValueAt(rowSelected, 5).toString();
            if (editby.isBlank()) {
                KhachHang ncc = getCustomerSelected(tblCustomer.getSelectedRow());
                UpdateCustomer a = new UpdateCustomer(this, true, adminController, ncc);
                adminController.sendMessage("isEdittingCustomer;" + tblCustomer.getValueAt(tblCustomer.getSelectedRow(), 0));
                a.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, editby + " is currently editing this customer.");
            }
        }
    }//GEN-LAST:event_btnEditCustomerActionPerformed

    private void btnDeleteCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteCustomerActionPerformed
        // TODO add your handling code here:
        int[] selectedRows = tblCustomer.getSelectedRows();
        if (selectedRows.length <= 0) {
            JOptionPane.showMessageDialog(this, "You haven't selected any customers to delete.");
        } else {
            if (checkEditting(tblCustomer, selectedRows)) {
                JOptionPane.showMessageDialog(this, "Some customers are currently being updated.");
            } else {
                int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete these customers?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    String listMaMay = "";
                    for (int i = 0; i < selectedRows.length; i++) {
                        listMaMay += tblCustomer.getValueAt(selectedRows[i], 0) + ",";
                    }

                    adminController.sendMessage("deleteCustomer;" + listMaMay);
                }
            }
        }
    }//GEN-LAST:event_btnDeleteCustomerActionPerformed

    private void btnChangePWActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangePWActionPerformed
        // TODO add your handling code here:
        if (newPW.isVisible() && confirmPW.isVisible()) {
            char[] confirmPW = txtConfirmPW.getPassword();
            char[] newPW = txtNewPW.getPassword();

            // Sử dụng Arrays.equals để so sánh giá trị của hai mảng char
            if (Arrays.equals(confirmPW, newPW)) {
                String password = new String(newPW);
                adminController.sendMessage("changePW;" + txtUsername.getText() + ";" + password);
            } else {
                JOptionPane.showMessageDialog(this, "New password and Confirmation password don't match.");
            }
        } else if (confirmPW.isVisible()) {
            if (txtConfirmPW.getText().isBlank()) {
                JOptionPane.showMessageDialog(this, "Confirmation password has not been entered");
                resetText();
            } else {
                char[] currentPassword = txtCurrentPW.getPassword();
                char[] confirmPassword = txtConfirmPW.getPassword();

                // Sử dụng Arrays.equals để so sánh giá trị của hai mảng char
                if (Arrays.equals(currentPassword, confirmPassword)) {
                    resetText();
                    changeForm();
                } else {
                    JOptionPane.showMessageDialog(this, "Confirmation password is incorrect");
                    resetText();
                }
            }
        } else {
            confirmForm();
        }
    }//GEN-LAST:event_btnChangePWActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        resetText();
        resetForm();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnChangeNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangeNameActionPerformed
        // TODO add your handling code here:
        adminController.sendMessage("changeName;" + txtUsername.getText() + ";" + txtFullname.getText());
        resetForm();
    }//GEN-LAST:event_btnChangeNameActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        int resp = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Exit?", JOptionPane.YES_NO_OPTION);
        if (resp == JOptionPane.YES_OPTION) {
            adminController.sendMessage("Close");
            try {
                socket.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        } else {
            this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        }
    }//GEN-LAST:event_formWindowClosing

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
            java.util.logging.Logger.getLogger(AdminJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel CustomerJPanel;
    private javax.swing.JPanel DangXuat;
    private javax.swing.JPanel Infor2;
    private javax.swing.JPanel KhachHang;
    private javax.swing.JLabel NameUser;
    private javax.swing.JPanel NavbarMenu;
    private javax.swing.JPanel NhaCungCap;
    private javax.swing.JPanel PersonalAccountJPanel;
    private javax.swing.JPanel ProductJPanel;
    private javax.swing.JPanel SanPham;
    private javax.swing.JPanel SupplierJPanel;
    private javax.swing.JPanel TaiKhoan1;
    private javax.swing.JButton btnAddCustomer;
    private javax.swing.JButton btnAddProduct;
    private javax.swing.JButton btnAddSupplier;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnChangeName;
    private javax.swing.JButton btnChangePW;
    private javax.swing.JButton btnDeleteCustomer;
    private javax.swing.JButton btnDeleteProduct;
    private javax.swing.JButton btnDeleteSupplier;
    private javax.swing.JButton btnDetail;
    private javax.swing.JButton btnEditCustomer;
    private javax.swing.JButton btnEditProduct;
    private javax.swing.JButton btnEditSupplier;
    private javax.swing.JLabel btnResetCustomer;
    private javax.swing.JLabel btnResetProduct;
    private javax.swing.JLabel btnResetSupplier;
    private javax.swing.JButton btnSearchCustomer;
    private javax.swing.JButton btnSearchProduct;
    private javax.swing.JButton btnSearchSupplier;
    private javax.swing.JButton btnxuatexcel;
    private javax.swing.JComboBox<String> cbbOptionCustomer;
    private javax.swing.JComboBox<String> cbbOptionProduct;
    private javax.swing.JComboBox<String> cbbOptionSupplier;
    private javax.swing.JPanel confirmPW;
    private javax.swing.JLabel imgFrocery;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lbTittle;
    private javax.swing.JPanel newPW;
    private javax.swing.JPanel pnTittle;
    private javax.swing.JTabbedPane tabMenu;
    private javax.swing.JTable tblCustomer;
    private javax.swing.JTable tblProduct;
    private javax.swing.JTable tblSupplier;
    private javax.swing.JPasswordField txtConfirmPW;
    private javax.swing.JPasswordField txtCurrentPW;
    private javax.swing.JTextField txtFullname;
    private javax.swing.JPasswordField txtNewPW;
    private javax.swing.JTextField txtSearchCustomer;
    private javax.swing.JTextField txtSearchProduct;
    private javax.swing.JTextField txtSearchSupplier;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
