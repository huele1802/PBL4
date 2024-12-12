/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Bean.serialization;
import Model.Bean.ClientSocketModel;
import Model.Bean.KhachHang;
import Model.Bean.MayTinh;
import Model.Bean.NhaCungCap;
import Model.Bean.support;
import View.AdminJFrame;
import View.DetailForm;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Windows
 */
public class AdminController {

    private Socket socket;
    private Thread receiveThread;
    private ClientSocketModel client;
    private AdminJFrame admin;
    private serialization sd;
    private support sp;

    public AdminController(Socket soc, AdminJFrame ad) {
        sp = new support();
        sd = new serialization();
        this.admin = ad;
        this.socket = soc;
        client = new ClientSocketModel(socket) {
            @Override
            public void run() {
                try {
                    while (socket.isConnected()) {
                        System.out.println("is receiving...");
                        String receivedData = this.receiveMessage();
                        System.out.println("Received data from server: " + receivedData);
                        handleAdminMessage(receivedData);
                    }
                } catch (IOException ex) {
//                    JOptionPane.showMessageDialog(admin, "Server is closed.");
                    System.err.println("Error reading from server: " + ex.getMessage());
                }
            }

        };
        
        admin.setTitle("Localhost: " + client.getSocket().getInetAddress().getHostAddress() + " , port: " + client.getSocket().getLocalPort());
        
        receiveThread = new Thread(client);
        receiveThread.start();
    }

    public void handleAdminMessage(String message) {
        if (message.startsWith("add")) {
            handleAdd(message);
        } else if (message.startsWith("isEditting")) {
            handleIsEditing(message);
        } else if (message.startsWith("stopEdit")){
            handleStopEdit(message);
        } else if (message.startsWith("deleted")) {
            handleDelete(message);
        } else if (message.startsWith("edit")) {
            handleEdit(message);
        } else if (message.startsWith("changePW")) {
            handleChangePWSuccess(message);
        } else if (message.startsWith("changeName")) {
            handleChangeNameSuccess(message);
        } else if (message.startsWith("DetailBills")) {
            handleShowDetailBills(message);
        } else {
            JOptionPane.showMessageDialog(admin, message);
        }
    }
    
    private void handleAdd(String message) {
        if (message.startsWith("addProductSuccess"))
            handleAddProductSuccess(message);
        else if (message.startsWith("addSupplierSuccess"))
            handleAddSupplierSuccess(message);
        else if (message.startsWith("addCustomerSuccess"))
            handleAddCustomerSuccess(message);
        else if (message.startsWith("addBills"))
            handleAddDetailBill(message);
    }
    
    private void handleIsEditing(String message) {
        if (message.startsWith("isEdittingProduct"))
            handleIsEditingProduct(message);
        else if (message.startsWith("isEdittingSupplier"))
            handleIsEditingSupplier(message);
        else if (message.startsWith("isEdittingCustomer"))
            handleIsEditingCustomer(message);
    }
    
    private void handleStopEdit(String message) {
        if (message.startsWith("stopEditProduct"))
            handleStopEditingProduct(message);
        else if (message.startsWith("stopEditSupplier"))
            handleStopEditingSupplier(message);
        else if (message.startsWith("stopEditCustomer"))
            handleStopEditingCustomer(message);
    }
    
    private void handleEdit(String message) {
        if (message.startsWith("editProductSuccess"))
            handleEditProductSuccess(message);
        else if (message.equals("editProductFail"))
            handleEditProductFail(message);
        else if (message.startsWith("editSupplierSuccess"))
            handleEditSupplierSuccess(message);
        else if (message.equals("editSupplierFail"))
            handleEditSupplierFail(message);
        else if (message.startsWith("editCustomerSuccess"))
            handleEditCustomerSuccess(message);
        else if (message.equals("editCustomerFail"))
            handleEditCustomerFail(message);
    }
    
    private void handleDelete(String message) {
        if (message.startsWith("deletedProduct"))
            handleDeletedProduct(message);
        else if (message.startsWith("deletedSupplier"))
            handleDeletedSupplier(message);
        else if (message.startsWith("deletedCustomer"))
            handleDeletedCustomer(message);
    }

