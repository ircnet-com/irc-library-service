package com.ircnet.library.service;

import com.ircnet.library.common.IRCCommonConfiguration;
import com.ircnet.library.common.SettingService;
import com.ircnet.library.common.connection.IRCConnection;
import com.ircnet.library.common.connection.ResolveService;
import com.ircnet.library.common.connection.SingletonIRCConnectionServiceImpl;
import com.ircnet.library.common.event.EventBus;
import com.ircnet.library.common.parser.Parser;
import com.ircnet.library.service.connection.IRCServiceConnection;
import com.ircnet.library.service.parser.ParserImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({IRCCommonConfiguration.class})
public class IRCServiceConfiguration {
    @Bean
    public ParserImpl parser(EventBus eventBus) {
        return new ParserImpl(eventBus);
    }

    @ConditionalOnBean(IRCServiceConnection.class)
    @Bean
    public SingletonIRCConnectionServiceImpl singletonIRCConnectionService(EventBus eventBus,
                                                                           Parser<IRCServiceConnection> parser,
                                                                           SettingService settingService,
                                                                           ResolveService resolveService,
                                                                           IRCConnection ircConnection) {
        return new SingletonIRCConnectionServiceImpl(eventBus, parser, settingService, resolveService, ircConnection);
    }
}
