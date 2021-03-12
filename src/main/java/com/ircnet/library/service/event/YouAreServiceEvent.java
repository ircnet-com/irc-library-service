package com.ircnet.library.service.event;

import com.ircnet.library.service.connection.IRCServiceConnection;

public class YouAreServiceEvent extends AbstractServiceEvent {
    private String serviceName;

    public YouAreServiceEvent(IRCServiceConnection ircConnection, String serviceName) {
        this.ircConnection = ircConnection;
        this.serviceName = serviceName;
    }

    public String getServiceName() {
        return serviceName;
    }
}
