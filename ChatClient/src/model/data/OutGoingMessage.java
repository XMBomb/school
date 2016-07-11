/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.data;

import java.util.List;

/**
 *
 * @author milan.bharanya
 */
public class OutGoingMessage extends Message {

    private List<User> receivers;
    private LoggedInUser loggedInUser;

    public OutGoingMessage(String text, List<User> receivers, LoggedInUser loggedInUser) {
        super(text);
        this.receivers = receivers;
        this.loggedInUser = loggedInUser;
    }

    public List<User> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<User> receivers) {
        this.receivers = receivers;
    }

    public LoggedInUser getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(LoggedInUser loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
}
