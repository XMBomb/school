/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.data;

import java.util.Calendar;
import java.util.List;

/**
 *
 * @author milan.bharanya
 */
public class Message {
    private String text;
    private Calendar date;

    public Message(String text) {
        this.text = text;
        this.date = date.getInstance();
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public Calendar getDate() {
        return date;
    }
    
    


}
