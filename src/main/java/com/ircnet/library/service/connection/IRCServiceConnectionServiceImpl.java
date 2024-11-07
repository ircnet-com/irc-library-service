package com.ircnet.library.service.connection;

import com.ircnet.library.common.SettingService;
import com.ircnet.library.common.connection.IRCConnectionServiceImpl;
import com.ircnet.library.common.connection.ResolveService;
import com.ircnet.library.common.event.EventBus;
import com.ircnet.library.parser.Parser;

public class IRCServiceConnectionServiceImpl extends IRCConnectionServiceImpl {
    public IRCServiceConnectionServiceImpl(EventBus eventBus, Parser parser, SettingService settingService, ResolveService resolveService) {
        super(eventBus, parser, settingService, resolveService);
    }
}
