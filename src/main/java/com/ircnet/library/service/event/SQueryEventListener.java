package com.ircnet.library.service.event;

import com.ircnet.library.common.event.AbstractEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SQueryEventListener extends AbstractEventListener<SQueryEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SQueryEventListener.class);

    protected void onEvent(SQueryEvent event) {
        LOGGER.trace("SQueryEvent nick={} message='{}'", event.getFrom().getNick(), event.getMessage());
    }
}
