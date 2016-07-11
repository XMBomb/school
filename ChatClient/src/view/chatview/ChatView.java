/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.chatview;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import model.data.IncomingMessage;
import view.AbstractPanel;

/**
 *
 * @author milan.bharanya
 */
public class ChatView extends AbstractPanel{
    
    private MessageContainerPanel messageContainerPanel = new MessageContainerPanel();
    private int messageCount = 0;
    private JScrollBar verticalScrollPane;

    public ChatView() {
        super(new GridBagLayout());

        messageContainerPanel = new MessageContainerPanel();

        JScrollPane messageScrollPane = new JScrollPane(messageContainerPanel);

        verticalScrollPane = messageScrollPane.getVerticalScrollBar();
        verticalScrollPane.setUnitIncrement(16);        

        add(messageScrollPane,                      new GridBagConstraints(0,    0, 1, 1, 1.0, 1.0, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,              new Insets(0, 0, 0, 0), 0, 0));
        messageContainerPanel.add(new JPanel(),     new GridBagConstraints(0, 1000, 1, 1, 1.0, 1.0, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,              new Insets(5, 5, 5, 5), 0, 0));
    }
    
    
    public void addIncomingMessage(IncomingMessage message){
        messageContainerPanel.add(new MessagePanel(message), new GridBagConstraints(0, messageCount++, 1, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    }
    
    public void scrollToBottom(){
        verticalScrollPane.setValue(verticalScrollPane.getMaximum());
    }

    public MessageContainerPanel getMessageContainerPanel() {
        return messageContainerPanel;
    }

    public void setMessageContainerPanel(MessageContainerPanel messageContainerPanel) {
        this.messageContainerPanel = messageContainerPanel;
    }
    

}
