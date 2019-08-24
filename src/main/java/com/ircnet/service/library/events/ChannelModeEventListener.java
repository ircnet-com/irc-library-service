package com.ircnet.service.library.events;

import com.ircnet.common.library.event.AbstractEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChannelModeEventListener extends AbstractEventListener<ChannelModeEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChannelModeEventListener.class);

    protected void onEvent(ChannelModeEvent event) {
        LOGGER.trace("ChannelModeEvent channelName={} modes={}", event.getChannelName(), event.getModes());
    }
}
