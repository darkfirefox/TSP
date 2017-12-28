/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import models.Users;

/**
 *
 * @author Павел
 */
@ManagedBean
@SessionScoped
public class LoginController implements java.io.Serializable{
    String username;
    String password;
    DBhelper helper;
    Users user;
    public LoginController() {
        this.helper=new DBhelper();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String validate(){
        this.user=helper.checkUser(this.username,this.password.hashCode()+"");
        if(user==null) return null;
        if(user.getRole().equals("a")) {
            return "admin";
        }
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("User", user);
        return "user";
    }
    public String showReg(){
        return "reg";
    }
    public String reg(){
        Users newuser=new Users();
        newuser.setUsername(username);
        newuser.setPassword(password);
        this.user=helper.registry(newuser);
        if(this.user==null) return null;
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("User", user);
        return "user";
    }
}
