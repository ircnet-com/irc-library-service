package com.ircnet.library.service;

import com.ircnet.library.common.IRCCommonConfiguration;
import com.ircnet.library.common.SettingService;
import com.ircnet.library.common.connection.IRCConnectionService;
import com.ircnet.library.common.event.EventBus;
import com.ircnet.library.service.event.eventlistener.ConnectionStatusChangedEventListener;
import com.ircnet.library.service.event.eventlistener.EndOfBurstEventListener;
import com.ircnet.library.service.event.eventlistener.ServSetEventListener;
import com.ircnet.library.service.event.eventlistener.YouAreServiceEventListener;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ IRCCommonConfiguration.class })
public class IRCServiceEventConfiguration {
    @Autowired
    private EventBus eventBus;

    @Autowired
    private IRCConnectionService ircConnectionService;

    @Autowired
    private SettingService settingService;

    @PostConstruct
    public void registerEventListeners() {
        eventBus.registerEventListener(0, new ConnectionStatusChangedEventListener(ircConnectionService, settingService));
        eventBus.registerEventListener(0, new YouAreServiceEventListener(ircConnectionService));
        eventBus.registerEventListener(0, new EndOfBurstEventListener());
        eventBus.registerEventListener(0, new ServSetEventListener());
    }
}
