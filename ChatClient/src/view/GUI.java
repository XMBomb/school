/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.ChatController;
import java.awt.Color;
import view.chatview.ChatView;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.plaf.FontUIResource;
import model.data.IncomingMessage;
import model.data.Message;
import model.data.Server;
import model.data.User;
import view.chatview.OnlineUserListPanel;
import view.loginpanel.LoginPanel;
import view.senderpanel.MessageSenderPanel;

/**
 *
 * @author milan.bharanya
 */
public class GUI extends JFrame {
    private ChatView chatView = new ChatView();
    private LoginPanel loginPanel = new LoginPanel();
    private MessageSenderPanel messageSenderPanel = new MessageSenderPanel();
    private OnlineUserListPanel onlineUserListPanel;
    private JScrollPane onlineUserListScrollPane;
    private List<User> onlineUserList = new ArrayList<>();
    private JLabel connectedLbl;
    private boolean isConnected = false;
    private final String IS_CONNECTED = "Verbunden mit Server";
    private final String IS_NOT_CONNECTED = "Nicht verbunden";
    private final String LOGIN = "Login";
    private final String LOGOUT = "Logout";
    
    private Color connectedColor = new Color(170, 255, 170);
    private Color notConnectedColor = new Color(255, 170, 170);

    public GUI() {
        initGui();
        Container contentPane = getContentPane();

        JLabel titleLbl = new JLabel("Chatclient");
        titleLbl.setFont(new Font("Segoe UI Light", Font.PLAIN, 44));

        connectedLbl = new JLabel(IS_NOT_CONNECTED);
        connectedLbl.setOpaque(true);
        connectedLbl.setBackground(notConnectedColor);


        onlineUserListPanel = new OnlineUserListPanel(onlineUserList);
        

        onlineUserListScrollPane = new JScrollPane(onlineUserListPanel);

        onlineUserListScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        
        contentPane.setLayout(new GridBagLayout());

        JPanel headerPanel = new JPanel(new GridBagLayout());
        
        
        loginPanel.setPreferredSize(new Dimension(300, 180));
        
        disablePanelsIfNotConnected();

        headerPanel.add(titleLbl,                   new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,         new Insets(5, 5, 5, 5), 0, 0));
        headerPanel.add(connectedLbl,               new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,         new Insets(5, 5, 5, 5), 0, 0));
        

        contentPane.add(headerPanel,                new GridBagConstraints(0, 0, 2, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,      new Insets(5, 5, 5, 5), 0, 0));
        contentPane.add(chatView,                   new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,         new Insets(5, 5, 5, 5), 0, 0));
        contentPane.add(onlineUserListScrollPane,   new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,         new Insets(5, 5, 5, 5), 0, 0));
        contentPane.add(messageSenderPanel,         new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,         new Insets(5, 5, 5, 5), 0, 0));
        contentPane.add(loginPanel,                 new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL,       new Insets(5, 5, 5, 5), 0, 0));
    }

    private void disablePanelsIfNotConnected() {
        chatView.setEnabled(isConnected);
        onlineUserListPanel.setEnabled(isConnected);
        messageSenderPanel.setEnabled(isConnected);
        loginPanel.setEnabled(!isConnected);
        loginPanel.getLoginButton().setEnabled(true);
    }

    public void setOnlineUserList(List<User> onlineUserList) {
        onlineUserListPanel.setUserList(onlineUserList);
        revalidate();
        repaint();
    }

    public List<User> getOnlineUserList() {
        return onlineUserList;
    }

    public void setConnected(boolean isConnected) {
        this.isConnected = isConnected;
        connectedLbl.setText(isConnected ? IS_CONNECTED : IS_NOT_CONNECTED);
        connectedLbl.setBackground(isConnected ? connectedColor : notConnectedColor);
        loginPanel.getLoginButton().setText(isConnected ? LOGOUT : LOGIN);
        disablePanelsIfNotConnected();
        revalidate();
    }

    public void addIncomingMessage(IncomingMessage message) {
        chatView.addIncomingMessage(message);
        revalidate();
    }

    public void setLoginActionListener(ActionListener listener) {
        loginPanel.getLoginButton().addActionListener(listener);
    }

    public void setSendListener(ActionListener sendListener) {
        messageSenderPanel.getSendButton().addActionListener(sendListener);
    }
    
    public void setClearChatListener(ActionListener clearChatListener) {
        messageSenderPanel.getClearChatButton().addActionListener(clearChatListener);
    }

    public void setReceiversCheckBoxItemListener(ItemListener itemListener) {
        onlineUserListPanel.setReceiversCheckBoxItemListener(itemListener);
    }

    public Server getLoginData() {
        return loginPanel.getLoginData();
    }

    public Message getMessage() throws Exception {
        return messageSenderPanel.getMessage();
    }

    private void initGui() {
        setTitle("ChatClient Milan Bharanya");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUIFont(new FontUIResource("Segoe UI", Font.PLAIN, 14));
        setPreferredSize(new Dimension(980, 800));
    }

    public void setUIFont(FontUIResource f) {
        Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value != null && value instanceof FontUIResource) {
                UIManager.put(key, f);
            }
        }
    }

    public List<User> getReceivers() {
        return onlineUserListPanel.getReceivers();
    }

    @Override
    public void revalidate() {
        super.revalidate();
        chatView.scrollToBottom();
    }
    
    public void displayErrorMessage(String text) {
        displayErrorMessage(new Exception(""), text);
    }
    
    public void displayErrorMessage(Exception e) {
        displayErrorMessage(e, "");
    }

    public void displayErrorMessage(Exception e, String additionalInfo) {
        JOptionPane.showMessageDialog(this, "Oops, da ist ein Fehler aufgetreten:\n" + additionalInfo + "\n" + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
    }

    public void setWorkingConnections(List<Server> workingConnectionList) {
        loginPanel.setServerListComboData(workingConnectionList);
    }
    
    public void setWorkingConnectionData(Server data){
        loginPanel.setWorkingConnectionData(data);
    }

    public void setWorkingHostChangeListener(ItemListener workingHostsChangeListener) {
        loginPanel.getServerListCombo().addItemListener(workingHostsChangeListener);
    }
    
    public void clearMessages() {
        chatView.getMessageContainerPanel().removeAll();
        repaint();
    }
    
}