package com.ircnet.library.service.event;

import com.ircnet.library.common.event.AbstractEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("defaultPermissionDeniedListener")
public class PermissionDeniedEventListener extends AbstractEventListener<PermissionDeniedEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PermissionDeniedEventListener.class);

    protected void onEvent(PermissionDeniedEvent event) {
        LOGGER.trace("PermissionDeniedEvent serverName={} message={}", event.getServerName(), event.getMessage());
    }
}