    private void handleAddProductSuccess(String message) {
        // Xử lý khi thêm sản phẩm thành công
        String[] productAdded = message.split(";")[1].split(",");
        if (message.split(";").length > 2) {
            JOptionPane.showMessageDialog(admin, "The new product added successfully");
        }
        int soluong = Integer.parseInt(productAdded[3]);
        float gia = Float.parseFloat(productAdded[4]);
        float kichthuoc = Float.parseFloat(productAdded[6]);
        
        DefaultTableModel model = admin.getModelProduct();
        model.addRow(new Object[]{productAdded[0], productAdded[1], productAdded[2], soluong, gia, productAdded[5], kichthuoc, productAdded[7], productAdded[8], ""});
        admin.setModelProduct(model);
        admin.loadTable(admin.getTblProduct(), model);
    }

    private void handleIsEditingProduct(String message) {
        // Xử lý khi đang chỉnh sửa sản phẩm
        String mamay = message.split(";")[1];
        String editby = message.split(";")[2];
        
        DefaultTableModel model = admin.getModelProduct();
        sp.editStatusEdit(model, mamay, editby);
        admin.setModelProduct(model);
        admin.loadTable(admin.getTblProduct(), model);
    }

    private void handleStopEditingProduct(String message) {
        // Xử lý khi dừng chỉnh sửa sản phẩm
        String mamay = message.split(";")[1];
        
        DefaultTableModel model = admin.getModelProduct();
        sp.editStatusEdit(model, mamay, "");
        admin.setModelProduct(model);
        admin.loadTable(admin.getTblProduct(), model);
    }

    private void handleEditProductSuccess(String message) {
        // Xử lý khi chỉnh sửa sản phẩm thành công
        String[] productEdited = message.split(";")[1].split(",");
        if (message.split(";").length > 2) {
            JOptionPane.showMessageDialog(admin, "The product edited successfully");
        }
        int soluong = Integer.parseInt(productEdited[3]);
        float gia = Float.parseFloat(productEdited[4]);
        float kichthuoc = Float.parseFloat(productEdited[6]);
        MayTinh mt = new MayTinh(productEdited[0], productEdited[1], productEdited[2], soluong, gia, productEdited[5], kichthuoc, productEdited[7], productEdited[8]);

        DefaultTableModel model = admin.getModelProduct();
        sp.editRowProduct(model, mt, "");
        admin.setModelProduct(model);
        admin.loadTable(admin.getTblProduct(), model);
    }

    private void handleEditProductFail(String message) {
        // Xử lý khi chỉnh sửa sản phẩm thất bại
        if (message.split(";").length > 2) {
            JOptionPane.showMessageDialog(admin, "Unable to edit product!");
        }
        DefaultTableModel model = admin.getModelProduct();
        sp.editStatusEdit(model, message.split(";")[1], "");
        admin.setModelProduct(model);
        admin.loadTable(admin.getTblProduct(), model);
    }

    private void handleDeletedProduct(String message) {
        // Xử lý khi xóa sản phẩm
        String[] mamay = message.split(";")[1].split(",");
        if (message.split(";").length > 2) {
            if (admin.checkEmptyArray(mamay)) {
                JOptionPane.showMessageDialog(admin, "Unable to delete product!");
            }
        }
        DefaultTableModel model = admin.getModelProduct();
        for (String str : mamay) {
            sp.removeRowModel(model, str);
        }
        admin.setModelProduct(model);
        admin.loadTable(admin.getTblProduct(), model);
    }
    
    //Supplier
    private void handleAddSupplierSuccess(String message) {
        // Xử lý khi thêm nhà cung cấp thành công
        String[] SupplierAdded = message.split(";")[1].split(",");
        if (message.split(";").length > 2) {
            JOptionPane.showMessageDialog(admin, "The new supplier added successfully");
        }
        DefaultTableModel model = admin.getModelSupplier();
        model.addRow(new Object[]{SupplierAdded[0], SupplierAdded[1], SupplierAdded[2], SupplierAdded[3], ""});
        admin.setModelSupplier(model);
        admin.loadTable(admin.getTblSupplier(), model);
        admin.setCBBItemSupplier();
    }

