package com.ircnet.library.service.event;

import com.ircnet.library.common.event.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component("ServiceLibraryEventRegistrant")
public class EventRegistrant {
    @Autowired
    private EventBus eventBus;

    @Autowired
    private ConnectionStatusChangedEventListener connectionStatusChangedEventListener;

    @Autowired
    private YouAreServiceEventListener youAreServiceEventListener;

    @Autowired
    private EndOfBurstEventListener endOfBurstEventListener;

    @Autowired
    private ServSetEventListener servSetEventListener;

    @PostConstruct
    public void initEventListeners() {
        eventBus.registerEventListener(0, connectionStatusChangedEventListener);
        eventBus.registerEventListener(0, youAreServiceEventListener);
        eventBus.registerEventListener(0, endOfBurstEventListener);
        eventBus.registerEventListener(0, servSetEventListener);
    }
}
