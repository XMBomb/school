/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.UIManager;
import model.data.LoggedInUser;
import model.MessageInterface;
import model.data.IncomingMessage;
import model.data.OutGoingMessage;
import model.data.Server;
import model.data.User;
import view.GUI;

/**
 *
 * @author milan.bharanya
 */
public class ChatController implements IReceiverHandler {

    private MessageInterface messageInterface;
    private GUI gui = new GUI();
    private Server loginData;
    private List<User> receivers = new ArrayList<>();
    private boolean isConnected = false;
    private List<Server> workingConnectionList = new ArrayList<>();
    private final String CONNECTION_FILE_PATH = "res" + File.separator + "connections.dat";

    public static void main(String... args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Error setting Design");
        }

        ChatController chatController = new ChatController();

        try {
            chatController.run();
        } catch (Exception e) {
//            System.err.println(e.getMessage());
            chatController.gui.displayErrorMessage(e);
//            e.printStackTrace();
        }
    }

    private void run() {
        loadWorkingConnections();
        initView();
    }

    private void initView() {
        gui.pack();
        gui.setVisible(true);

        // set ActionListener to "Login Button"
        gui.setWorkingConnections(workingConnectionList);
        gui.setLoginActionListener(new LoginListener());
        gui.setSendListener(new SendListener());
        gui.setClearChatListener(new ClearChatListener());
        gui.setWorkingHostChangeListener(new WorkingHostsChangeListener());
        gui.setReceiversCheckBoxItemListener(new ReceiversCheckBoxItemListener());
    }

    private LoggedInUser getCurrentlyLoggedInUser() {
        if (isConnected) {
            return loginData.getUser();
        } else {
            gui.displayErrorMessage("Es ist kein Benutzer angemeldet!");
            return null;
        }
    }

    private void login() {
        try {
            messageInterface = new MessageInterface(loginData.getHostname(), loginData.getPort(), this);
            messageInterface.sendClientLogon(loginData.getUser());

            gui.setConnected(isConnected = true);
            addNewWorkingConnection(loginData);
            saveConnections();
            gui.setWorkingConnections(workingConnectionList);
            gui.revalidate();
        } catch (UnknownHostException ex) {
            gui.displayErrorMessage(ex, "Unbekannter Hostname:");
//            ex.printStackTrace();
        } catch (IOException ex) {
            gui.displayErrorMessage(ex);
//            ex.printStackTrace();
        }
    }

    @Override
    public void handleNewMessage(IncomingMessage message) {
//        System.out.println(message.getText());
        // add message on view
        gui.addIncomingMessage(message);
    }

    @Override
    public void handleError(Exception e) {
        if (e instanceof EOFException && e.getMessage() == null) {
            gui.displayErrorMessage("Kann nicht zum Server verbinden, oder ein anderer Benutzer ist bereits eingeloggt");
        }else
        if(e instanceof IOException){
            if (e.getMessage().toLowerCase().contains("connection reset")){
                gui.displayErrorMessage(e, "Verbindung zum Server verloren");
            }
            
            if(e.getMessage().toLowerCase().contains("refused")){
                gui.displayErrorMessage(e, "Kann nicht zum Server verbinden");
            }
            
            if (e instanceof UnknownHostException){
                gui.displayErrorMessage(e, "Unbekannter Server");
            }
        }else{
            gui.displayErrorMessage(e);
        }
        
        disconnect();
//        e.printStackTrace();
    }

    @Override
    public void handleOnlineUsers(List<User> users) {
        if (users != null && !users.isEmpty()) {
            for (int i = 0; i<users.size(); i++){
                if (users.get(i).getUsername().equals(getCurrentlyLoggedInUser().getUsername())){
                    users.remove(i);
                }
            }
            // all online users
            gui.setOnlineUserList(users);
        }
    }

    public void setLoginData(Server loginData) {
        this.loginData = loginData;
    }

    private void addNewWorkingConnection(Server newServer) {
        if (workingConnectionList.size() > 1) {
            if (workingConnectionList.contains(newServer)){
                for (int i = 0; i < workingConnectionList.size(); i++) {
                    if (workingConnectionList.get(i) != null) {
                        if (workingConnectionList.get(i).equals(newServer)) {
                            workingConnectionList.set(i, newServer);
                            break;
                        }
                    }
                }
            }
            else {
                workingConnectionList.add(newServer);
            }
        } else {
            workingConnectionList.add(newServer);
        }
    }

    private void saveConnections() {
        FileOutputStream fout = null;
        ObjectOutputStream oos = null;
        try {
            fout = new FileOutputStream(CONNECTION_FILE_PATH);
            oos = new ObjectOutputStream(fout);
            oos.writeObject(workingConnectionList);
        } catch (Exception e) {
            gui.displayErrorMessage(e);
        } finally {
            if (fout != null) {
                try {
                    fout.close();
                } catch (IOException ex) {
                    gui.displayErrorMessage(ex);
//                    ex.printStackTrace();
                }
            }
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException ex) {
                    gui.displayErrorMessage(ex);
//                    ex.printStackTrace();
                }
            }
        }
    }

    private void loadWorkingConnections() {
        FileInputStream fin = null;
        ObjectInputStream ois = null;
        try {
            fin = new FileInputStream(CONNECTION_FILE_PATH);
            ois = new ObjectInputStream(fin);
            List<Server> oldList = (List<Server>) ois.readUnshared();

            this.workingConnectionList = oldList;
        } catch (FileNotFoundException fnfe) {
            this.workingConnectionList = new ArrayList<Server>();
            this.workingConnectionList.add(null);
        }
        catch (Exception e) {
//            e.printStackTrace();
            gui.displayErrorMessage(e);
        }
        finally {
            try {
                if (fin != null) {
                    fin.close();
                }
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException ioe) {
//                ioe.printStackTrace();
                gui.displayErrorMessage(ioe);
            }
        }
    }
    
    private void disconnect(){
        gui.setConnected(isConnected = false);
        receivers.clear();
        gui.setOnlineUserList(receivers);
        messageInterface.disconnect();
        gui.revalidate();
    }

    protected class LoginListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!isConnected) {
                Server _loginData = gui.getLoginData();
                if (_loginData != null){
                    setLoginData(gui.getLoginData());
                    login();
                }else{
                    gui.displayErrorMessage("Es müssen alle Felder ausgefüllt werden");
                }
            } else {
                disconnect();
            }
        }
    }
    
  

    protected class SendListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if(receivers.size() > 0){
                    receivers.add(getCurrentlyLoggedInUser());
                }
                OutGoingMessage message = new OutGoingMessage(gui.getMessage().getText(), receivers, getCurrentlyLoggedInUser());
                messageInterface.sendMessage(message);
            } catch (Exception ex) {
                gui.displayErrorMessage(ex);
            }
        }
    }
    
    protected class ClearChatListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            gui.clearMessages();
        }
    }

    protected class ReceiversCheckBoxItemListener implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent e) {
            JCheckBox checkBox = (JCheckBox) e.getItemSelectable();
            if (checkBox.isSelected()) {
                receivers.add(new User(checkBox.getText()));
            }else{
                // remove not checked users
                for (int i =0; i< receivers.size(); i++){
                    if (receivers.get(i).getUsername().equals(checkBox.getText())){
                        receivers.remove(i);
                    }
                }
            }
        }
    }

    protected class WorkingHostsChangeListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            JComboBox<Server> combo = (JComboBox<Server>) e.getSource();
            gui.setWorkingConnectionData((Server) combo.getSelectedItem());
        }
    }
}
