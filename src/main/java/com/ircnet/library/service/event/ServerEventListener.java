package com.ircnet.library.service.event;

import com.ircnet.library.common.event.AbstractEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerEventListener extends AbstractEventListener<ServerEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerEventListener.class);

    protected void onEvent(ServerEvent event) {
        LOGGER.trace("ServerEvent serverName={} hopCount={} sid={} info='{}'", event.getServerName(), event.getHopCount(), event.getSid(), event.getInfo());
    }
}
