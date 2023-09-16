package com.ircnet.library.service.event;

import com.ircnet.library.service.connection.IRCServiceConnection;
import lombok.Getter;

@Getter
public class SQuitEvent extends AbstractServiceEvent {
    private String sender;
    private String serverName;
    private String reason;

    public SQuitEvent(IRCServiceConnection ircConnection, String sender, String serverName, String reason) {
        this.ircConnection = ircConnection;
        this.sender = sender;
        this.serverName = serverName;
        this.reason = reason;
    }
}
