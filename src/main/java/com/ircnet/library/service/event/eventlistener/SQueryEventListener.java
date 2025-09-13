package com.ircnet.library.service.event.eventlistener;

import com.ircnet.library.common.connection.IRCConnectionService;
import com.ircnet.library.common.connection.SingletonIRCConnectionService;
import com.ircnet.library.common.event.AbstractEventListener;
import com.ircnet.library.service.ServiceConfigurationModel;
import com.ircnet.library.service.event.SQueryEvent;
import com.ircnet.library.service.squery.SQueryCommand;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Map;

public class SQueryEventListener extends AbstractEventListener<SQueryEvent, IRCConnectionService> {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LoggerFactory.getLogger(SQueryEventListener.class);
    private final ServiceConfigurationModel properties;
    private final Map<String, SQueryCommand> squeryCommandMap;

    public SQueryEventListener(SingletonIRCConnectionService ircConnectionService,
                               ServiceConfigurationModel properties,
                               @Qualifier("squeryCommandMap") Map<String, SQueryCommand> squeryCommandMap) {
        super(ircConnectionService);
        this.properties = properties;
        this.squeryCommandMap = squeryCommandMap;
    }

    protected void onEvent(SQueryEvent event) {
        String nick = event.getFrom().getNick();

        if (StringUtils.isEmpty(event.getMessage())) {
            return;
        }

        String[] parts = event.getMessage().split(" ");
        SQueryCommand<IRCConnectionService> squeryCommand = squeryCommandMap.get(parts[0]);

        if(squeryCommand != null) {
            squeryCommand.processCommand(event.getIRCConnection(), event.getFrom(),
                    event.getMessage(), event.getMessageTags());
        }

        else {
            ircConnectionService.notice(event.getIRCConnection(), nick,
                    "Unrecognized command: \"%s\". Use /SQUERY %s HELP\n", parts[0], properties.getName());
        }
    }
}
