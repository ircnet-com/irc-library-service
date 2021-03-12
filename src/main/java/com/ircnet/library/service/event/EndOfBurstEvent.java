package com.ircnet.library.service.event;

import com.ircnet.library.service.connection.IRCServiceConnection;

public class EndOfBurstEvent extends AbstractServiceEvent {
    public EndOfBurstEvent(IRCServiceConnection ircConnection) {
        this.ircConnection = ircConnection;
    }
}
