package com.ircnet.library.service.event;

import com.ircnet.library.common.event.AbstractEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("defaultQuitEventListener")
public class QuitEventListener extends AbstractEventListener<QuitEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuitEventListener.class);

    protected void onEvent(QuitEvent event) {
        LOGGER.trace("QuitEvent uid={} message={}", event.getUid(), event.getMessage());
    }
}
