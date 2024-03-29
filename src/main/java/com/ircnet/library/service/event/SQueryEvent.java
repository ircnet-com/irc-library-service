package com.ircnet.library.service.event;

import com.ircnet.library.common.User;
import com.ircnet.library.service.connection.IRCServiceConnection;

public class SQueryEvent extends AbstractServiceEvent {
    private User from;
    private String message;

    public SQueryEvent(IRCServiceConnection ircConnection, User from, String message) {
        this.ircConnection = ircConnection;
        this.from = from;
        this.message = message;
    }

    public User getFrom() {
        return from;
    }

    public String getMessage() {
        return message;
    }
}
