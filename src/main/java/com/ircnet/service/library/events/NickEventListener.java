package com.ircnet.service.library.events;

import com.ircnet.common.library.event.AbstractEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NickEventListener extends AbstractEventListener<NickEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(NickEventListener.class);

    protected void onEvent(NickEvent event) {
        LOGGER.trace("NickEvent nick={} user={} host={} realName={} serverName={} hopCount={} userModes={}", event.getNick(), event.getUser(), event.getHost(), event.getRealName(), event.getServerName(), event.getHopCount(), event.getUserModes());
    }
}
