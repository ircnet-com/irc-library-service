package com.ircnet.library.service.squery;

import com.ircnet.library.common.User;
import com.ircnet.library.common.connection.IRCConnectionService;
import com.ircnet.library.service.connection.IRCServiceConnection;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import java.util.Map;

/**
 * Handler for:
 *  - /SQUERY Service COMMAND
 *  - /SQUERY Service HELP COMMAND
 */
public abstract class SQueryCommand<T extends IRCConnectionService> {
    protected final T ircConnectionService;

    public abstract String getName();

    public SQueryCommand(T ircConnectionService) {
        this.ircConnectionService = ircConnectionService;
    }

    /**
     * Handler for: /SQUERY Service COMMAND
     *
     * @param ircServiceConnection The IRC connection that received the command
     * @param from User who sent the SQUERY
     * @param message A message starting with "COMMAND"
     * @param tags IRCv3 message tags
     */
    abstract public void processCommand(IRCServiceConnection ircServiceConnection, User from,
                                        String message, Map<String, String> tags);

    /**
     * Handler for: /SQUERY Service HELP COMMAND
     *
     * @param ircServiceConnection The IRC connection that received the command
     * @param from User who sent the SQUERY
     * @param message "HELP COMMAND"
     */
    abstract public void processHelp(IRCServiceConnection ircServiceConnection, User from, String message);

    protected void sendOptionSyntax(IRCServiceConnection ircConnection, String nick, Options options) {
        for(Option option : options.getOptions()) {
            StringBuilder stringBuilder = new StringBuilder();

            if (option.getOpt() == null) {
                stringBuilder.append("   ").append("--").append(option.getLongOpt());
            } else {
                stringBuilder.append("-").append(option.getOpt());
                if (option.hasLongOpt()) {
                    stringBuilder.append(',').append("--").append(option.getLongOpt());
                }
            }

            if (option.hasArg()) {
                String argName = option.getArgName();
                if (argName != null && argName.isEmpty()) {
                    stringBuilder.append(' ');
                } else {
                    stringBuilder.append(" ");
                    stringBuilder.append("<").append(argName != null ? option.getArgName() : "arg").append(">");
                }
            }

            ircConnectionService.notice(ircConnection, nick, " %-20s %s",
                    stringBuilder.toString(), option.getDescription() != null ? option.getDescription() : "");
        }
    }
}
