package com.ircnet.library.service;

import com.ircnet.library.common.IRCCommonConfiguration;
import com.ircnet.library.common.SettingService;
import com.ircnet.library.common.connection.*;
import com.ircnet.library.common.event.AbstractEventListener;
import com.ircnet.library.common.event.EventBus;
import com.ircnet.library.common.parser.Parser;
import com.ircnet.library.service.connection.IRCServiceConnection;
import com.ircnet.library.service.event.service.ConnectionStatusChangedHandlerImpl;
import com.ircnet.library.service.event.service.EventRegistrationService;
import com.ircnet.library.service.parser.ParserImpl;
import com.ircnet.library.service.squery.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.util.List;
import java.util.Map;

@Configuration
@Import({IRCCommonConfiguration.class})
@ComponentScan(basePackages = {"com.ircnet.library.service.event.eventlistener", "com.ircnet.library.service.squery"})
public class IRCServiceConfiguration {
    @Bean
    public ParserImpl parser(@Lazy IRCConnectionService ircConnectionService,
                             EventBus eventBus,
                             @Lazy ConnectionStatusChangedHandlerImpl connectionStatusChangedHandler) {
        return new ParserImpl(ircConnectionService, eventBus, connectionStatusChangedHandler);
    }

    @ConditionalOnBean(IRCServiceConnection.class)
    @Bean
    public SingletonIRCConnectionServiceImpl singletonIRCConnectionService(EventBus eventBus,
                                                                           Parser<IRCServiceConnection> parser,
                                                                           SettingService settingService,
                                                                           ResolveService resolveService,
                                                                           IRCConnection ircConnection,
                                                                           @Lazy ConnectionStatusChangedHandler connectionStatusChangedHandler) {
        return new SingletonIRCConnectionServiceImpl(eventBus, parser, settingService, resolveService, ircConnection,
                connectionStatusChangedHandler);
    }

    @Bean
    public ConnectionStatusChangedHandlerImpl connectionStatusChangedHandler(IRCConnectionService ircConnectionService,
                                                                             SettingService settingService) {
        return new ConnectionStatusChangedHandlerImpl(ircConnectionService, settingService);
    }

    @Bean
    public EventRegistrationService eventRegistrationService(EventBus eventBus,
                                                             List<AbstractEventListener> eventListeners) {
        return new EventRegistrationService(eventBus, eventListeners);
    }

    @Bean
    @ConditionalOnMissingBean(SQueryCommandAdmin.class)
    @Order(Integer.MAX_VALUE - 3)
    public SQueryCommandAdmin squeryCommandAdmin(IRCConnectionService ircConnectionService,
                                                 ServiceConfigurationModel properties) {
        return new SQueryCommandAdmin(ircConnectionService, properties);
    }

    @Bean
    @ConditionalOnMissingBean(SQueryCommandInfo.class)
    @Order(Integer.MAX_VALUE - 2)
    public SQueryCommandInfo squeryCommandInfo(IRCConnectionService ircConnectionService,
                                               ServiceConfigurationModel properties) {
        return new SQueryCommandInfo(ircConnectionService, properties);
    }

    @Bean
    @ConditionalOnMissingBean(SQueryCommandVersion.class)
    @Order(Integer.MAX_VALUE - 1)
    public SQueryCommandVersion squeryCommandVersion(IRCConnectionService ircConnectionService,
                                                     ServiceConfigurationModel properties,
                                                     AppVersionProvider appVersionProvider) {
        return new SQueryCommandVersion(ircConnectionService, properties, appVersionProvider);
    }

    @Bean
    @ConditionalOnMissingBean(SQueryCommandHelp.class)
    @Order
    public SQueryCommandHelp squeryCommandHelp(IRCConnectionService ircConnectionService,
                                               @Lazy @Qualifier("squeryCommandMap") Map<String, SQueryCommand> squeryCommandMap,
                                               ServiceConfigurationModel properties) {
        return new SQueryCommandHelp(ircConnectionService, squeryCommandMap, properties);
    }

    @Bean("squeryCommandMap")
    public Map<String, SQueryCommand> squeryCommandMap(List<SQueryCommand> commands) {
        Map<String, SQueryCommand> commandMap = new LinkedCaseInsensitiveMap<>();
        commands.forEach(command -> commandMap.put(command.getName(), command));
        return commandMap;
    }
}
