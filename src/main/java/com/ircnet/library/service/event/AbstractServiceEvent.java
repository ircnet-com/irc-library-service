package com.ircnet.library.service.event;

import com.ircnet.library.common.event.AbstractEvent;
import com.ircnet.library.service.IRCService;
import com.ircnet.library.service.connection.IRCServiceConnection;

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
