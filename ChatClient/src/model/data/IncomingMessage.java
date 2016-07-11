/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.data;

import model.data.Message;

/**
 * Extrahiert noch usernamen aus message
 * @author milan.bharanya
 */
public class IncomingMessage extends Message{
    
    private User user;

    public IncomingMessage(String text) {
        super(text.substring(text.indexOf(":") + 1));
        this.user = getUserFromMessageText(text);
    }
    
    private User getUserFromMessageText(String text){
        return new User(text.substring(0, text.indexOf(":") + 1));
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
}
