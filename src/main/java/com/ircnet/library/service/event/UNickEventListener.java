package com.ircnet.library.service.event;

import com.ircnet.library.common.event.AbstractEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("defaultUNickEventListener")
public class UNickEventListener extends AbstractEventListener<UNickEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(UNickEventListener.class);

    protected void onEvent(UNickEvent event) {
        LOGGER.trace("UNickEvent sid={} nick={} user={} host={} userModes={} realName={}",
                event.getSid(), event.getNick(), event.getUser(), event.getHost(), event.getUserModes(),
                event.getRealName());
    }
}
