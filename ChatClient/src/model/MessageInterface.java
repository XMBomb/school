/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import model.data.OutGoingMessage;
import model.data.User;
import controller.IReceiverHandler;
import java.io.IOException;
import java.io.Serializable;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import m120.ChatProtocol.ClientLogon;
import m120.ChatProtocol.MessageToClients;
import m120.ChatProtocol.MessageToServer;
import m120.ChatProtocol.OnlineUserList;
import model.data.IncomingMessage;
import model.data.LoggedInUser;

/**
 *
 * @author milan.bharanya
 */
public class MessageInterface extends SendReceive {

    private IReceiverHandler iReceiverHandler;

    public MessageInterface(String host, int port, IReceiverHandler iReceiverHandler) throws UnknownHostException, IOException {
        super();
        this.iReceiverHandler = iReceiverHandler;
        super.connect(host, port);
    }
    

    public void sendClientLogon(LoggedInUser user) {
        ClientLogon clientLogon = new ClientLogon();
        clientLogon.setUserPassword(user.getUsername(), user.getPassword());
        send(clientLogon);
    }

    public void sendMessage(OutGoingMessage message) {
        MessageToServer messageToServer = new MessageToServer();
        messageToServer.setMessage(message.getText());

        Vector<String> userList = new Vector<>();
        for (User user : message.getReceivers()) {
            userList.add(user.getUsername());
        }

        messageToServer.setUsernames(userList);
        messageToServer.setUserPassword(message.getLoggedInUser().getUsername(), message.getLoggedInUser().getPassword());

        send(messageToServer);
    }

    @Override
    public void receive(Object message) {
        if (message instanceof OnlineUserList) {
            OnlineUserList onlineUserList = (OnlineUserList) message;
            List<User> userList = new ArrayList<>();
            Vector<String> usernames = onlineUserList.getUsernames();

            for (String username : usernames) {
                userList.add(new User(username));
            }
            iReceiverHandler.handleOnlineUsers(userList);
        }
        
        if (message instanceof MessageToClients){
            MessageToClients messageToClients = (MessageToClients) message;
            IncomingMessage incomingMessage = new IncomingMessage(messageToClients.getMessage());
            iReceiverHandler.handleNewMessage(incomingMessage);
        }
    }

    @Override
    public void handleError(Exception e) {
        iReceiverHandler.handleError(e);
    }
}
