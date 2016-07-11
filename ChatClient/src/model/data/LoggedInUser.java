/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.data;

import m120.ChatProtocol.Security;

/**
 *
 * @author milan.bharanya
 */
public class LoggedInUser extends User{
    private String password;
    private String passwordPlain;

    
    public LoggedInUser(String username) {
        super(username);
    }
    
    public LoggedInUser(String username, String password) {
        super(username);
        setPassword(password);
    }    


    public final void setPassword(String password) {
        this.password = Security.encrypt(password);
    }

    public String getPassword() {
        return password;
    }

    public void setPasswordPlain(String passwordPlain) {
        this.passwordPlain = passwordPlain;
    }

    public String getPasswordPlain() {
        return passwordPlain;
    }  
    
}
