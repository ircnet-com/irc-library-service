package com.ircnet.library.service.event;

import com.ircnet.library.service.connection.IRCServiceConnection;
import lombok.Getter;

@Getter
public class YouAreServiceEvent extends AbstractServiceEvent {
    private String serviceName;

    public YouAreServiceEvent(IRCServiceConnection ircConnection, String serviceName) {
        this.ircConnection = ircConnection;
        this.serviceName = serviceName;
    }
}
