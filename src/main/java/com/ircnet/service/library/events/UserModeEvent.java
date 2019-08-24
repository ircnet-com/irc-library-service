package com.ircnet.service.library.events;

import com.ircnet.service.library.IRCService;
import com.ircnet.service.library.connection.IRCServiceConnection;

public class UserModeEvent extends AbstractServiceEvent {
    private String nick;
    private String modes;

    public UserModeEvent(IRCService ircService, IRCServiceConnection ircConnection, String nick, String modes) {
        this.ircService = ircService;
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
