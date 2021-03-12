package com.ircnet.library.service.event;

import com.ircnet.library.common.event.AbstractEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("defaultChannelEventListener")
public class ChannelEventListener extends AbstractEventListener<ChannelEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChannelEventListener.class);

    protected void onEvent(ChannelEvent event) {
        LOGGER.trace("ChannelEvent channelName={} userCount={}", event.getChannelName(), event.getUserCount());
    }
}
