package com.ircnet.library.service.parser;


import com.ircnet.library.common.User;
import com.ircnet.library.common.Util;
import com.ircnet.library.common.connection.ConnectionStatus;
import com.ircnet.library.common.connection.IRCConnectionService;
import com.ircnet.library.common.event.ConnectionStatusChangedEvent;
import com.ircnet.library.common.event.EventBus;
import com.ircnet.library.common.event.EventContext;
import com.ircnet.library.common.parser.ParserMapping;
import com.ircnet.library.service.ServiceConfigurationModel;
import com.ircnet.library.service.connection.IRCServiceConnection;
import com.ircnet.library.service.event.*;
import com.ircnet.library.service.event.service.ConnectionStatusChangedHandlerImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Map;

public class ParserImpl extends com.ircnet.library.common.parser.ParserImpl<IRCServiceConnection> {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LoggerFactory.getLogger(ParserImpl.class);

    private final EventBus eventBus;
    private final ConnectionStatusChangedHandlerImpl connectionStatusChangedHandler;

    public ParserImpl(IRCConnectionService ircConnectionService,
                      EventBus eventBus,
                      ConnectionStatusChangedHandlerImpl connectionStatusChangedHandler) {
        this.ircConnectionService = ircConnectionService;
        this.eventBus = eventBus;
        this.connectionStatusChangedHandler = connectionStatusChangedHandler;
        parserMappingList.add(new ParserMapping<>("UNICK", 1, (arg1, arg2, arg3, arg4, arg5) -> parseUNick(arg1, arg2, arg4, arg5)));
        parserMappingList.add(new ParserMapping<>("NICK", 1, (arg1, arg2, arg3, arg4, arg5) -> parseNickChange(arg1, arg2, arg4, arg5)));
        parserMappingList.add(new ParserMapping<>("NICK", 0, (arg1, arg2, arg3, arg4, arg5) -> parseNick(arg1, arg2, arg4, arg5)));
        parserMappingList.add(new ParserMapping<>("CHANNEL", 0, (arg1, arg2, arg3, arg4, arg5) -> parseChannel(arg1, arg2, arg4, arg5)));
        parserMappingList.add(new ParserMapping<>("MODE", 0, (arg1, arg2, arg3, arg4, arg5) -> parseChannelMode(arg1, arg2, arg4, arg5)));
        parserMappingList.add(new ParserMapping<>("QUIT", 1, (arg1, arg2, arg3, arg4, arg5) -> parseQuit(arg1, arg2, arg4, arg5)));
        parserMappingList.add(new ParserMapping<>("TOPIC", 0, (arg1, arg2, arg3, arg4, arg5) -> parseTopic(arg1, arg2, arg4, arg5)));
        parserMappingList.add(new ParserMapping<>("TOPIC", 1, (arg1, arg2, arg3, arg4, arg5) -> parseTopicChange(arg1, arg2, arg4, arg5)));
        parserMappingList.add(new ParserMapping<>("SERVER", 1, (arg1, arg2, arg3, arg4, arg5) -> parseServer(arg1, arg2, arg4, arg5)));
        parserMappingList.add(new ParserMapping<>("SQUIT", 1, (arg1, arg2, arg3, arg4, arg5) -> parseSQuit(arg1, arg2, arg4, arg5)));
        parserMappingList.add(new ParserMapping<>("EOB", 0, (arg1, arg2, arg3, arg4, arg5) -> parseEndOfBurst(arg1, arg4, arg5)));
        parserMappingList.add(new ParserMapping<>("383", 1, (arg1, arg2, arg3, arg4, arg5) -> parseYouAreService(arg1, arg2, arg4, arg5)));
        parserMappingList.add(new ParserMapping<>("MODE", 1, (arg1, arg2, arg3, arg4, arg5) -> parseUserMode(arg1, arg2, arg4, arg5)));
        parserMappingList.add(new ParserMapping<>("SQUERY", 1, (arg1, arg2, arg3, arg4, arg5) -> parseSQuery(arg1, arg2, arg3, arg4, arg5)));
        parserMappingList.add(new ParserMapping<>("SERVSET", 1, (arg1, arg2, arg3, arg4, arg5) -> parseServSet(arg1, arg2, arg4, arg5)));
        parserMappingList.add(new ParserMapping<>("SASL", 1, (arg1, arg2, arg3, arg4, arg5) -> parseSASL(arg1, arg2, arg4, arg5)));
        parserMappingList.add(new ParserMapping<>("481", 1, (arg1, arg2, arg3, arg4, arg5) -> parsePermissionDenied(arg1, arg2, arg4, arg5)));
    }

