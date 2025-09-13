package com.ircnet.library.service.squery;

import com.ircnet.library.common.User;
import com.ircnet.library.common.connection.IRCConnectionService;
import com.ircnet.library.service.ServiceConfigurationModel;
import com.ircnet.library.service.connection.IRCServiceConnection;

import java.util.Map;

/**
 * Handler for:
 *  - /SQUERY DNSBLService ADMIN
 *  - /SQUERY DNSBLService HELP ADMIN
 */
public class SQueryCommandAdmin extends SQueryCommand<IRCConnectionService> {
    private ServiceConfigurationModel properties;

    public SQueryCommandAdmin(IRCConnectionService ircConnectionService, ServiceConfigurationModel properties) {
        super(ircConnectionService);
        this.properties = properties;
    }

    @Override
    public String getName() {
        return "ADMIN";
    }

    /**
     * Handler for: /SQUERY      * Handler for: /SQUERY DNSBLService HELP ADMIN ADMIN
     *
     * @param ircServiceConnection The IRC connection that received the command
     * @param from User who sent the SQUERY
     * @param tags IRCv3 message tags
     * @param message "ADMIN"
     */
    @Override
    public void processCommand(IRCServiceConnection ircServiceConnection, User from,
                               String message, Map<String, String> tags) {
        ircConnectionService.notice(ircServiceConnection, from.getNick(), "Administrative info about %s:", properties.getName());
        ircConnectionService.notice(ircServiceConnection, from.getNick(), properties.getSquery().getAdmin());
    }

    /**
     * Handler for: /SQUERY DNSBLService HELP ADMIN
     *
     * @param ircServiceConnection The IRC connection that received the command
     * @param from User who sent the SQUERY
     * @param message "HELP ADMIN"
     */
    @Override
    public void processHelp(IRCServiceConnection ircServiceConnection, User from, String message) {
        ircConnectionService.notice(ircServiceConnection, from.getNick(), "Shows administrative information");
    }
}
