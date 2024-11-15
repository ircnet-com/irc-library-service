package com.ircnet.library.service.event;

import com.ircnet.library.service.connection.IRCServiceConnection;
import lombok.Getter;

@Getter
public class NickEvent extends AbstractServiceEvent {
    private String nick;
    private String user;
    private String host;
    private String realName;
    private String serverName;
    private int hopCount;
    private String userModes;

    public NickEvent(IRCServiceConnection ircConnection, String nick, int hopCount) {
        this.ircConnection = ircConnection;
        this.nick = nick;
        this.hopCount = hopCount;
    }

    public NickEvent(IRCServiceConnection ircConnection, String nick, String user, String host, String realName, String serverName, int hopCount, String userModes) {
        this.ircConnection = ircConnection;
        this.nick = nick;
        this.user = user;
        this.host = host;
        this.realName = realName;
        this.serverName = serverName;
        this.hopCount = hopCount;
        this.userModes = userModes;
    }
}