    @Override
    public boolean parse(IRCServiceConnection ircConnection, String input, EventContext<IRCServiceConnection> eventContext) {
        LOGGER.debug("{}", input);
        return super.parse(ircConnection, input, eventContext);
    }

    private void parseYouAreService(IRCServiceConnection ircConnection, String[] parts,
                                    EventContext<IRCServiceConnection> eventContext,
                                    String line) {
        /*
            parts[0] = server (starting with ':')
            parts[1] = 383
            parts[2] = my service name
            parts[3] = :You are service <service-name>
        */
        String serviceName = parts[3].substring(parts[3].lastIndexOf(" ") + 1);

        ircConnection.setConnectionStatus(ConnectionStatus.REGISTERED);
        LOGGER.trace("Service connected as {}", serviceName);
        ircConnection.setServerName(StringUtils.substringAfter(serviceName, "@"));

        ServiceConfigurationModel config = ircConnection.getServiceConfiguration();

        StringBuilder servSetCommand = new StringBuilder("SERVSET ");
        servSetCommand.append("0x");
        servSetCommand.append(Integer.toHexString(config.getDataFlags()));

        if(config.getBurstFlags() != 0) {
            // Add flags for requested burst
            servSetCommand.append(" 0x");
            servSetCommand.append(Integer.toHexString(config.getBurstFlags()));
        }

        ircConnectionService.send(ircConnection, servSetCommand.toString());

        connectionStatusChangedHandler.onRegistered(ircConnection);

        eventBus.publishEvent(YouAreServiceEvent.builder()
                        .context(eventContext)
                        .raw(line)
                        .serviceName(serviceName)
                .build());
        eventBus.publishEvent(ConnectionStatusChangedEvent.<IRCServiceConnection>builder()
                .context(eventContext)
                .oldStatus(ircConnection.getConnectionStatus())
                .newStatus(ConnectionStatus.REGISTERED)
                .raw(line)
                .build());
    }


    private void parseServer(IRCServiceConnection ircConnection, String[] parts,
                             EventContext<IRCServiceConnection> eventContext,
                             String line) {
        /*
            parts[0] = server (starting with ':')
            parts[1] = "SERVER"
            parts[2] = name of the new server
            parts[3] = hop count
            parts[4] = SID of the new server
            parts[5] = service info (starting with ':')
        */
        eventBus.publishEvent(ServerEvent.builder()
                .context(eventContext)
                .serverName(parts[2])
                .hopCount(Integer.parseInt(parts[3]))
                .sid(parts[4])
                .info(Util.removeLeadingColon(parts[5]))
                .raw(line)
                .build());
    }

    private void parseSQuit(IRCServiceConnection ircConnection, String[] parts,
                            EventContext<IRCServiceConnection> eventContext,
                            String line) {
        /*
            parts[0] = sender (starting with ':')
            parts[1] = "SQUIT"
            parts[2] = name of the quitting server
            parts[3] = reason
        */
        eventBus.publishEvent(SQuitEvent.builder()
                .context(eventContext)
                .sender(Util.removeLeadingColon(parts[0]))
                .serverName(parts[2])
                .reason(Util.removeLeadingColon(parts[3]))
                .raw(line)
                .build());
    }

    private void parseUNick(IRCServiceConnection ircConnection, String[] parts,
                            EventContext<IRCServiceConnection> eventContext,
                            String line) {
        /*
            parts[0] = server (starting with ':')
            parts[1] = UNICK
            parts[2] = nick
            parts[3] = UID
            parts[4] = username / ident
            parts[5] = hostname
            parts[6] = IP address
            parts[7] = user modes (starting with '+')
            parts[8] = account or "*" if not authenticated (since contempt-1.0.3)
            parts[9] = real name (starting with ':')
        */
        String accountName;
        String realName;

        if (parts.length > 9 && parts[9].charAt(0) == ':') {
            accountName = parts[8];
            realName = Util.removeLeadingColon(parts[9]);
        }
        else {
            accountName = "*";
            realName = Util.removeLeadingColon(parts[8]);
        }

        eventBus.publishEvent(UNickEvent.builder()
                .context(eventContext)
                .sid(Util.removeLeadingColon(parts[0]))
                .uid(parts[3])
                .nick(parts[2])
                .user(parts[4])
                .host(parts[5])
                .ipAddress(parts[6])
                .userModes(parts[7])
                .account(accountName)
                .realName(realName)
                .raw(line)
                .build());
    }

