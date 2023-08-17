package com.ircnet.library.service.event;

import com.ircnet.library.service.connection.IRCServiceConnection;
import lombok.Getter;

@Getter
public class NickChangeEvent extends AbstractServiceEvent {
    private String uid;
    private String newNick;

    public NickChangeEvent(IRCServiceConnection ircConnection, String uid, String newNick) {
        this.ircConnection = ircConnection;
        this.uid = uid;
        this.newNick = newNick;
    }
}
