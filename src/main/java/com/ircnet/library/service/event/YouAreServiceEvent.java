package com.ircnet.library.service.event;

import com.ircnet.library.service.IRCService;
import com.ircnet.library.service.connection.IRCServiceConnection;

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
