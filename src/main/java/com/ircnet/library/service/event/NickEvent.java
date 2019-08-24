package com.ircnet.library.service.event;

import com.ircnet.library.service.IRCService;
import com.ircnet.library.service.connection.IRCServiceConnection;

public class NickEvent extends AbstractServiceEvent {
    private String nick;
    private String user;
    private String host;
    private String realName;

    private String serverName;
    private int hopCount;
    private String userModes;

    public NickEvent(IRCService ircService, IRCServiceConnection ircConnection, String nick, String user, String host, String realName, String serverName, int hopCount, String userModes) {
        this.ircService = ircService;
        this.ircConnection = ircConnection;
        this.nick = nick;
        this.user = user;
        this.host = host;
        this.realName = realName;
        this.serverName = serverName;
        this.hopCount = hopCount;
        this.userModes = userModes;
    }

    public String getNick() {
        return nick;
    }

    public String getUser() {
        return user;
    }

    public String getHost() {
        return host;
    }

    public String getRealName() {
        return realName;
    }

    public String getServerName() {
        return serverName;
    }

    public int getHopCount() {
        return hopCount;
    }

    public String getUserModes() {
        return userModes;
    }
}
