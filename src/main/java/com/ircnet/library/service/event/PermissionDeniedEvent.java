package com.ircnet.library.service.event;

import com.ircnet.library.service.connection.IRCServiceConnection;
import lombok.Getter;

@Getter
public class PermissionDeniedEvent extends AbstractServiceEvent {
    private String serverName;
    private String message;

    public PermissionDeniedEvent(IRCServiceConnection ircConnection, String serverName, String message) {
        this.ircConnection = ircConnection;
        this.serverName = serverName;
        this.message = message;
    }
}
