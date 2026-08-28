package com.ircnet.library.service.event.service;

import com.ircnet.library.common.SettingConstants;
import com.ircnet.library.common.SettingService;
import com.ircnet.library.common.connection.ConnectionStatus;
import com.ircnet.library.common.connection.IRCConnection;
import com.ircnet.library.common.connection.IRCConnectionService;
import com.ircnet.library.service.ServiceConfigurationModel;
import com.ircnet.library.service.connection.IRCServiceConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class ConnectionStatusChangedHandlerImpl extends com.ircnet.library.common.connection.ConnectionStatusChangedHandlerImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionStatusChangedHandlerImpl.class);

    private final IRCConnectionService ircConnectionService;
    private final SettingService settingService;

    public ConnectionStatusChangedHandlerImpl(IRCConnectionService ircConnectionService,
                                              SettingService settingService) {
        this.ircConnectionService = ircConnectionService;
        this.settingService = settingService;
    }

    @Override
    public void onConnectionEstablished(IRCConnection ircConnection) {
        LOGGER.info("Connection established");
        ServiceConfigurationModel config = ((IRCServiceConnection)ircConnection).getServiceConfiguration();
        ircConnectionService.send(ircConnection, "PASS %s", ircConnection.getCurrentServer().getPassword());
        ircConnectionService.send(ircConnection, "SERVICE %s %s %s :%s", config.getName(), config.getDistributionMask(), "0x" + Long.toHexString(config.getType()), config.getInfo());
    }

    @Override
    public void onConnectFailed(IRCConnection ircConnection) {
        super.onConnectFailed(ircConnection);
        LOGGER.info("Connect failed");
        ircConnectionService.reset(ircConnection);
        prepareDelayedReconnect(ircConnection);
    }

    @Override
    public void onDisconnect(IRCConnection ircConnection, ConnectionStatus oldStatus) {
        ircConnectionService.reset(ircConnection);

        switch (oldStatus) {
            case CONNECTING:
                LOGGER.info("Connect failed");
                this.prepareDelayedReconnect(ircConnection);
                break;
            case CONNECTION_ESTABLISHED:
                LOGGER.info("Disconnected");
                this.prepareDelayedReconnect(ircConnection);
                break;
            case REGISTERED:
                LOGGER.info("Disconnected");
                ircConnection.setNextConnectAttempt(new Date());
                break;
            default:
                this.prepareDelayedReconnect(ircConnection);
        }
    }

    private void prepareDelayedReconnect(IRCConnection ircConnection) {
        Date now = new Date();
        int reconnectDelay = settingService.findInteger(SettingConstants.RECONNECT_DELAY, SettingConstants.RECONNECT_DELAY_DEFAULT);
        ircConnection.setNextConnectAttempt(new Date(now.getTime() + (long) (reconnectDelay * 1000)));
        LOGGER.debug("Reconnecting in {} seconds", reconnectDelay);
    }
}