    private void parseNickChange(IRCServiceConnection ircConnection, String[] parts,
                                 EventContext<IRCServiceConnection> eventContext,
                                 String line) {
        /*
         *  parts[0] = UID (starting with ':')
         *  parts[1] = NICK
         *  parts[2] = the new nick (starting with ':')
         */
        eventBus.publishEvent(NickChangeEvent.builder()
                .context(eventContext)
                .uid(Util.removeLeadingColon(parts[0]))
                .newNick(Util.removeLeadingColon(parts[2]))
                .raw(line)
                .build());
    }

    private void parseNick(IRCServiceConnection ircConnection, String[] parts,
                           EventContext<IRCServiceConnection> eventContext,
                           String line) {
        /*
            parts[0] = "NICK"
            parts[1] = nick
            parts[2] = hop count
            parts[3] = username / ident
            parts[4] = hostname
            parts[5] = irc server name
            parts[6] = user modes (starting with '+')
            parts[7] = real name (starting with ':')
        */
        if(parts.length == 3) {
            eventBus.publishEvent(NickEvent.builder()
                    .context(eventContext)
                    .nick(parts[1])
                    .hopCount(Integer.parseInt(Util.removeLeadingColon(parts[2])))
                    .raw(line)
                    .build());
        }
        else if(parts.length == 8) {
            eventBus.publishEvent(NickEvent.builder()
                    .context(eventContext)
                    .nick(parts[1])
                    .user(parts[3])
                    .host(parts[4])
                    .realName(Util.removeLeadingColon(parts[7]))
                    .serverName(parts[5])
                    .hopCount(Integer.parseInt(parts[2]))
                    .userModes(parts[6])
                    .raw(line)
                    .build());
        }
    }

    private void parseChannel(IRCServiceConnection ircConnection, String[] parts,
                              EventContext<IRCServiceConnection> eventContext,
                              String line) {
        /*
            parts[0] = "CHANNEL"
            parts[1] = channel name
            parts[2] = user count
        */
        eventBus.publishEvent(ChannelEvent.builder()
                .context(eventContext)
                .channelName(parts[1])
                .userCount(Integer.parseInt(parts[2]))
                .raw(line)
                .build());
    }

    private void parseQuit(IRCServiceConnection ircConnection, String[] parts,
                           EventContext<IRCServiceConnection> eventContext,
                           String line) {
        /*
         *  parts[0] = UID (starting with ':')
         *  parts[1] = QUIT
         *  parts[2] = message (starting with ':')
         */
        eventBus.publishEvent(QuitEvent.builder()
                .context(eventContext)
                .uid(Util.removeLeadingColon(parts[0]))
                .message(Util.removeLeadingColon(parts[2]))
                .raw(line)
                .build());
    }

    private void parseTopic(IRCServiceConnection ircConnection, String[] parts,
                            EventContext<IRCServiceConnection> eventContext,
                            String line) {
        /*
            parts[0] = "TOPIC"
            parts[1] = channel name
            parts[2] = topic (starting with ':')
        */
        eventBus.publishEvent(TopicEvent.builder()
                .context(eventContext)
                .channelName(parts[1])
                .topic(Util.removeLeadingColon(parts[2]))
                .raw(line)
                .build());
    }

    private void parseTopicChange(IRCServiceConnection ircConnection, String[] parts,
                                  EventContext<IRCServiceConnection> eventContext,
                                  String line) {
        /*
            parts[0] = nick (starting with ':')
            parts[1] = "TOPIC"
            parts[2] = channel name
            parts[3] = topic (starting with ':')
        */
        eventBus.publishEvent(TopicEvent.builder()
                .context(eventContext)
                .channelName(parts[2])
                .topic(Util.removeLeadingColon(parts[3]))
                .from(Util.removeLeadingColon(parts[0]))
                .raw(line)
                .build());
    }

