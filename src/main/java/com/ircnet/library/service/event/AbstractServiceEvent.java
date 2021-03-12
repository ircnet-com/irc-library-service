package com.ircnet.library.service.event;

import com.ircnet.library.common.event.AbstractEvent;
import com.ircnet.library.service.connection.IRCServiceConnection;

public class AbstractServiceEvent extends AbstractEvent<IRCServiceConnection> {
    public AbstractServiceEvent() {
        super();
    }

    public AbstractServiceEvent(Object source) {
        super(source);
    }
}
