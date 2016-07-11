/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.chatview;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemListener;
import java.util.List;
import javax.swing.JCheckBox;
import model.data.User;
import view.AbstractPanel;

/**
 *
 * @author XMBomb
 */
public class OnlineUserListPanel extends AbstractPanel{
    private List<User> receivers;
    
    private ItemListener receiversCheckBoxItemListener;
    
    public OnlineUserListPanel(List<User> userList) {
        super(new GridBagLayout());
        setUserList(userList);
    }

    public final void setUserList(List<User> receivers) {
        this.receivers = receivers;
        removeAll();
        
        for (int i = 0; i < receivers.size(); i++) {
            JCheckBox userCheckBox = new JCheckBox(receivers.get(i).getUsername());
            userCheckBox.addItemListener(receiversCheckBoxItemListener);
            add(userCheckBox, new GridBagConstraints(0, i, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
        }
    }

    public List<User> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<User> receivers) {
        this.receivers = receivers;
    }

    public void setReceiversCheckBoxItemListener(ItemListener itemListener) {
        this.receiversCheckBoxItemListener = itemListener;
    }

    public ItemListener getCheckBoxItemListener() {
        return receiversCheckBoxItemListener;
    }
}
