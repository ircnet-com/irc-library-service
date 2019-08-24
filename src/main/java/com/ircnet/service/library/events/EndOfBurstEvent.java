package com.ircnet.service.library.events;

import com.ircnet.service.library.IRCService;
import com.ircnet.service.library.connection.IRCServiceConnection;

public class EndOfBurstEvent extends AbstractServiceEvent {
    public EndOfBurstEvent(IRCService ircService, IRCServiceConnection ircConnection) {
        this.ircService = ircService;
        this.ircConnection = ircConnection;
    }
}
