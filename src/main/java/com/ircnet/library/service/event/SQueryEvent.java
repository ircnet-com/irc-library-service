package com.ircnet.library.service.event;

import com.ircnet.library.common.User;
import com.ircnet.library.service.connection.IRCServiceConnection;

import java.util.Map;

public class SQueryEvent extends AbstractServiceEvent {
    private User from;
    private String message;

    public SQueryEvent(IRCServiceConnection ircConnection, Map<String, String> messageTags, User from, String message) {
        this.ircConnection = ircConnection;
        this.messageTags = messageTags;
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
