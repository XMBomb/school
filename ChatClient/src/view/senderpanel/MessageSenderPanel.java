/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.senderpanel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import model.data.Message;
import view.AbstractPanel;

/**
 * This Panel contains all Elements to send a message (textarea + send buttons)
 * @author XMBomb
 */
public class MessageSenderPanel extends AbstractPanel{
    
    private JButton sendButton = new JButton("Senden");
    private JButton clearChatButton = new JButton("Leeren");
    private MessageTextArea messageTextArea = new MessageTextArea();
    
    public MessageSenderPanel() {
        super(new GridBagLayout());
        messageTextArea.addKeyListener(new EnterKeyListener());
        
        add(messageTextArea,         new GridBagConstraints(0, 0, 1, 2, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,         new Insets(5, 5, 5, 5), 0, 0));
        add(clearChatButton,         new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,    new Insets(5, 5, 5, 5), 0, 0));
        add(sendButton,              new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTHEAST, GridBagConstraints.NONE,    new Insets(5, 5, 5, 5), 0, 0));
    }

    public Message getMessage() throws Exception {
        String messageText = messageTextArea.getText();
               
        if (messageText.length() <= 256){
            Message msg = new Message(messageTextArea.getText());
            messageTextArea.setText("");
            messageTextArea.requestFocusInWindow();
            return msg;
        }else{
            throw new Exception("Nachricht zu gross, nur 256 Zeichen erlaubt");
        }
    }

    public MessageTextArea getMessageTextArea() {
        return messageTextArea;
    }

    public void setMessageTextArea(MessageTextArea messageTextArea) {
        this.messageTextArea = messageTextArea;
    }

    public void setSendButton(JButton sendButton) {
        this.sendButton = sendButton;
    }

    public JButton getSendButton() {
        return sendButton;
    }

    public JButton getClearChatButton() {
        return clearChatButton;
    }

    public void setClearChatButton(JButton clearChatButton) {
        this.clearChatButton = clearChatButton;
    }
    

    private class EnterKeyListener implements KeyListener {
        @Override
        public void keyPressed(KeyEvent e) {
            int code = e.getKeyCode();
            int modifiers = e.getModifiers();
            if (code == KeyEvent.VK_ENTER && modifiers == KeyEvent.CTRL_MASK) {
                sendButton.doClick();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {}
        
        @Override
        public void keyTyped(KeyEvent e) {
        }
    }
 
}
