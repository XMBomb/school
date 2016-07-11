package view.chatview;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.SimpleDateFormat;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import model.data.IncomingMessage;
import view.AbstractPanel;

/**
 *
 * @author milan.bharanya
 */
public class MessagePanel extends AbstractPanel {
    
    public MessagePanel(IncomingMessage message) {
        super(new GridBagLayout());
        JLabel userNameLbl = new JLabel(message.getUser().getUsername());
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        JLabel timeLbl = new JLabel("["+timeFormat.format(message.getDate().getTime())+"]");
        JTextArea messageArea = new JTextArea(message.getText());
        messageArea.setEditable(false);
        
        add(userNameLbl,    new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,          new Insets(5, 5, 5, 5), 0, 0));
        add(timeLbl,        new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,          new Insets(5, 5, 5, 5), 0, 0));
        add(messageArea,    new GridBagConstraints(0, 1, 2, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,         new Insets(5, 5, 5, 5), 0, 0));
        
        setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
    }

}
