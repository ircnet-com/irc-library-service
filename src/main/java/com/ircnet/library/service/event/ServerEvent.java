package com.ircnet.library.service.event;

import com.ircnet.library.service.connection.IRCServiceConnection;
import lombok.Getter;

@Getter
public class ServerEvent extends AbstractServiceEvent {
    private String serverName;
    private int hopCount;
    private String sid;
    private String info;

    public ServerEvent(IRCServiceConnection ircConnection, String serverName, int hopCount, String sid, String info) {
        this.ircConnection = ircConnection;
        this.serverName = serverName;
        this.hopCount = hopCount;
        this.sid = sid;
        this.info = info;
    }
}
