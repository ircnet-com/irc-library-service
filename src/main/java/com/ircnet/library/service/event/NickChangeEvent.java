package com.ircnet.library.service.event;

import com.ircnet.library.service.connection.IRCServiceConnection;

public class NickChangeEvent extends AbstractServiceEvent {
    private String uid;
    private String newNick;

    public NickChangeEvent(IRCServiceConnection ircConnection, String uid, String newNick) {
        this.ircConnection = ircConnection;
        this.uid = uid;
        this.newNick = newNick;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNewNick() {
        return newNick;
    }

    public void setNewNick(String newNick) {
        this.newNick = newNick;
    }
}
