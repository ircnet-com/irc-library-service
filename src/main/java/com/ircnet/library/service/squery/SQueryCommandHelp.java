package com.ircnet.library.service.squery;

import com.ircnet.library.common.User;
import com.ircnet.library.common.connection.IRCConnectionService;
import com.ircnet.library.service.ServiceConfigurationModel;
import com.ircnet.library.service.connection.IRCServiceConnection;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * Handler for: <code>/SQUERY &lt;Service&gt; HELP</code>
 */
public class SQueryCommandHelp extends SQueryCommand<IRCConnectionService> {
    private final Map<String, SQueryCommand> squeryCommandMap;
    private final ServiceConfigurationModel properties;

    public SQueryCommandHelp(IRCConnectionService ircConnectionService,
                             Map<String, SQueryCommand> squeryCommandMap,
                             ServiceConfigurationModel properties) {
        super(ircConnectionService);
        this.squeryCommandMap = squeryCommandMap;
        this.properties = properties;
    }

    @Override
    public String getName() {
        return "HELP";
    }

    /**
     * Handler for: <code>/SQUERY &lt;Service&gt; HELP</code>
     *
     * @param ircServiceConnection The IRC connection that received the command
     * @param from User who sent the SQUERY
     * @param message "HELP" or "HELP &lt;command&gt;"
     * @param tags IRCv3 message tags
     */
    @Override
    public void processCommand(IRCServiceConnection ircServiceConnection, User from,
                               String message, Map<String, String> tags) {
        String[] parts = message.split(" ");

        if(parts.length == 1) {
            // HELP
            ircConnectionService.notice(ircServiceConnection, from.getNick(), "%s help index", properties.getName());
            ircConnectionService.notice(ircServiceConnection, from.getNick(), "Use /SQUERY %s HELP <topic>", properties.getName());
            ircConnectionService.notice(ircServiceConnection, from.getNick(), "Available topics: %s", StringUtils.join((squeryCommandMap).keySet(), ", "));
        }
        else {
            // HELP <command>
            SQueryCommand squeryCommand = squeryCommandMap.get(parts[1]);

            if(squeryCommand != null) {
                squeryCommand.processHelp(ircServiceConnection, from, message);
            }
            else {
                ircConnectionService.notice(ircServiceConnection, from.getNick(), "No such help topic: \"%s\". Use /SQUERY %s HELP", parts[1], properties.getName());
            }
        }
    }

    /**
     * Handler for: <code>/SQUERY &lt;Service&gt; HELP HELP</code>
     *
     * @param ircServiceConnection The IRC connection that received the command
     * @param from User who sent the SQUERY
     * @param message "HELP HELP"
     */
    @Override
    public void processHelp(IRCServiceConnection ircServiceConnection, User from, String message) {
        String[] parts = message.split(" ");
        ircConnectionService.notice(ircServiceConnection, from.getNick(), "No such help topic: \"%s\". Use /SQUERY %s HELP", parts[1], properties.getName());
    }
}

