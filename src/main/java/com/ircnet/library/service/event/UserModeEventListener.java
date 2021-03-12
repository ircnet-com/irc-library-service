package com.ircnet.library.service.event;

import com.ircnet.library.common.event.AbstractEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("defaultUserModeEventListener")
public class UserModeEventListener extends AbstractEventListener<UserModeEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserModeEventListener.class);

    protected void onEvent(UserModeEvent event) {
        LOGGER.trace("UserModeEventListener nick={} modes={}", event.getNick(), event.getModes());
    }
}
