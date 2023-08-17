package com.ircnet.library.service.event;

import com.ircnet.library.service.connection.IRCServiceConnection;
import lombok.Getter;

@Getter
public class SASLEvent extends AbstractServiceEvent {
    private String uidNick;
    private String type;
    private String data;

    public SASLEvent(IRCServiceConnection ircConnection, String uidNick, String type, String data) {
        this.ircConnection = ircConnection;
        this.uidNick = uidNick;
        this.type = type;
        this.data = data;
    }
}
