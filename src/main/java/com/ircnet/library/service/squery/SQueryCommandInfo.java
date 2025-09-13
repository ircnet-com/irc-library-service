package com.ircnet.library.service.squery;

import com.ircnet.library.common.User;
import com.ircnet.library.common.connection.IRCConnectionService;
import com.ircnet.library.service.ServiceConfigurationModel;
import com.ircnet.library.service.connection.IRCServiceConnection;

import java.util.Map;

/**
 * Handler for:
 *  - /SQUERY DNSBLService INFO
 *  - /SQUERY DNSBLService HELP INFO
 */
public class SQueryCommandInfo extends SQueryCommand<IRCConnectionService> {
    private ServiceConfigurationModel properties;

    public SQueryCommandInfo(IRCConnectionService ircConnectionService, ServiceConfigurationModel properties) {
        super(ircConnectionService);
        this.properties = properties;
    }

    @Override
    public String getName() {
        return "INFO";
    }

    /**
     * Handler for: /SQUERY DNSBLService INFO
     *
     * @param ircServiceConnection The IRC connection that received the command
     * @param from User who sent the SQUERY
     * @param message "INFO"
     * @param tags IRCv3 message tags
     */
    @Override
    public void processCommand(IRCServiceConnection ircServiceConnection, User from,
                               String message, Map<String, String> tags) {
        ircConnectionService.notice(ircServiceConnection, from.getNick(), properties.getSquery().getInfo());
    }

    /**
     * Handler for: /SQUERY DNSBLService HELP INFO
     *
     * @param from User who sent the SQUERY
     * @param message "HELP INFO"
     */
    @Override
    public void processHelp(IRCServiceConnection ircServiceConnection, User from, String message) {
        ircConnectionService.notice(ircServiceConnection, from.getNick(), "Shows information about this software");
    }
}