    private void handleIsEditingSupplier(String message) {
        // Xử lý khi đang chỉnh sửa nhà cung cấp
        String mancc = message.split(";")[1];
        String editby = message.split(";")[2];
        
        DefaultTableModel model = admin.getModelSupplier();
        sp.editStatusEdit(model, mancc, editby);
        admin.setModelSupplier(model);
        admin.loadTable(admin.getTblSupplier(), model);
    }

    private void handleStopEditingSupplier(String message) {
        // Xử lý khi dừng chỉnh sửa nhà cung cấp
        String mancc = message.split(";")[1];
        
        DefaultTableModel model = admin.getModelSupplier();
        sp.editStatusEdit(model, mancc, "");
        admin.setModelSupplier(model);
        admin.loadTable(admin.getTblSupplier(), model);
    }

    private void handleEditSupplierSuccess(String message) {
        // Xử lý khi chỉnh sửa nhà cung cấp thành công
        String[] SupplierAdded = message.split(";")[1].split(",");
        if (message.split(";").length > 2) {
            JOptionPane.showMessageDialog(admin, "The supplier edited successfully");
        }
        NhaCungCap ncc = new NhaCungCap(SupplierAdded[0], SupplierAdded[1], SupplierAdded[2], SupplierAdded[3]);
        
        DefaultTableModel model = admin.getModelSupplier();
        sp.editRowSupplier(model, ncc, "");
        admin.setModelSupplier(model);
        admin.loadTable(admin.getTblSupplier(), model);
        admin.setCBBItemSupplier();
    }

    private void handleEditSupplierFail(String message) {
        // Xử lý khi chỉnh sửa nhà cung cấp thất bại
        if (message.split(";").length > 2) {
            JOptionPane.showMessageDialog(admin, "Unable to edit supplier!");
        }
        DefaultTableModel model = admin.getModelSupplier();
        sp.editStatusEdit(model, message.split(";")[1], "");
        admin.setModelSupplier(model);
        admin.loadTable(admin.getTblSupplier(), model);
    }

    private void handleDeletedSupplier(String message) {
        // Xử lý khi xóa nhà cung cấp
        String[] mancc = message.split(";")[1].split(",");
        if (message.split(";").length > 2) {
            if (admin.checkEmptyArray(mancc)) {
                JOptionPane.showMessageDialog(admin, "Unable to delete supplier!");
            }
        }
        DefaultTableModel model = admin.getModelSupplier();
        for (String str : mancc) {
            sp.removeRowModel(model, str);
        }
        admin.setModelSupplier(model);
        admin.loadTable(admin.getTblSupplier(), model);
        admin.setCBBItemSupplier();
    }
    
    //Account
    private void handleChangePWSuccess(String message) {
        JOptionPane.showMessageDialog(admin, "Password changed successfully!");
        admin.setPWAccount(message.split(";")[2]);
        admin.setInf(admin.getAccount());
        admin.resetForm();
        admin.resetText();
    }
    
    private void handleChangeNameSuccess(String message) {
        JOptionPane.showMessageDialog(admin, "Fullname changed successfully!");
        admin.setNameAccount(message.split(";")[2]);
        admin.setInf(admin.getAccount());
        admin.resetForm();
        admin.resetText();
    }
    
