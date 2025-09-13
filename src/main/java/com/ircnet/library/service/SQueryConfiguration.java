package com.ircnet.library.service;

import com.ircnet.library.common.connection.SingletonIRCConnectionService;
import com.ircnet.library.service.event.eventlistener.SQueryEventListener;
import com.ircnet.library.service.squery.SQueryCommand;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.Map;

@AutoConfiguration(after = IRCServiceConfiguration.class)
public class SQueryConfiguration {
    @Bean
    public SQueryEventListener squeryEventListener(SingletonIRCConnectionService ircConnectionService,
                                                   ServiceConfigurationModel properties,
                                                   @Qualifier("squeryCommandMap") Map<String, SQueryCommand> squeryCommandMap) {
        return new SQueryEventListener(ircConnectionService, properties, squeryCommandMap);
    }
}
