package com.ircnet.library.service.event;

import com.ircnet.library.service.IRCService;
import com.ircnet.library.service.connection.IRCServiceConnection;

public class EndOfBurstEvent extends AbstractServiceEvent {
    public EndOfBurstEvent(IRCService ircService, IRCServiceConnection ircConnection) {
        this.ircService = ircService;
        this.ircConnection = ircConnection;
    }
}
