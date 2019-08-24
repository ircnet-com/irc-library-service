package com.ircnet.library.service.event;

import com.ircnet.library.common.User;
import com.ircnet.library.service.IRCService;
import com.ircnet.library.service.connection.IRCServiceConnection;

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
