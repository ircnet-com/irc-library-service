package com.ircnet.library.service.squery;

import com.ircnet.library.common.User;
import com.ircnet.library.common.connection.IRCConnectionService;
import com.ircnet.library.service.AppVersionProvider;
import com.ircnet.library.service.ServiceConfigurationModel;
import com.ircnet.library.service.connection.IRCServiceConnection;

import java.util.Map;

/**
 * Handler for:
 *  - /SQUERY DNSBLService VERSION
 *  - /SQUERY DNSBLService HELP VERSION
 */
public class SQueryCommandVersion extends SQueryCommand<IRCConnectionService> {
    private final ServiceConfigurationModel properties;
    private final AppVersionProvider appVersionProvider;

    public SQueryCommandVersion(IRCConnectionService ircConnectionService, ServiceConfigurationModel properties,
                                AppVersionProvider appVersionProvider) {
        super(ircConnectionService);
        this.properties = properties;
        this.appVersionProvider = appVersionProvider;
    }

    @Override
    public String getName() {
        return "VERSION";
    }

    /**
     * Handler for: /SQUERY DNSBLService VERSION
     *
     * @param ircServiceConnection The IRC connection that received the command
     * @param from User who sent the SQUERY
     * @param message "VERSION"
     * @param tags IRCv3 message tags
     */
    @Override
    public void processCommand(IRCServiceConnection ircServiceConnection, User from,
                               String message, Map<String, String> tags) {
        ircConnectionService.notice(ircServiceConnection, from.getNick(), "%s v%s",
                properties.getName(), appVersionProvider.get());
    }

    /**
     * Handler for: /SQUERY DNSBLService HELP VERSION
     *
     * @param ircServiceConnection The IRC connection that received the command
     * @param from User who sent the SQUERY
     * @param message "HELP VERSION"
     */
    @Override
    public void processHelp(IRCServiceConnection ircServiceConnection, User from, String message) {
        ircConnectionService.notice(ircServiceConnection, from.getNick(), "Shows the current version");
    }
}
