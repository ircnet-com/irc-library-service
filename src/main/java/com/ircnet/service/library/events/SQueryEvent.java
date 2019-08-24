package com.ircnet.service.library.events;

import com.ircnet.common.library.User;
import com.ircnet.service.library.IRCService;
import com.ircnet.service.library.connection.IRCServiceConnection;

public class SQueryEvent extends AbstractServiceEvent {
    private User from;
    private String message;

    public SQueryEvent(IRCService ircService, IRCServiceConnection ircConnection, User from, String message) {
        this.ircService = ircService;
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