    private void parseChannelMode(IRCServiceConnection ircConnection, String[] parts,
                                  EventContext<IRCServiceConnection> eventContext,
                                  String line) {
        /*
            parts[0] = "CHANNEL"
            parts[1] = channel name
            parts[2] = modes
        */
        eventBus.publishEvent(ChannelModeEvent.builder()
                .context(eventContext)
                .channelName(parts[1])
                .modes(parts[2])
                .raw(line)
                .build());
    }

    private void parseUserMode(IRCServiceConnection ircConnection, String[] parts,
                               EventContext<IRCServiceConnection> eventContext,
                               String line) {
        /*
            parts[0] = nick (starting with ':')
            parts[1] = "MODE"
            parts[2] = my service name
            parts[3] = modes
       */
        eventBus.publishEvent(UserModeEvent.builder()
                .context(eventContext)
                .nick(parts[2])
                .modes(parts[3])
                .raw(line)
                .build());
    }

    private void parseSQuery(IRCServiceConnection ircConnection, String[] parts, Map<String, String> messageTags,
                             EventContext<IRCServiceConnection> eventContext,
                             String line) {
        /*
            parts[0] = hostmask of the sender
            parts[1] = "SQUERY"
            parts[2] = my service name
            parts[3] = message
        */
        eventBus.publishEvent(SQueryEvent.builder()
                .context(eventContext)
                .messageTags(messageTags)
                .from(new User(parts[0]))
                .message(Util.removeLeadingColon(parts[3]))
                .raw(line)
                .build());
    }

    private void parseEndOfBurst(IRCServiceConnection ircConnection,
                                 EventContext<IRCServiceConnection> eventContext,
                                 String line) {
        // parts[0] = "EOB"
        LOGGER.trace("End of burst");
        ircConnection.setBurst(false);

        eventBus.publishEvent(EndOfBurstEvent.builder()
                .context(eventContext)
                .raw(line)
                .build());
    }

    private void parseServSet(IRCServiceConnection ircConnection, String[] parts,
                              EventContext<IRCServiceConnection> eventContext,
                              String line) {
        /*
            parts[0] = hostmask of the sender
            parts[1] = "SERVSET"
            parts[2] = my service name
            parts[3] = The accepted SERVSET as integer (starting with ':')
        */
        int acceptedSettings = Integer.parseInt(Util.removeLeadingColon(parts[3]));
        LOGGER.trace("Accepted SERVSET: {}", "0x" + Integer.toHexString(acceptedSettings));

        if(ircConnection.getServiceConfiguration().getBurstFlags() != 0) {
            LOGGER.trace("Expecting burst now");
            ircConnection.setBurst(true);
            ircConnection.setBurstStart(new Date());
        }

        eventBus.publishEvent(ServSetEvent.builder()
                .context(eventContext)
                .from(new User(parts[0]))
                .acceptedSettings(acceptedSettings)
                .raw(line)
                .build());
    }

    private void parseSASL(IRCServiceConnection ircConnection, String[] parts,
                           EventContext<IRCServiceConnection> eventContext,
                           String line) {
        /*
            parts[0] = server (starting with ':')
            parts[1] = "SASL"
            parts[2] = UID
            parts[3] = my service Name
            parts[4] = type ('H', 'S', 'C')
            parts[5] = data
        */
        eventBus.publishEvent(SASLEvent.builder()
                .context(eventContext)
                .serverName(Util.removeLeadingColon(parts[0]))
                .uidNick(parts[2])
                .type(parts[4])
                .data(parts[5])
                .raw(line)
                .build());
    }

    private void parsePermissionDenied(IRCServiceConnection ircConnection, String[] parts,
                                       EventContext<IRCServiceConnection> eventContext,
                                       String line) {
        /*
            parts[0] = server (starting with ':')
            parts[1] = "481"
            parts[2] = my service name
            parts[3] = ":Permission Denied"
        */
        eventBus.publishEvent(PermissionDeniedEvent.builder()
                .context(eventContext)
                .serverName(Util.removeLeadingColon(parts[0]))
                .message(Util.removeLeadingColon(parts[3]))
                .raw(line)
                .build());
    }
}
