/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author XMBomb
 */
public abstract class SendReceive {

    private Socket socket;
    private Sender sender;
    private Receiver receiver;

    public SendReceive() {

    }
    
    public void connect(String hostname, int port) {
        try {
            socket = new Socket(hostname, port);
            sender = new Sender(this, socket);
            sender.start();

            receiver = new Receiver(this, socket);
            receiver.start();
        } catch (UnknownHostException ex) {
            handleError(ex);
        } catch (IOException ex) {
            handleError(ex);
        }
    }


    public void send(Serializable message) {
        sender.sendMessage(message);
    }

    abstract public void receive(Object message);

    abstract public void handleError(Exception e);
    

    public void disconnect() {
        if(this.sender != null){
            this.sender.interrupt();
        }
        if (this.receiver !=null){
            this.receiver.interrupt();
        }
        try {
            if(this.socket != null){
                this.socket.close();
            }
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
    }

}
