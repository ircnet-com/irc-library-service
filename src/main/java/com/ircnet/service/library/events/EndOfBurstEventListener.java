package com.ircnet.service.library.events;

import com.ircnet.common.library.event.AbstractEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EndOfBurstEventListener extends AbstractEventListener<EndOfBurstEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(EndOfBurstEventListener.class);

    protected void onEvent(EndOfBurstEvent event) {
        LOGGER.trace("End of burst");
        event.getIRCConnection().setBurst(false);
    }
}
