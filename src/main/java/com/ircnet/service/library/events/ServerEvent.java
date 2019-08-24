package com.ircnet.service.library.events;

import com.ircnet.service.library.IRCService;
import com.ircnet.service.library.connection.IRCServiceConnection;

public class ServerEvent extends AbstractServiceEvent {
    private String serverName;
    private int hopCount;
    private String sid;
    private String info;

    public ServerEvent(IRCService ircService, IRCServiceConnection ircConnection, String serverName, int hopCount, String sid, String info) {
        this.ircService = ircService;
        this.ircConnection = ircConnection;
        this.serverName = serverName;
        this.hopCount = hopCount;
        this.sid = sid;
        this.info = info;
    }

    public String getServerName() {
        return serverName;
    }

    public int getHopCount() {
        return hopCount;
    }

    public String getSid() {
        return sid;
    }

    public String getInfo() {
        return info;
    }
}
