package com.ircnet.library.service.event;

import com.ircnet.library.common.connection.ConnectionStatus;
import com.ircnet.library.common.connection.IRCConnectionService;
import com.ircnet.library.common.event.AbstractEventListener;
import com.ircnet.library.service.ServiceConfigurationModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("defaultYouAreServiceEventListener")
public class YouAreServiceEventListener extends AbstractEventListener<YouAreServiceEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(YouAreServiceEventListener.class);

    @Autowired
    private IRCConnectionService ircConnectionService;

    protected void onEvent(YouAreServiceEvent event) {
        event.getIRCConnection().setConnectionStatus(ConnectionStatus.REGISTERED);
        LOGGER.trace("Service connected as {}", event.getServiceName());

        ServiceConfigurationModel config = event.getIRCConnection().getServiceConfiguration();

        StringBuilder servSetCommand = new StringBuilder("SERVSET ");
        servSetCommand.append("0x");
        servSetCommand.append(Integer.toHexString(config.getDataFlags()));

        if(config.getBurstFlags() != 0) {
            // Add flags for requested burst
            servSetCommand.append(" 0x");
            servSetCommand.append(Integer.toHexString(config.getBurstFlags()));
        }

        ircConnectionService.send(event.getIRCConnection(), servSetCommand.toString());
    }
}
