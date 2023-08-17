package com.ircnet.library.service.event;

import com.ircnet.library.common.User;
import com.ircnet.library.service.connection.IRCServiceConnection;
import lombok.Getter;

import java.util.Map;

@Getter
public class SQueryEvent extends AbstractServiceEvent {
    private User from;
    private String message;

    public SQueryEvent(IRCServiceConnection ircConnection, Map<String, String> messageTags, User from, String message) {
        this.ircConnection = ircConnection;
        this.messageTags = messageTags;
        this.from = from;
        this.message = message;
    }
}
