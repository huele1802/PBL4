/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Bean.serialization;
import Model.BO.accountBO;
import Model.BO.chitietBO;
import Model.BO.khachhangBO;
import Model.BO.maytinhBO;
import Model.BO.nhacungcapBO;
import Model.Bean.Account;
import Model.Bean.ChiTiet;
import Model.Bean.ClientSocketModel;
import Model.Bean.KhachHang;
import Model.Bean.MayTinh;
import Model.Bean.NhaCungCap;
import Model.Bean.support;
import View.ServerJFrame;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Windows
 */
public final class ServerController {

    accountBO accBO;
    maytinhBO mtBO;
    nhacungcapBO nccBO;
    khachhangBO khBO;
    chitietBO ctBO;

    private ServerSocket server;
    private serialization sd;
    private support sp;
    private ServerJFrame serverView;
    private List<ClientSocketModel> clientList;
    int accountNumber;
    
     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ServerController(ServerSocket _server, ServerJFrame view) {
        accBO = new accountBO();
        mtBO = new maytinhBO();
        nccBO = new nhacungcapBO();
        khBO = new khachhangBO();
        ctBO = new chitietBO();

        accountNumber = 0;
        clientList = new ArrayList<>();
        sd = new serialization();
        sp = new support();
        this.serverView = view;
        this.server = _server;

        Thread listenThread = new Thread(() -> {
            try {
                while (!server.isClosed()) {
                    Socket socket = server.accept();
                    ClientSocketModel client = new ClientSocketModel(socket, "client " + clientList.size()) {
                        @Override
                        public void run() {
                            try {
                                while (this.getSocket().isConnected() && this.getSocket() != null && this.isContinueReading()) {
                                    String receivedData = this.receiveMessage();
                                    System.out.println("Received data from client: " + receivedData);
                                    handleProcessMessage(receivedData, this);
                                }
                            } catch (IOException ex) {
                                System.err.println(ex.getMessage());
                                UpdateListAccountModel();
                            }
                        }
                    };
                    clientList.add(client);
                    Thread receiveThread = new Thread(client);
                    receiveThread.start();
                }
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
            finally {
            try {
                if (server != null && !server.isClosed()) {
                    server.close();
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }

        });
        listenThread.start();
    }

    private void handleProcessMessage(String message, ClientSocketModel client) {
        if (message.startsWith("Login")) {
            // Xử lý đăng nhập
            handleLogin(message, client);
        } else if (message.startsWith("resetPW")) {
            //Xử lý đóng client
            handleRequestResetPW(message, client);
        } else if (message.startsWith("Signup")) {
            //Xử lý đóng client
            handleCreateAccount(message, client);
        } else if (message.startsWith("Close")) {
            //Xử lý đóng client
            handleCloseSocket(client);
        } else if (message.startsWith("add")) {
            // Xử lý thêm
            handleAdd(message, client);
        } else if (message.startsWith("isEditting")) {
            // Xử lý đang edit
            handleisEditting(message, client);
        } else if (message.startsWith("stopEdit")) {
            // Xử lý ngừng edit
            handleStopEdit(message, client);
        } else if (message.startsWith("edit")) {
            // Xử lý edit
            handleEdit(message, client);
        } else if (message.startsWith("delete")) {
            // Xử lý xoá
            handleDelete(message, client);
        } else if (message.startsWith("changePW")) {
            // Xử lý thay đổi mật khẩu
            handleChangePassword(message, client);
        } else if (message.startsWith("changeName")) {
            // Xử lý thay đổi tên
            handleChangeName(message, client);
        } else if (message.startsWith("DetailBills")) {
            // Xử lý chi tiết hoá đơn
            handleShowDetailBill(message, client);
        }
    }

    private void handleLogin(String message, ClientSocketModel client) {
        try {
            String accString = message.split(";")[1];
            Account accLogin = (Account) sd.StringtoObject(accString);
            if (checkAccount(accLogin.getusername(), accLogin.getpassword())) {
                if (checkLogin(accLogin.getusername())) {
                    client.sendMessage("AccountisLoggedin");
                } else {
                    try {
                        String product = sd.ObjecttoString(serverView.getModelProduct());
                        String supplier = sd.ObjecttoString(serverView.getModelSupplier());
                        String customer = sd.ObjecttoString(serverView.getModelCustomer());
                        Account accountLogin = accBO.getAccountByUsername(accLogin.getusername());
                        String acc = sd.ObjecttoString(accountLogin);

                        client.sendMessage("LoginOk;" + product + ";" + supplier + ";" + customer + ";" + acc);
                        client.setUsername(accLogin.getusername());
                        accountNumber++;
                        serverView.setStatusAccountOnline(accLogin.getusername(), "Online", client.getSocket().getInetAddress().getHostAddress(), client.getSocket().getPort() + "");
                        serverView.setTableOnl(serverView.getModelAccounts());
                        serverView.setLoginCount(accountNumber);
                    } catch (IOException ex) {
                        System.err.println(ex.getMessage());
                    }
                }
            } else {
                client.sendMessage("LoginFailed");
            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void handleRequestResetPW(String message, ClientSocketModel client) {
        String username = message.split(";")[1];
        Account acc = accBO.getAccountByUsername(username);
        if (acc == null)
            client.sendMessage("Account does not exist.");
        else {
            client.sendMessage("Request sent to the server. Please wait for confirmation.");
            client.setUsername(username);
            serverView.setStatusResetPW(username, "X");
            serverView.setTableOnl(serverView.getModelAccounts());
        }
    }
    
    private void handleCreateAccount(String message, ClientSocketModel client) {
        try {
            Account accSignup = (Account) sd.StringtoObject(message.split(";")[1]);
            if (accBO.checkUsername(accSignup.getusername()) == true) {
                client.sendMessage("UsernameisExist");
            } else {
                int TotalAcc = lastIdAccount() + 1;
                String idAcc = null;
                if (TotalAcc < 10)
                    idAcc = "NV0" + TotalAcc;
                else
                    idAcc = "NV" + TotalAcc;
                boolean add = accBO.Add(idAcc, accSignup.getusername(), accSignup.getpassword(), accSignup.getname());
                if (add) {
                    client.sendMessage("AddAccountSuccess");
                    DefaultTableModel model = serverView.getModelAccounts();
                    model.addRow(new Object[] {model.getRowCount() + 1, idAcc, accSignup.getname(), accSignup.getusername(), "Offline", "", "", ""});
                    serverView.setModelAccounts(model);
                    serverView.setTableOnl(serverView.getModelAccounts());
                } else
                    client.sendMessage("AddAccountFail");
            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void handleCloseSocket(ClientSocketModel client) {
        try {
            accountNumber--;
            serverView.setStatusAccountOnline(client.getUsername(), "Offline", "", "");
            serverView.setTableOnl(serverView.getModelAccounts());
            serverView.setLoginCount(accountNumber);
            
            client.setContinueReading(false);
            client.getSocket().close();
            clientList.remove(client);
        } catch (IOException ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void handleAdd(String message, ClientSocketModel client) {
        if (message.startsWith("addProduct")) {
            // Xử lý thêm sản phẩm
            handleAddProduct(message, client);
        } else if (message.startsWith("addSupplier")) {
            // Xử lý thêm nhà cung cấp
            handleAddSupplier(message, client);
        } else if (message.startsWith("addCustomer")) {
            // Xử lý thêm khách hàng
            handleAddCustomer(message, client);
        } else if (message.startsWith("addDetail")) {
            //Xử lý thêm chi tiết sản phẩm của 1 khách hàng
            handleAddDetail(message, client);
        }
    }
    
    private void handleisEditting(String message, ClientSocketModel client) {
        if (message.startsWith("isEdittingProduct")) {
            //Xử lý đang chỉnh sửa sản phẩm
            handleEdittingProduct(message, client);
        } else if (message.startsWith("isEdittingSupplier")) {
            // Xử lý đang chỉnh sửa nhà cung cấp
            handleEdittingSupplier(message, client);
        } else if (message.startsWith("isEdittingCustomer")) {
            // Xử lý đang chỉnh sửa nhà cung cấp
            handleEdittingCustomer(message, client);
        }
    }
    
    private void handleStopEdit(String message, ClientSocketModel client) {
        if (message.startsWith("stopEditProduct")) {
            //Xử lý dừng chỉnh sửa
            handleStopEditProduct(message, client);
        } else if (message.startsWith("stopEditSupplier")) {
            // Xử lý ngừng chỉnh sửa nhà cung cấp
            handleStopEditSupplier(message, client);
        } else if (message.startsWith("stopEditCustomer")) {
            // Xử lý ngừng chỉnh sửa nhà cung cấp
            handleStopEditCustomer(message, client);
        }
    }
    
    private void handleEdit(String message, ClientSocketModel client) {
        if (message.startsWith("editProduct")) {
            // Xử lý sửa sản phẩm
            handleEditProduct(message, client);
        } else if (message.startsWith("editSupplier")) {
            // Xử lý chỉnh sửa nhà cung cấp
            handleEditSupplier(message, client);
        } else if (message.startsWith("editCustomer")) {
            // Xử lý chỉnh sửa nhà cung cấp
            handleEditCustomer(message, client);
        }
    }
    
    private void handleDelete(String message, ClientSocketModel client) {
        if (message.startsWith("deleteProduct")) {
            // Xử lý xóa sản phẩm
            handleDeleteProduct(message, client);
        } else if (message.startsWith("deleteSupplier")) {
            // Xử lý xoá nhà cung cấp
            handleDeleteSupplier(message, client);
        } else if (message.startsWith("deleteCustomer")) {
            // Xử lý xoá nhà cung cấp
            handleDeleteCustomer(message, client);
        } else if (message.startsWith("deleteDetail")) {
            // Xử lý xoá chi tiết hoá đơn khách hàng
            handleDeleteDetail(message, client);
        }
    }

    private void handleAddProduct(String message, ClientSocketModel client) {
        String[] product = message.split(";")[1].split(",");
        if (checkMaMay(product[0])) {
            client.sendMessage("Product code already exists");
        } else {
            int soluong = Integer.parseInt(product[3]);
            float gia = Float.parseFloat(product[4]);
            float kichthuoc = Float.parseFloat(product[6]);
            boolean status = mtBO.Add(product[0], product[1], product[2], soluong, gia, product[5], kichthuoc, product[7], product[8]);
            if (status) {
                DefaultTableModel model = serverView.getModelProduct();
                model.addRow(new Object[]{product[0], product[1], product[2], soluong, gia, product[5], kichthuoc, product[7], product[8], ""});
                serverView.setModelProduct(model);
                
                DefaultTableModel history = serverView.getModelEditHistory();
                history.addRow(new Object[] {"Add", client.getUsername(), "Product", product[0], "", ""});
                sp.loadTable(serverView.getTbEditHistory(), history);

                client.sendMessage("addProductSuccess;" + message.split(";")[1] + ";" + client.getUsername());
                for (int i = 0; i < clientList.size(); i++) {
                    ClientSocketModel get = clientList.get(i);
                    if (!get.getUsername().equals(client.getUsername())) {
                        get.sendMessage("addProductSuccess;" + message.split(";")[1]);
                    }
                }
            } else {
                client.sendMessage("Unable to add new product!");
            }
        }
    }

    private void handleEdittingProduct(String message, ClientSocketModel client) {
        String mamay = message.split(";")[1];
        DefaultTableModel model = serverView.getModelProduct();
        sp.editStatusEdit(model, mamay, client.getUsername());
        serverView.setModelProduct(model);

        DefaultTableModel history = serverView.getModelEditHistory();
        history.addRow(new Object[] {"Edit", client.getUsername(), "Product", mamay, LocalDateTime.now().format(formatter), ""});
        sp.loadTable(serverView.getTbEditHistory(), history);
        
        for (int i = 0; i < clientList.size(); i++) {
            ClientSocketModel get = clientList.get(i);
            get.sendMessage("isEdittingProduct;" + mamay + ";" + client.getUsername());
        }
    }

    private void handleStopEditProduct(String message, ClientSocketModel client) {
        String mamay = message.split(";")[1];
        DefaultTableModel model = serverView.getModelProduct();
        sp.editStatusEdit(model, mamay, "");
        serverView.setModelProduct(model);
        
        DefaultTableModel history = serverView.getModelEditHistory();
        rowEdit(history, client.getUsername(), mamay, LocalDateTime.now().format(formatter));
        sp.loadTable(serverView.getTbEditHistory(), history);

        for (int i = 0; i < clientList.size(); i++) {
            ClientSocketModel get = clientList.get(i);
            get.sendMessage("stopEditProduct;" + mamay);
        }
    }

    private void handleEditProduct(String message, ClientSocketModel client) {
        String[] product = message.split(";")[1].split(",");
        int soluong = Integer.parseInt(product[3]);
        float gia = Float.parseFloat(product[4]);
        float kichthuoc = Float.parseFloat(product[6]);
        boolean status = mtBO.Update(product[0], product[1], product[2], soluong, gia, product[5], kichthuoc, product[7], product[8]);
        if (status) {
            MayTinh mt = new MayTinh(product[0], product[1], product[2], soluong, gia, product[5], kichthuoc, product[7], product[8]);
            DefaultTableModel model = serverView.getModelProduct();
            sp.editRowProduct(model, mt, "");
            serverView.setModelProduct(model);
            
            DefaultTableModel history = serverView.getModelEditHistory();
            rowEdit(history, client.getUsername(), product[0], LocalDateTime.now().format(formatter));
            sp.loadTable(serverView.getTbEditHistory(), history);

            client.sendMessage("editProductSuccess;" + message.split(";")[1] + ";" + client.getUsername());
            for (int i = 0; i < clientList.size(); i++) {
                ClientSocketModel get = clientList.get(i);
                if (!get.getUsername().equals(client.getUsername())) {
                    get.sendMessage("editProductSuccess;" + message.split(";")[1]);
                }
            }
        } else {
            DefaultTableModel model = serverView.getModelProduct();
            sp.editStatusEdit(model, product[0], "");
            serverView.setModelProduct(model);
            
            DefaultTableModel history = serverView.getModelEditHistory();
            rowEdit(history, client.getUsername(), product[0], LocalDateTime.now().format(formatter));
            sp.loadTable(serverView.getTbEditHistory(), history);

            client.sendMessage("editProductFail;" + product[0] + ";" + client.getUsername());
            for (int i = 0; i < clientList.size(); i++) {
                ClientSocketModel get = clientList.get(i);
                if (!get.getUsername().equals(client.getUsername())) {
                    get.sendMessage("editProductFail;" + product[0]);
                }
            }
        }
    }

    private void handleDeleteProduct(String message, ClientSocketModel client) {
        String[] mamay = message.split(";")[1].split(",");
        String mamayDel = "";
        
        DefaultTableModel model = serverView.getModelProduct();
        DefaultTableModel history = serverView.getModelEditHistory();
        for (String str : mamay) {
            boolean del = mtBO.Delete(str);
            if (del) {
                mamayDel += str + ",";
                sp.removeRowModel(model, str);
                history.addRow(new Object[] {"Delete", client.getUsername(), "Product", str, "", ""});
            }
        }
        serverView.setModelProduct(model);
        sp.loadTable(serverView.getTbEditHistory(), history);

        client.sendMessage("deletedProduct;" + mamayDel + ";" + client.getUsername());
        for (int i = 0; i < clientList.size(); i++) {
            ClientSocketModel get = clientList.get(i);
            if (!get.getUsername().equals(client.getUsername())) {
                get.sendMessage("deletedProduct;" + mamayDel);
            }
        }
    }

    private void handleAddSupplier(String message, ClientSocketModel client) {
        String[] product = message.split(";")[1].split(",");
        if (checkMaNCC(product[0])) {
            client.sendMessage("Supplier code already exists.");
        } else {
            boolean status = nccBO.Add(product[0], product[1], product[2], product[3]);
            if (status) {
                DefaultTableModel model = serverView.getModelSupplier();
                model.addRow(new Object[]{product[0], product[1], product[2], product[3], ""});
                serverView.setModelProduct(model);
                
                DefaultTableModel history = serverView.getModelEditHistory();
                history.addRow(new Object[] {"Add", client.getUsername(), "Supplier", product[0], "", ""});
                sp.loadTable(serverView.getTbEditHistory(), history);

                client.sendMessage("addSupplierSuccess;" + message.split(";")[1] + ";" + client.getUsername());
                for (int i = 0; i < clientList.size(); i++) {
                    ClientSocketModel get = clientList.get(i);
                    if (!get.getUsername().equals(client.getUsername())) {
                        get.sendMessage("addSupplierSuccess;" + message.split(";")[1]);
                    }
                }
            } else {
                client.sendMessage("Unable to add new supplier!");
            }
        }
    }

    private void handleEdittingSupplier(String message, ClientSocketModel client) {
        String mamay = message.split(";")[1];
        DefaultTableModel model = serverView.getModelSupplier();
        sp.editStatusEdit(model, mamay, client.getUsername());
        serverView.setModelSupplier(model);
        
        DefaultTableModel history = serverView.getModelEditHistory();
        history.addRow(new Object[] {"Edit", client.getUsername(), "Supplier", mamay, LocalDateTime.now().format(formatter), ""});
        sp.loadTable(serverView.getTbEditHistory(), history);

        for (int i = 0; i < clientList.size(); i++) {
            ClientSocketModel get = clientList.get(i);
            get.sendMessage("isEdittingSupplier;" + mamay + ";" + client.getUsername());
        }
    }

    private void handleStopEditSupplier(String message, ClientSocketModel client) {
        String mamay = message.split(";")[1];
        DefaultTableModel model = serverView.getModelSupplier();
        sp.editStatusEdit(model, mamay, "");
        serverView.setModelSupplier(model);
        
        DefaultTableModel history = serverView.getModelEditHistory();
        rowEdit(history, client.getUsername(), mamay, LocalDateTime.now().format(formatter));
        sp.loadTable(serverView.getTbEditHistory(), history);

        for (int i = 0; i < clientList.size(); i++) {
            ClientSocketModel get = clientList.get(i);
            get.sendMessage("stopEditSupplier;" + mamay);
        }
    }

    private void handleEditSupplier(String message, ClientSocketModel client) {
        String[] product = message.split(";")[1].split(",");
        boolean status = nccBO.Update(product[0], product[1], product[2], product[3]);
        if (status) {
            NhaCungCap ncc = new NhaCungCap(product[0], product[1], product[2], product[3]);
            DefaultTableModel model = serverView.getModelSupplier();
            sp.editRowSupplier(model, ncc, "");
            serverView.setModelSupplier(model);
            
            DefaultTableModel history = serverView.getModelEditHistory();
            rowEdit(history, client.getUsername(), product[0], LocalDateTime.now().format(formatter));
            sp.loadTable(serverView.getTbEditHistory(), history);

            client.sendMessage("editSupplierSuccess;" + message.split(";")[1] + ";" + client.getUsername());
            for (int i = 0; i < clientList.size(); i++) {
                ClientSocketModel get = clientList.get(i);
                if (!get.getUsername().equals(client.getUsername())) {
                    get.sendMessage("editSupplierSuccess;" + message.split(";")[1]);
                }
            }
        } else {
            DefaultTableModel model = serverView.getModelSupplier();
            sp.editStatusEdit(model, product[0], "");
            serverView.setModelSupplier(model);
            
            DefaultTableModel history = serverView.getModelEditHistory();
            rowEdit(history, client.getUsername(), product[0], LocalDateTime.now().format(formatter));
            sp.loadTable(serverView.getTbEditHistory(), history);

            client.sendMessage("editSupplierFail;" + product[0] + ";" + client.getUsername());
            for (int i = 0; i < clientList.size(); i++) {
                ClientSocketModel get = clientList.get(i);
                if (!get.getUsername().equals(client.getUsername())) {
                    get.sendMessage("editSupplierFail;" + product[0]);
                }
            }
        }
    }

    private void handleDeleteSupplier(String message, ClientSocketModel client) {
        String[] mamay = message.split(";")[1].split(",");
        String mamayDel = "";
        DefaultTableModel model = serverView.getModelSupplier();
        DefaultTableModel history = serverView.getModelEditHistory();
        for (String str : mamay) {
            boolean del = nccBO.Delete(str);
            if (del) {
                mamayDel += str + ",";
                sp.removeRowModel(model, str);
                history.addRow(new Object[] {"Delete", client.getUsername(), "Supplier", str, "", ""});
            }
        }
        serverView.setModelSupplier(model);
        sp.loadTable(serverView.getTbEditHistory(), history);

        client.sendMessage("deletedSupplier;" + mamayDel + ";" + client.getUsername());
        for (int i = 0; i < clientList.size(); i++) {
            ClientSocketModel get = clientList.get(i);
            if (!get.getUsername().equals(client.getUsername())) {
                get.sendMessage("deletedSupplier;" + mamayDel);
            }
        }
    }

    private void handleChangePassword(String message, ClientSocketModel client) {
        String username = message.split(";")[1];
        String pw = message.split(";")[2];
        boolean status = accBO.changePassword(username, pw);
        if (status) {
            client.sendMessage(message);
        } else {
            client.sendMessage("Unable to change password!");
        }
    }

    private void handleChangeName(String message, ClientSocketModel client) {
        String username = message.split(";")[1];
        String name = message.split(";")[2];
        boolean status = accBO.changeName(username, name);
        if (status) {
            client.sendMessage(message);
        } else {
            client.sendMessage("Unable to change fullname!");
        }
    }
    
    private void handleShowDetailBill(String message, ClientSocketModel client) {
        String idkhachhang = message.split(";")[1];
        DefaultTableModel modelDetail = getListDetail(idkhachhang);
        String nameCustomer = khBO.selectById(idkhachhang).getTenkhachhang();
        String response = null;
        try {
            response = sd.ObjecttoString(modelDetail);
        } catch (IOException ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        client.sendMessage("DetailBills;" + response + ";" + nameCustomer + ";" + idkhachhang);
    }
    
    private void handleAddCustomer(String message, ClientSocketModel client) {
        String[] customer = message.split(";")[1].split(",");
        if (checkMaCustomer(customer[0])) {
            client.sendMessage("Customer code already exists.");
        } else {
            boolean status = khBO.Add(customer[0], customer[1], customer[2], customer[3]);
            if (status) {
                DefaultTableModel model = serverView.getModelCustomer();
                model.addRow(new Object[]{customer[0], customer[1], customer[2], customer[3], 0, ""});
                serverView.setModelSupplier(model);
                
                DefaultTableModel history = serverView.getModelEditHistory();
                history.addRow(new Object[] {"Add", client.getUsername(), "Customer", customer[0], "", ""});
                sp.loadTable(serverView.getTbEditHistory(), history);

                client.sendMessage("addCustomerSuccess;" + message.split(";")[1] + ";" + client.getUsername());
                for (int i = 0; i < clientList.size(); i++) {
                    ClientSocketModel get = clientList.get(i);
                    if (!get.getUsername().equals(client.getUsername())) {
                        get.sendMessage("addCustomerSuccess;" + message.split(";")[1]);
                    }
                }
            } else {
                client.sendMessage("Unable to add new customer!");
            }
        }
    }
    
    private void handleEdittingCustomer(String message, ClientSocketModel client) {
        String makh = message.split(";")[1];
        DefaultTableModel model = serverView.getModelCustomer();
        sp.editStatusEdit(model, makh, client.getUsername());
        serverView.setModelCustomer(model);

        DefaultTableModel history = serverView.getModelEditHistory();
        history.addRow(new Object[] {"Edit", client.getUsername(), "Customer", makh, LocalDateTime.now().format(formatter), ""});
        sp.loadTable(serverView.getTbEditHistory(), history);
        
        for (int i = 0; i < clientList.size(); i++) {
            ClientSocketModel get = clientList.get(i);
            get.sendMessage("isEdittingCustomer;" + makh + ";" + client.getUsername());
        }
    }

    private void handleStopEditCustomer(String message, ClientSocketModel client) {
        String maKH = message.split(";")[1];
        DefaultTableModel model = serverView.getModelCustomer();
        sp.editStatusEdit(model, maKH, "");
        serverView.setModelCustomer(model);
        
        DefaultTableModel history = serverView.getModelEditHistory();
        rowEdit(history, client.getUsername(), maKH, LocalDateTime.now().format(formatter));
        sp.loadTable(serverView.getTbEditHistory(), history);

        for (int i = 0; i < clientList.size(); i++) {
            ClientSocketModel get = clientList.get(i);
            get.sendMessage("stopEditCustomer;" + maKH);
        }
    }

    private void handleEditCustomer(String message, ClientSocketModel client) {
        String[] product = message.split(";")[1].split(",");
        boolean status = khBO.Update(product[0], product[1], product[2], product[3]);
        if (status) {
            KhachHang kh = new KhachHang(product[0], product[1], product[2], product[3]);
            DefaultTableModel model = serverView.getModelCustomer();
            sp.editRowCustomer(model, kh, "");
            serverView.setModelCustomer(model);
            
            DefaultTableModel history = serverView.getModelEditHistory();
            rowEdit(history, client.getUsername(), product[0], LocalDateTime.now().format(formatter));
            sp.loadTable(serverView.getTbEditHistory(), history);

            client.sendMessage("editCustomerSuccess;" + message.split(";")[1] + ";" + client.getUsername());
            for (int i = 0; i < clientList.size(); i++) {
                ClientSocketModel get = clientList.get(i);
                if (!get.getUsername().equals(client.getUsername())) {
                    get.sendMessage("editCustomerSuccess;" + message.split(";")[1]);
                }
            }
        } else {
            DefaultTableModel model = serverView.getModelCustomer();
            sp.editStatusEdit(model, product[0], "");
            serverView.setModelCustomer(model);
            
            DefaultTableModel history = serverView.getModelEditHistory();
            rowEdit(history, client.getUsername(), product[0], LocalDateTime.now().format(formatter));
            sp.loadTable(serverView.getTbEditHistory(), history);

            client.sendMessage("editCustomerFail;" + product[0] + ";" + client.getUsername());
            for (int i = 0; i < clientList.size(); i++) {
                ClientSocketModel get = clientList.get(i);
                if (!get.getUsername().equals(client.getUsername())) {
                    get.sendMessage("editCustomerFail;" + product[0]);
                }
            }
        }
    }
    
    private void handleDeleteCustomer(String message, ClientSocketModel client) {
        String[] mamay = message.split(";")[1].split(",");
        String mamayDel = "";
        DefaultTableModel model = serverView.getModelCustomer();
        DefaultTableModel history = serverView.getModelEditHistory();
        for (String str : mamay) {
            boolean del = khBO.Delete(str);
            if (del) {
                mamayDel += str + ",";
                sp.removeRowModel(model, str);
                history.addRow(new Object[] {"Delete", client.getUsername(), "Customer", str, "", ""});
            }
        }
        serverView.setModelCustomer(model);
        sp.loadTable(serverView.getTbEditHistory(), history);

        client.sendMessage("deletedCustomer;" + mamayDel + ";" + client.getUsername());
        for (int i = 0; i < clientList.size(); i++) {
            ClientSocketModel get = clientList.get(i);
            if (!get.getUsername().equals(client.getUsername())) {
                get.sendMessage("deletedCustomer;" + mamayDel);
            }
        }
    }

    private void handleAddDetail(String message, ClientSocketModel client) {
        String billString = message.split(";")[1];
        try {
            List<ChiTiet> list = (List<ChiTiet>) sd.StringtoObject(billString);
            for (int i = 0; i < list.size(); i++) {
                ChiTiet get = list.get(i);
                System.out.println(get.getIdkhachhang() + " " + get.getMaMay() + " " + get.getSoluong() + " " + get.getNgaymua());
                ctBO.Add(get);
            }

            String idKH = list.get(0).getIdkhachhang();
            DefaultTableModel modelDetail = getListDetail(idKH);
            String nameCustomer = khBO.selectById(idKH).getTenkhachhang();
            String response = null;
            try {
                response = sd.ObjecttoString(modelDetail);
            } catch (IOException ex) {
                Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
            }

            client.sendMessage("addBills;" + response + ";" + nameCustomer + ";" + idKH + ";" + ctBO.TotalBillofCustomer(idKH) + ";" + "clientAdd");
            for (int i = 0; i < clientList.size(); i++) {
                ClientSocketModel get = clientList.get(i);
                if (!get.getUsername().equals(client.getUsername())) {
                    get.sendMessage("addBills;" + response + ";" + nameCustomer + ";" + idKH + ";" + ctBO.TotalBillofCustomer(idKH));
                }
            }

            DefaultTableModel modelcustomer = serverView.getModelCustomer();
            sp.updateTotalBillsOfCustomer(modelcustomer, idKH, ctBO.TotalBillofCustomer(idKH));
            serverView.setModelCustomer(modelcustomer);
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void handleDeleteDetail(String message, ClientSocketModel client) {
        String[] mamay = message.split(";")[1].split(",");
        for (String str : mamay) {
            ctBO.Delete(str);
        }
        String idkhachhang = message.split(";")[2];
        DefaultTableModel modelDetail = getListDetail(idkhachhang);
        String nameCustomer = khBO.selectById(idkhachhang).getTenkhachhang();
        String response = null;
        try {
            response = sd.ObjecttoString(modelDetail);
        } catch (IOException ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        }

        client.sendMessage("DetailBills;" + response + ";" + nameCustomer + ";" + idkhachhang + ";" + ctBO.TotalBillofCustomer(idkhachhang) + ";" + "clientShow");
        for (int i = 0; i < clientList.size(); i++) {
            ClientSocketModel get = clientList.get(i);
            if (!get.getUsername().equals(client.getUsername())) {
                get.sendMessage("DetailBills;" + response + ";" + nameCustomer + ";" + idkhachhang + ";" + ctBO.TotalBillofCustomer(idkhachhang));
            }
        }

        DefaultTableModel modelcustomer = serverView.getModelCustomer();
        sp.updateTotalBillsOfCustomer(modelcustomer, idkhachhang, ctBO.TotalBillofCustomer(idkhachhang));
        serverView.setModelCustomer(modelcustomer);
    }
    
    boolean checkAccount(String username, String pass) {
        return accBO.login(username, pass);
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

    boolean checkLogin(String username) {
        for (ClientSocketModel client : clientList) {
            if (username.equals(client.getUsername())) {
                return true;
            }
        }
        return false;
    }

    void UpdateListAccountModel() {
        serverView.setListAccountModel(getListAccounts());
        for (ClientSocketModel client : clientList) {
            if (client.isLogin() == true) {
                serverView.setStatusAccountOnline(client.getUsername(), "Online", client.getSocket().getInetAddress().getHostAddress(), client.getSocket().getPort() + "");
            }
        }
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    boolean checkMaMay(String mamay) {
        List<MayTinh> listMT = getListProducts();
        for (int i = 0; i < listMT.size(); i++) {
            if (listMT.get(i).getmaMay().equals(mamay)) {
                return true;
            }
        }
        return false;
    }

    boolean checkMaNCC(String mancc) {
        List<NhaCungCap> listNCC = getListSuppliers();
        for (int i = 0; i < listNCC.size(); i++) {
            if (listNCC.get(i).getmaNhaCungCap().equals(mancc)) {
                return true;
            }
        }
        return false;
    }
    
    boolean checkMaCustomer(String makh) {
        List<KhachHang> listKH = getListCustomers();
        for (int i = 0; i < listKH.size(); i++) {
            if (listKH.get(i).getIdkhachhang().equals(makh)) {
                return true;
            }
        }
        return false;
    }
    
    int lastIdAccount() {
        Account acc = accBO.getLastAccount();
        
        String numberPart = acc.getidaccount().replaceAll("[^0-9]", "");
        return Integer.parseInt(numberPart);
    }
    
    DefaultTableModel getListDetail(String idKH) {
        DefaultTableModel modelDetail = new DefaultTableModel();
        String[] columnNames = {"ID", "Product ID", "Product Name", "Quantity", "Purchase Date", "Price", "Total"};
        modelDetail.setColumnIdentifiers(columnNames);

        List<ChiTiet> chitiet = ctBO.listChiTietBill(idKH);
        for (ChiTiet chiTiet : chitiet) {
            int id = chiTiet.getIdchitiet();
            String mamay = chiTiet.getMaMay();
            MayTinh mt = mtBO.selectById(mamay);
            String tenmay = mt.gettenmay();
            int soluong = chiTiet.getSoluong();
            Date dateString = chiTiet.getNgaymua();
            float gia = mt.getgia();
            float total = gia * soluong;

            modelDetail.addRow(new Object[]{id, mamay, tenmay, soluong, dateString, gia, total});
        }
        return modelDetail;
    }
    
    public void rowEdit(DefaultTableModel model, String username, String id, String endtime) {
        for (int i = model.getRowCount() - 1; i >= 0; i--) {
            String user = model.getValueAt(i, 1).toString();
            String code = model.getValueAt(i, 3).toString();
            if (user.equals(username) && code.equals(id)) {
                model.setValueAt(endtime, i, 5);
                break;
            }
        }
    }

    public List<ClientSocketModel> getClientList() {
        return clientList;
    }
}