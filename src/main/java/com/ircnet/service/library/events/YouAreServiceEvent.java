package com.ircnet.service.library.events;

import com.ircnet.service.library.IRCService;
import com.ircnet.service.library.connection.IRCServiceConnection;

public class YouAreServiceEvent extends AbstractServiceEvent {
    private String serviceName;

    public YouAreServiceEvent(IRCService ircService, IRCServiceConnection ircConnection, String serviceName) {
        this.ircService = ircService;
        this.ircConnection = ircConnection;
        this.serviceName = serviceName;
    }

    public String getServiceName() {
        return serviceName;
    }
}