    private void handleShowDetailBills(String message) {
        try {
            String response = message.split(";")[1];
            DefaultTableModel model = (DefaultTableModel) sd.StringtoObject(response);
            
            String name = message.split(";")[2];
            String idKH = message.split(";")[3];
            if (message.split(";").length > 4) {
                float totalOfKH = Float.parseFloat(message.split(";")[4]);
                DefaultTableModel modelcustomer = admin.getModelCustomer();
                sp.updateTotalBillsOfCustomer(modelcustomer, idKH, totalOfKH);
                admin.setModelCustomer(modelcustomer);
                admin.loadTable(admin.getTblCustomer(), modelcustomer);
            }
            
            if (message.split(";").length != 5) {
                DetailForm detail = new DetailForm(admin,true, this, model, admin.getModelProduct(), name, idKH);
                detail.setVisible(true);
            }
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    //Customer
    private void handleAddCustomerSuccess(String message) {
        // Xử lý khi thêm nhà cung cấp thành công
        String[] CustomerAdded = message.split(";")[1].split(",");
        if (message.split(";").length > 2) {
            JOptionPane.showMessageDialog(admin, "The new customer added successfully");
        }
        DefaultTableModel model = admin.getModelCustomer();
        model.addRow(new Object[]{CustomerAdded[0], CustomerAdded[1], CustomerAdded[2], CustomerAdded[3], 0, ""});
        admin.setModelCustomer(model);
        admin.loadTable(admin.getTblCustomer(), model);
    }

    private void handleIsEditingCustomer(String message) {
        String makh = message.split(";")[1];
        String editby = message.split(";")[2];
        
        DefaultTableModel model = admin.getModelCustomer();
        sp.editStatusEdit(model, makh, editby);
        admin.setModelCustomer(model);
        admin.loadTable(admin.getTblCustomer(), model);
    }

    private void handleStopEditingCustomer(String message) {
        // Xử lý khi dừng chỉnh sửa nhà cung cấp
        String makh = message.split(";")[1];
        
        DefaultTableModel model = admin.getModelCustomer();
        sp.editStatusEdit(model, makh, "");
        admin.setModelCustomer(model);
        admin.loadTable(admin.getTblCustomer(), model);
    }

    private void handleEditCustomerSuccess(String message) {
        // Xử lý khi chỉnh sửa nhà cung cấp thành công
        String[] CustomerAdded = message.split(";")[1].split(",");
        if (message.split(";").length > 2) {
            JOptionPane.showMessageDialog(admin, "The customer edited successfully");
        }
        KhachHang kh = new KhachHang(CustomerAdded[0], CustomerAdded[1], CustomerAdded[2], CustomerAdded[3]);
        
        DefaultTableModel model = admin.getModelCustomer();
        sp.editRowCustomer(model, kh, "");
        admin.setModelCustomer(model);
        admin.loadTable(admin.getTblCustomer(), model);
        admin.setCBBItemSupplier();
    }

    private void handleEditCustomerFail(String message) {
        // Xử lý khi chỉnh sửa nhà cung cấp thất bại
        if (message.split(";").length > 2) {
            JOptionPane.showMessageDialog(admin, "Unable to edit customer!");
        }
        
        DefaultTableModel model = admin.getModelCustomer();
        sp.editStatusEdit(model, message.split(";")[1], "");
        admin.setModelCustomer(model);
        admin.loadTable(admin.getTblCustomer(), model);
    }

    private void handleDeletedCustomer(String message) {
        // Xử lý khi xóa nhà cung cấp
        String[] mancc = message.split(";")[1].split(",");
        if (message.split(";").length > 2) {
            if (admin.checkEmptyArray(mancc)) {
                JOptionPane.showMessageDialog(admin, "Unable to delete customer!");
            }
        }
        DefaultTableModel model = admin.getModelCustomer();
        for (String str : mancc) {
            sp.removeRowModel(model, str);
        }
        admin.setModelCustomer(model);
        admin.loadTable(admin.getTblCustomer(), model);
        admin.setCBBItemSupplier();
    }
    
    private void handleAddDetailBill(String message) {
        try {
            String response = message.split(";")[1];
            DefaultTableModel model = (DefaultTableModel) sd.StringtoObject(response);

            String name = message.split(";")[2];
            String idKH = message.split(";")[3];

            float totalOfKH = Float.parseFloat(message.split(";")[4]);

            DefaultTableModel modelcustomer = admin.getModelCustomer();
            sp.updateTotalBillsOfCustomer(modelcustomer, idKH, totalOfKH);
            admin.setModelCustomer(modelcustomer);
            admin.loadTable(admin.getTblCustomer(), modelcustomer);

            if (message.endsWith("clientAdd")) {
                DetailForm detail = new DetailForm(admin, true, this, model, admin.getModelProduct(), name, idKH);
                detail.setVisible(true);
            }
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    public void sendMessage(String message) {
        client.sendMessage(message);
    }
}