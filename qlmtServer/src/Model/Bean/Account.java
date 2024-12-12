/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Bean;

import java.io.Serializable;



/**
 *
 * @author PC
 */
public class Account implements Serializable {
    private String idaccount,username,password,name;

    public Account(){

    }
    public Account(String idaccount, String username, String password, String name) {
        this.idaccount = idaccount;
        this.username = username;
        this.password = password;
        this.name = name;
        
    }
    public String getidaccount(){
        return idaccount;
    }
    public void setidaccount(String idaccount){
        this.idaccount = idaccount;
    }
    public String getusername(){
        return username;
    }
    public void setusername(String username){
        this.username = username;
    }
    public String getpassword(){
        return password;
    }
    public void setpassword(String password){
        this.password = password;
    }
    public String getname(){
        return name;
    }
    public void setname(String name){
        this.name = name;
    }
}