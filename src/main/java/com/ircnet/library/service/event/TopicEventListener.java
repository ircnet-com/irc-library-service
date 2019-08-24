package com.ircnet.library.service.event;

import com.ircnet.library.common.event.AbstractEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TopicEventListener extends AbstractEventListener<TopicEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TopicEventListener.class);

    protected void onEvent(TopicEvent event) {
        LOGGER.trace("TopicEvent channelName={} topic='{}'", event.getChannelName(), event.getTopic());
    }
}
