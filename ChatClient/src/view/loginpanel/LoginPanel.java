/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.loginpanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import model.data.LoggedInUser;
import model.data.Server;
import view.AbstractPanel;

/**
 *
 * @author XMBomb
 */
public class LoginPanel extends AbstractPanel{
    private JButton loginButton;
    
    private JTextField hostnameTxt = new JTextField();
    private JFormattedTextField portTxt;
    private JTextField usernameTxt = new JTextField();
    private JPasswordField passwordTxt = new JPasswordField();
    private JComboBox<Server> serverListCombo;
    private JCheckBox  savePasswordCheck;
    private Color errorColor = new Color(255, 170, 170);
    private Color okColor = new Color(170, 255, 170);
    
    
    public LoginPanel(){
        super(new GridBagLayout());     
       
        serverListCombo = new JComboBox<>();

        savePasswordCheck = new JCheckBox("Passwort speichern");
        loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(100, 25));
        loginButton.setMinimumSize(new Dimension(100, 25));

        
        DecimalFormat portFormat = new DecimalFormat("#####");
        portFormat.setMaximumIntegerDigits(5);
        portFormat.setParseIntegerOnly(true);
        
        portTxt = new JFormattedTextField(portFormat);
        
   
        add(new JLabel("Gespeicherte Server:"),          new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,         new Insets(5, 5, 5, 5), 0, 0));
        add(serverListCombo,                             new GridBagConstraints(1, 0, 2, 1, 1.0, 0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.HORIZONTAL,   new Insets(5, 5, 5, 5), 0, 0));
                     
        add(new JLabel("Hostname, Port:"),               new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,         new Insets(5, 5, 5, 5), 0, 0));
        add(hostnameTxt,                                 new GridBagConstraints(1, 1, 1, 1, 0.7, 0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.HORIZONTAL,   new Insets(5, 5, 5, 5), 0, 0));
        add(portTxt,                                     new GridBagConstraints(2, 1, 1, 1, 0.3, 0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.HORIZONTAL,   new Insets(5, 5, 5, 5), 0, 0));
                     
        add(new JLabel("Benutzername:"),                 new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,         new Insets(5, 5, 5, 5), 0, 0));
        add(usernameTxt,                                 new GridBagConstraints(1, 2, 2, 1, 1.0, 0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.HORIZONTAL,   new Insets(5, 5, 5, 5), 0, 0));
                     
        add(new JLabel("Passwort:"),                     new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,         new Insets(5, 5, 5, 5), 0, 0));
        add(passwordTxt,                                 new GridBagConstraints(1, 3, 2, 1, 1.0, 0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.HORIZONTAL,   new Insets(5, 5, 5, 5), 0, 0));
                     
        add(savePasswordCheck,                           new GridBagConstraints(0, 4, 2, 1, 1.0, 0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.HORIZONTAL,   new Insets(5, 5, 5, 5), 0, 0));
                     
        add(loginButton,                                 new GridBagConstraints(1, 5, 2, 1, 1.0, 0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.HORIZONTAL,   new Insets(5, 5, 5, 5), 0, 0));
    }
    
    public void setServerListComboData(List<Server> serverList){
        serverListCombo.removeAllItems();
        for (Server server: serverList){
            serverListCombo.addItem(server);
        }
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public void setLoginButton(JButton loginButton) {
        this.loginButton = loginButton;
    }

    public Server getLoginData() {
        if (isFormValid()){
            Server server = new Server();
            server.setHostname(hostnameTxt.getText());

            server.setPort(Integer.parseInt(portTxt.getText()));

            LoggedInUser user = new LoggedInUser(usernameTxt.getText());

            user.setPassword(passwordTxt.getText());

            if(savePasswordCheck.isSelected()){
                user.setPasswordPlain(passwordTxt.getText());
            }

            server.setUser(user);
            return server;
        }else{
            return null;
        } 
    }
    

    public JComboBox<Server> getServerListCombo() {
        return serverListCombo;
    }

    public void setServerListCombo(JComboBox<Server> serverListCombo) {
        this.serverListCombo = serverListCombo;
    }

    public void setWorkingConnectionData(Server srv) {
        if (srv != null) {
            hostnameTxt.setText(srv.getHostname());
            portTxt.setText(String.valueOf(srv.getPort()));
            usernameTxt.setText(srv.getUser().getUsername());

            // reset password
            passwordTxt.setText("");
            savePasswordCheck.setSelected(false);

            // we saved a password, so the user must have pressed the "save password" checkbox
            if (srv.getUser().getPasswordPlain() != null) {
                passwordTxt.setText(srv.getUser().getPasswordPlain());
                savePasswordCheck.setSelected(true);
            }
        }

    }

    private boolean isFormValid() {
        if (hostnameTxt.getText().isEmpty()){
            hostnameTxt.setBackground(errorColor);
            return false;
        }else{
            hostnameTxt.setBackground(okColor);
        }
        
        if (portTxt.getText().isEmpty()) {
            portTxt.setBackground(errorColor);
            return false;
        } else {
            portTxt.setBackground(okColor);
        }
        
        if (usernameTxt.getText().isEmpty()) {
            usernameTxt.setBackground(errorColor);
            return false;
        } else {
            usernameTxt.setBackground(okColor);
        }
        
        if (passwordTxt.getText().isEmpty()) {
            passwordTxt.setBackground(errorColor);
            return false;
        } else {
            passwordTxt.setBackground(okColor);
        }
        return true;
    }
}
