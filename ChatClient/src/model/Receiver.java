/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * Objectouputstream getObject Message an SendReceive
 *
 * @author
 * @author milan.bharanya
 */
class Receiver extends Thread {

    private SendReceive sendReceive;
    private Socket socket;

    public Receiver(SendReceive sendReceive, Socket socket) {
        super("Receiver");
        this.socket = socket;
        this.sendReceive = sendReceive;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            while (!isInterrupted()) {
                Object message = (Object) inputStream.readUnshared();
                sendReceive.receive(message);
            }
        }catch (NullPointerException ne){
            sendReceive.handleError(ne);
        }
        catch (IOException | ClassNotFoundException ex) {
            sendReceive.handleError(ex);
        }
    }
    
}
