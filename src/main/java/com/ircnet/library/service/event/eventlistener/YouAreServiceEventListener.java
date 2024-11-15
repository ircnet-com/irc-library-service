package com.ircnet.library.service.event.eventlistener;

import com.ircnet.library.common.connection.ConnectionStatus;
import com.ircnet.library.common.connection.IRCConnectionService;
import com.ircnet.library.common.event.AbstractEventListener;
import com.ircnet.library.service.ServiceConfigurationModel;
import com.ircnet.library.service.event.YouAreServiceEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class YouAreServiceEventListener extends AbstractEventListener<YouAreServiceEvent, IRCConnectionService> {
    private static final Logger LOGGER = LoggerFactory.getLogger(YouAreServiceEventListener.class);

    public YouAreServiceEventListener(IRCConnectionService ircConnectionService) {
        super(ircConnectionService);
    }

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
