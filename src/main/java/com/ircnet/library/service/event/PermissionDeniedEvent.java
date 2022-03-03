package com.ircnet.library.service.event;

import com.ircnet.library.service.connection.IRCServiceConnection;

public class PermissionDeniedEvent extends AbstractServiceEvent {
    private String serverName;
    private String message;

    public PermissionDeniedEvent(IRCServiceConnection ircConnection, String serverName, String message) {
        this.ircConnection = ircConnection;
        this.serverName = serverName;
        this.message = message;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
