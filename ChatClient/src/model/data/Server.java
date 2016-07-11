/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.data;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author XMBomb
 */
public class Server implements Serializable{
    private LoggedInUser User;
    private String hostname;
    private int port;

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setUser(LoggedInUser User) {
        this.User = User;
    }

    public LoggedInUser getUser() {
        return User;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.User);
        hash = 97 * hash + Objects.hashCode(this.hostname);
        hash = 97 * hash + this.port;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Server other = (Server) obj;
        if (!Objects.equals(this.User.getUsername(), other.User.getUsername())) {
            return false;
        }
        if (!Objects.equals(this.hostname, other.hostname)) {
            return false;
        }
        if (this.port != other.port) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getUser().getUsername() + "@" + getHostname() + ":" + getPort();
    }
}
