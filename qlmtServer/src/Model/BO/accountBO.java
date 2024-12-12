/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.BO;

import java.util.List;
import Model.Bean.Account;
import Model.DAO.accountDAO;

/**
 *
 * @author Windows
 */
public class accountBO {
    accountDAO accDAO = new accountDAO();
    public boolean login(String username, String password) {
        return accDAO.login(username, password);
    }
    
    public List<Account> listAccount() {
        return accDAO.listAccount();
    }

    public Boolean Add(String idaccount, String username, String password, String name) {
        return accDAO.Add(idaccount, username, password, name);
    }

    public Boolean Update(String idaccount, String username, String password, String name) {
        return accDAO.Update(idaccount, username, password, name);
    }
    
    public boolean changePassword(String username, String pw) {
        return accDAO.changePassword(username, pw);
    }
    
    public boolean changeName(String username, String name) {
        return accDAO.changeName(username, name);
    }

    public Boolean Delete(String idaccount) {
        return accDAO.Delete(idaccount);
    }

    public Account getAccountByUsername(String user) {
        return accDAO.getAccountByUsername(user);
    }
    
    public Account getLastAccount() {
        return accDAO.getLastAccount();
    }
    
    public boolean checkUsername(String user) {
        return accDAO.checkUsername(user);
    }
    
    public boolean checkidAccount(String id) {
        return accDAO.checkidAccount(id);
    }
}
