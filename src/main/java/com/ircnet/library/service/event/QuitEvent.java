package com.ircnet.library.service.event;

import com.ircnet.library.service.connection.IRCServiceConnection;
import lombok.Getter;

@Getter
public class QuitEvent extends AbstractServiceEvent {
    private String uid;
    private String message;

    public QuitEvent(IRCServiceConnection ircConnection, String uid, String message) {
        this.ircConnection = ircConnection;
        this.uid = uid;
        this.message = message;
    }
}
