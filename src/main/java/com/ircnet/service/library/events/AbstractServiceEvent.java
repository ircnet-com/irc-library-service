package com.ircnet.service.library.events;

import com.ircnet.common.library.event.AbstractEvent;
import com.ircnet.service.library.IRCService;
import com.ircnet.service.library.connection.IRCServiceConnection;

public class AbstractServiceEvent extends AbstractEvent<IRCServiceConnection> {
    protected IRCService ircService;

    public AbstractServiceEvent() {
        super();
    }

    public AbstractServiceEvent(Object source) {
        super(source);
    }

    public IRCService getIRCService() {
        return ircService;
    }

    public void setIRCService(IRCService ircService) {
        this.ircService = ircService;
    }
}
