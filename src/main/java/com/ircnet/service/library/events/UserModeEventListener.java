package com.ircnet.service.library.events;

import com.ircnet.common.library.event.AbstractEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserModeEventListener extends AbstractEventListener<UserModeEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserModeEventListener.class);

    protected void onEvent(UserModeEvent event) {
        LOGGER.trace("UserModeEventListener nick={} modes={}", event.getNick(), event.getModes());
    }
}
