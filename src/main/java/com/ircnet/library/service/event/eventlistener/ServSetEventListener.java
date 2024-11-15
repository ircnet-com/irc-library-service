package com.ircnet.library.service.event.eventlistener;

import com.ircnet.library.common.connection.IRCConnectionService;
import com.ircnet.library.common.event.AbstractEventListener;
import com.ircnet.library.service.event.ServSetEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class ServSetEventListener extends AbstractEventListener<ServSetEvent, IRCConnectionService> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServSetEventListener.class);

    public ServSetEventListener(IRCConnectionService ircConnectionService) {
        super(ircConnectionService);
    }

    protected void onEvent(ServSetEvent event) {
        LOGGER.trace("Accepted SERVSET: {}", "0x" + Integer.toHexString(event.getAcceptedSettings()));

        if(event.getIRCConnection().getServiceConfiguration().getBurstFlags() != 0) {
            LOGGER.trace("Expecting burst now");
            event.getIRCConnection().setBurst(true);
            event.getIRCConnection().setBurstStart(new Date());
        }
    }
}
