package com.ircnet.library.service.event;

import com.ircnet.library.service.connection.IRCServiceConnection;
import lombok.Getter;

@Getter
public class UNickEvent extends AbstractServiceEvent {
    private String sid;
    private String uid;
    private String nick;
    private String user;
    private String host;
    private String ipAddress;
    private String userModes;
    private String account;
    private String realName;

    public UNickEvent(IRCServiceConnection ircConnection, String sid, String uid, String nick, String user, String host,
                      String ipAddress, String userModes, String account, String realName) {
        this.ircConnection = ircConnection;
        this.sid = sid;
        this.uid = uid;
        this.nick = nick;
        this.user = user;
        this.host = host;
        this.ipAddress = ipAddress;
        this.userModes = userModes;
        this.account = account;
        this.realName = realName;
    }
}
