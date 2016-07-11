/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.List;
import model.data.IncomingMessage;
import model.data.User;

/**
 *
 * @author milan.bharanya
 */
public interface IReceiverHandler {
    void handleOnlineUsers(List<User> users);
    void handleNewMessage(IncomingMessage message);
    void handleError(Exception e);
}
