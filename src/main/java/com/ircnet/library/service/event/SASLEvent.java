package com.ircnet.library.service.event;

import com.ircnet.library.service.connection.IRCServiceConnection;

public class SASLEvent extends AbstractServiceEvent {
    private String uidNick;
    private String type;
    private String data;

    public SASLEvent(IRCServiceConnection ircConnection, String uidNick, String type, String data) {
        this.ircConnection = ircConnection;
        this.uidNick = uidNick;
        this.type = type;
        this.data = data;
    }

    public String getUidNick() {
        return uidNick;
    }

    public void setUidNick(String uidNick) {
        this.uidNick = uidNick;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
