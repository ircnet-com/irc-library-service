package com.ircnet.library.service.event;

import com.ircnet.library.service.connection.IRCServiceConnection;

public class UserModeEvent extends AbstractServiceEvent {
    private String nick;
    private String modes;

    public UserModeEvent(IRCServiceConnection ircConnection, String nick, String modes) {
        this.ircConnection = ircConnection;
        this.nick = nick;
        this.modes = modes;
    }

    public String getNick() {
        return nick;
    }

    public String getModes() {
        return modes;
    }
}
