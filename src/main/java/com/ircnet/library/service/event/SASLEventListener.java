package com.ircnet.library.service.event;

import com.ircnet.library.common.event.AbstractEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("defaultSASLEventListener")
public class SASLEventListener extends AbstractEventListener<SASLEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SASLEventListener.class);

    protected void onEvent(SASLEvent event) {
        LOGGER.trace("SASLEvent serverName={} uidNick={} type={} data='{}'", event.getServerName(), event.getUidNick(),
                event.getType(), event.getData());
    }
}
