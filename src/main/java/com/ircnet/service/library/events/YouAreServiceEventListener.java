package com.ircnet.service.library.events;

import com.ircnet.common.library.event.AbstractEventListener;
import com.ircnet.service.library.IRCService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class YouAreServiceEventListener extends AbstractEventListener<YouAreServiceEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(YouAreServiceEventListener.class);

    protected void onEvent(YouAreServiceEvent event) {
        LOGGER.trace("Service connected as {}", event.getServiceName());

        IRCService ircService = event.getIRCService();

        StringBuilder servSetCommand = new StringBuilder("SERVSET ");
        servSetCommand.append("0x");
        servSetCommand.append(Integer.toHexString(ircService.getServiceConfigurationModel().getDataFlags()));

        if(ircService.getServiceConfigurationModel().getBurstFlags() != 0) {
            // Add flags for requested burst
            servSetCommand.append(" 0x");
            servSetCommand.append(Integer.toHexString(ircService.getServiceConfigurationModel().getBurstFlags()));
        }

        event.getIRCConnection().send(servSetCommand.toString());
    }
}
