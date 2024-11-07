package com.ircnet.library.service.event.eventlistener;

import com.ircnet.library.common.SettingConstants;
import com.ircnet.library.common.SettingService;
import com.ircnet.library.common.connection.ConnectionStatus;
import com.ircnet.library.common.connection.IRCConnection;
import com.ircnet.library.common.connection.IRCConnectionService;
import com.ircnet.library.common.event.AbstractEventListener;
import com.ircnet.library.common.event.ConnectionStatusChangedEvent;
import com.ircnet.library.service.ServiceConfigurationModel;
import com.ircnet.library.service.connection.IRCServiceConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class ConnectionStatusChangedEventListener extends AbstractEventListener<ConnectionStatusChangedEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionStatusChangedEventListener.class);

    private IRCConnectionService ircConnectionService;
    private SettingService settingService;

    public ConnectionStatusChangedEventListener(IRCConnectionService ircConnectionService,
                                                SettingService settingService) {
        this.ircConnectionService = ircConnectionService;
        this.settingService = settingService;
    }

    public void onEvent(ConnectionStatusChangedEvent event) {
        IRCServiceConnection ircConnection = (IRCServiceConnection) event.getIRCConnection();

        if(event.getNewStatus() == ConnectionStatus.CONNECTION_ESTABLISHED) {
            LOGGER.info("Connection established");
            this.onConnectionEstablished(ircConnection);
        }
        else if(event.getNewStatus() == ConnectionStatus.DISCONNECTED) {
            if(event.getOldStatus() == ConnectionStatus.CONNECTING) {
                LOGGER.info("Connect failed");
                ircConnectionService.reset(ircConnection);
                this.prepareDelayedReconnect(ircConnection);
            }
            else if(event.getOldStatus() == ConnectionStatus.CONNECTION_ESTABLISHED) {
                LOGGER.info("Disconnected");
                ircConnectionService.reset(ircConnection);
                this.prepareDelayedReconnect(ircConnection);
            }
            else if(event.getOldStatus() == ConnectionStatus.REGISTERED) {
                LOGGER.info("Disconnected");
                ircConnectionService.reset(ircConnection);
                ircConnection.setNexConnectAttempt(new Date());
            }
            else {
                ircConnectionService.reset(ircConnection);
                this.prepareDelayedReconnect(ircConnection);
            }
        }
    }

    protected void onConnectionEstablished(IRCServiceConnection ircConnection) {
        ServiceConfigurationModel config = ircConnection.getServiceConfiguration();
        ircConnectionService.send(ircConnection, "PASS %s", ircConnection.getCurrentServer().getPassword());
        ircConnectionService.send(ircConnection, "SERVICE %s %s %s :%s", config.getName(), config.getDistributionMask(), "0x" + Integer.toHexString(config.getType()), config.getInfo());
    }

    private void prepareDelayedReconnect(IRCConnection ircConnection) {
        Date now = new Date();
        int reconnectDelay = settingService.findInteger(SettingConstants.RECONNECT_DELAY, SettingConstants.RECONNECT_DELAY_DEFAULT);
        ircConnection.setNexConnectAttempt(new Date(now.getTime() + (long) (reconnectDelay * 1000)));
        LOGGER.debug("Reconnecting in {} seconds", reconnectDelay);
    }
}
