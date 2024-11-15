package com.ircnet.library.service.event.eventlistener;

import com.ircnet.library.common.connection.IRCConnectionService;
import com.ircnet.library.common.event.AbstractEventListener;
import com.ircnet.library.service.event.EndOfBurstEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EndOfBurstEventListener extends AbstractEventListener<EndOfBurstEvent, IRCConnectionService> {
    private static final Logger LOGGER = LoggerFactory.getLogger(EndOfBurstEventListener.class);

    public EndOfBurstEventListener(IRCConnectionService ircConnectionService) {
        super(ircConnectionService);
    }

    protected void onEvent(EndOfBurstEvent event) {
        LOGGER.trace("End of burst");
        event.getIRCConnection().setBurst(false);
    }
}
