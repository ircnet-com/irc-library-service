package com.ircnet.library.service.event;

import com.ircnet.library.common.IRCTask;
import com.ircnet.library.common.SettingConstants;
import com.ircnet.library.common.connection.ConnectionStatus;
import com.ircnet.library.common.connection.IRCConnection;
import com.ircnet.library.common.event.AbstractEventListener;
import com.ircnet.library.common.event.ConnectionStatusChangedEvent;
import com.ircnet.library.service.IRCService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class ConnectionStatusChangedEventListener extends AbstractEventListener<ConnectionStatusChangedEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionStatusChangedEventListener.class);
    private IRCService ircService;

    public ConnectionStatusChangedEventListener(IRCService ircService) {
        this.ircService = ircService;
    }

    public void onEvent(ConnectionStatusChangedEvent event) {
        IRCConnection ircConnection = event.getIRCConnection();

        if (event.getNewStatus() == ConnectionStatus.CONNECTION_ESTABLISHED) {
            LOGGER.info("Connection established");
            this.onConnectionEstablished(ircConnection);
        } else if (event.getNewStatus() == ConnectionStatus.DISCONNECTED) {
            if (event.getOldStatus() == ConnectionStatus.CONNECTING) {
                LOGGER.info("Connect failed");
                ircConnection.reset();
                this.prepareDelayedReconnect(ircConnection);
            } else if (event.getOldStatus() == ConnectionStatus.CONNECTION_ESTABLISHED) {
                LOGGER.info("Disconnected");
                ircConnection.reset();
                this.prepareDelayedReconnect(ircConnection);
            } else if (event.getOldStatus() == ConnectionStatus.REGISTERED) {
                LOGGER.info("Disconnected");
                ircConnection.reset();
                ircConnection.setNexConnectAttempt(new Date());
            } else {
                ircConnection.reset();
                this.prepareDelayedReconnect(ircConnection);
            }
        }
    }

    protected void onConnectionEstablished(IRCConnection ircConnection) {
        ircConnection.send("PASS %s", ircService.getServiceConfigurationModel().getPassword());
        ircConnection.send("SERVICE %s %s %s :%s" , ircService.getServiceConfigurationModel().getServiceName(), ircService.getServiceConfigurationModel().getDistributionMask(), "0x" + Integer.toHexString(ircService.getServiceConfigurationModel().getServiceType()), ircService.getServiceConfigurationModel().getInfo());
    }

    private void prepareDelayedReconnect(IRCConnection ircConnection) {
        Date now = new Date();
        int reconnectDelay = IRCTask.getSettingService().findInteger(SettingConstants.RECONNECT_DELAY, SettingConstants.RECONNECT_DELAY_DEFAULT);
        ircConnection.setNexConnectAttempt(new Date(now.getTime() + (long)(reconnectDelay * 1000)));
        LOGGER.debug("Reconnecting in {} seconds", reconnectDelay);
    }
}