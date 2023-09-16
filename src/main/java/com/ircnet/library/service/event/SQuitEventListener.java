package com.ircnet.library.service.event;

import com.ircnet.library.common.event.AbstractEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("defaultSQuitEventListener")
public class SQuitEventListener extends AbstractEventListener<SQuitEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SQuitEventListener.class);

    protected void onEvent(SQuitEvent event) {
        LOGGER.trace("SQuitEvent sender={} severName={} reason='{}'", event.getSender(), event.getServerName(), event.getReason());
    }
}
