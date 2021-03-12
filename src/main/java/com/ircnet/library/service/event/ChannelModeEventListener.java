package com.ircnet.library.service.event;

import com.ircnet.library.common.event.AbstractEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("defaultChannelModeEventListener")
public class ChannelModeEventListener extends AbstractEventListener<ChannelModeEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChannelModeEventListener.class);

    protected void onEvent(ChannelModeEvent event) {
        LOGGER.trace("ChannelModeEvent channelName={} modes={}", event.getChannelName(), event.getModes());
    }
}
