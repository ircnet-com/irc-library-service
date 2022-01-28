package com.ircnet.library.service.event;

import com.ircnet.library.service.connection.IRCServiceConnection;

public class QuitEvent extends AbstractServiceEvent {
    private String uid;
    private String message;

    public QuitEvent(IRCServiceConnection ircConnection, String uid, String message) {
        this.ircConnection = ircConnection;
        this.uid = uid;
        this.message = message;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
