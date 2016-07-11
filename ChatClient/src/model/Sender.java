/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author XMBomb
 */
public class Sender extends Thread {

    private SendReceive sendReceive;
    private Socket socket;
    private List<Serializable> messageQueue = new ArrayList<>();

    public Sender(SendReceive sendReceive, Socket socket) {
        super("Sender");
        this.sendReceive = sendReceive;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            while (!isInterrupted()) {
                Serializable message = null;
                synchronized (messageQueue) {
                    if (!messageQueue.isEmpty()) {
                        message = messageQueue.get(0);
                        messageQueue.remove(0);
                    }
                }
                if (message != null) {
                    outputStream.writeUnshared(message);
                    outputStream.flush();
                }
                synchronized (messageQueue) {
                    if (!messageQueue.isEmpty()) {
                        continue;
                    }
                }
                synchronized (this) {
                    try {
                        wait();
                    } catch (InterruptedException ie) {
                        return;
                    }
                }

            }
        } catch (UnknownHostException ex) {
            sendReceive.handleError(ex);
        } catch (IOException ex) {
            sendReceive.handleError(ex);
        }
    }

    public void sendMessage(Serializable messageObject) {
        synchronized (messageQueue) {
            messageQueue.add(messageObject);
        }

        synchronized (this) {
            notify();
        }
    }
}
