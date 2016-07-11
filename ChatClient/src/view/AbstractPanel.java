/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Component;
import java.awt.LayoutManager;
import javax.swing.JPanel;

/**
 *
 * @author XMBomb
 */
public abstract class AbstractPanel extends JPanel{

    public AbstractPanel(LayoutManager layout) {
        super(layout);
    }
    
    @Override
    public void setEnabled(boolean enabled) {
        for(Component cmp : getComponents()){
            cmp.setEnabled(enabled);
        }
        super.setEnabled(enabled);
    }
    
}
