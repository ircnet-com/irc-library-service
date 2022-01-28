package com.ircnet.library.service.event;

import com.ircnet.library.common.event.AbstractEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("defaultNickChangeEventListener")
public class NickChangedEventListener extends AbstractEventListener<NickChangeEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(NickChangedEventListener.class);

    protected void onEvent(NickChangeEvent event) {
        LOGGER.trace("NickEvent uid={} newNick={}", event.getUid(), event.getNewNick());
    }
}
