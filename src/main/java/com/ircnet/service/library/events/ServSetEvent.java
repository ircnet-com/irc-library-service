package com.ircnet.service.library.events;

import com.ircnet.common.library.User;
import com.ircnet.service.library.IRCService;
import com.ircnet.service.library.connection.IRCServiceConnection;

public class ServSetEvent extends AbstractServiceEvent {
    private User from;
    private int acceptedSettings;

    public ServSetEvent(IRCService ircService, IRCServiceConnection ircConnection, User from, int acceptedSettings) {
        this.ircService = ircService;
        this.ircConnection = ircConnection;
        this.from = from;
        this.acceptedSettings = acceptedSettings;
    }

    public User getFrom() {
        return from;
    }

    public int getAcceptedSettings() {
        return acceptedSettings;
    }
}
