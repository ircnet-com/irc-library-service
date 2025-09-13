package com.ircnet.library.service.event.service;

import com.ircnet.library.common.event.AbstractEventListener;
import com.ircnet.library.common.event.EventBus;
import jakarta.annotation.PostConstruct;

import java.util.List;

/**
 * Registers event listeners.
 */
public class EventRegistrationService {
    private final EventBus eventBus;
    private final List<AbstractEventListener> eventListeners;
    private final boolean checkInheritance = false;

    public EventRegistrationService(EventBus eventBus, List<AbstractEventListener> eventListeners) {
        this.eventBus = eventBus;
        this.eventListeners = eventListeners;
    }

    @PostConstruct
    public void init() {
        eventBus.setCheckInheritance(checkInheritance);
        eventListeners.forEach(eventBus::registerEventListener);
    }
}
