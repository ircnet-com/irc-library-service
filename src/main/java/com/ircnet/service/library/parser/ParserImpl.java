package com.ircnet.service.library.parser;


import com.ircnet.common.library.Parser;
import com.ircnet.common.library.User;
import com.ircnet.common.library.Util;
import com.ircnet.common.library.connection.ConnectionStatus;
import com.ircnet.common.library.event.ConnectionStatusChangedEvent;
import com.ircnet.common.library.event.EventBus;
import com.ircnet.service.library.IRCService;
import com.ircnet.service.library.connection.IRCServiceConnection;
import com.ircnet.service.library.events.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ParserImpl implements Parser<IRCServiceConnection> {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LoggerFactory.getLogger(ParserImpl.class);

    private IRCService ircService;
    private List<ParserMapping> parserMappingList;

    public ParserImpl(IRCService ircService) {
        this.ircService = ircService;

        parserMappingList = new ArrayList<>();
        parserMappingList.add(new ParserMapping("PING", 0, 2, (arg1, arg2) -> parsePing(arg1, arg2)));
        parserMappingList.add(new ParserMapping("NICK", 0, 8, (arg1, arg2) -> parseNick(arg1, arg2)));
        parserMappingList.add(new ParserMapping("CHANNEL", 0, 0, (arg1, arg2) -> parseChannel(arg1, arg2)));
        parserMappingList.add(new ParserMapping("MODE", 0, 0, (arg1, arg2) -> parseChannelMode(arg1, arg2)));
        parserMappingList.add(new ParserMapping("TOPIC", 0, 3, (arg1, arg2) -> parseTopic(arg1, arg2)));
        parserMappingList.add(new ParserMapping("TOPIC", 1, 4, (arg1, arg2) -> parseTopicChange(arg1, arg2)));
        parserMappingList.add(new ParserMapping("SERVER", 1, 6, (arg1, arg2) -> parseServer(arg1, arg2)));
        parserMappingList.add(new ParserMapping("EOB", 0, 0, (arg1, arg2) -> parseEndOfBurst(arg1)));
        parserMappingList.add(new ParserMapping("383", 1, 4, (arg1, arg2) -> parseYouAreService(arg1, arg2)));
        parserMappingList.add(new ParserMapping("MODE", 1, 0, (arg1, arg2) -> parseUserMode(arg1, arg2)));
        parserMappingList.add(new ParserMapping("SQUERY", 1, 4, (arg1, arg2) -> parseSQuery(arg1, arg2)));
        parserMappingList.add(new ParserMapping("SERVSET", 1, 0, (arg1, arg2) -> parseServSet(arg1, arg2)));
    }

    @Override
    public void parse(IRCServiceConnection ircConnection, String line) {
        String[] parts = line.split(" ");

        for(ParserMapping parserMapping : parserMappingList) {
            if(parts.length > parserMapping.getIndex() && parserMapping.getKey().equals(parts[parserMapping.getIndex()])) {
                parts = line.split(" ", parserMapping.getArgumentCount());
                parserMapping.getParserMethod().parse(ircConnection, parts);
                return;
            }
        }
    }

    private void parsePing(IRCServiceConnection ircConnection, String[] parts) {
        ircConnection.send("PONG %s", parts[1]);
    }

    private void parseYouAreService(IRCServiceConnection ircConnection, String[] parts) {
        /*
            parts[0] = server (starting with ':')
            parts[1] = 383
            parts[2] = my service name
            parts[3] = :You are service <service-name>
        */
        String lastWord = parts[3].substring(parts[3].lastIndexOf(" ") + 1);

        EventBus.publishEvent(new YouAreServiceEvent(ircService, ircConnection, lastWord));
        EventBus.publishEvent(new ConnectionStatusChangedEvent(ircConnection, ircConnection.getConnectionStatus(), ConnectionStatus.REGISTERED));
    }

    private void parseServer(IRCServiceConnection ircConnection, String[] parts) {
        /*
            parts[0] = server (starting with ':')
            parts[1] = "SERVER"
            parts[2] = name of the new server
            parts[3] = hop count
            parts[4] = SID of the new server
            parts[5] = service info (starting with ':')
        */
        EventBus.publishEvent(new ServerEvent(ircService, ircConnection, parts[2], Integer.parseInt(parts[3]), parts[4], Util.removeLeadingColon(parts[5])));
    }

    private void parseNick(IRCServiceConnection ircConnection, String[] parts) {
        /*
            parts[0] = "NICK"
            parts[1] = nick
            parts[2] = 1
            parts[3] = user name / ident
            parts[4] = hostname
            parts[5] = irc server name
            parts[6] = user modes (starting with '+')
            parts[7] = real name (starting with ':')
        */
        EventBus.publishEvent(new NickEvent(ircService, ircConnection, parts[1], parts[3], parts[4], Util.removeLeadingColon(parts[7]), parts[5], Integer.parseInt(parts[2]), parts[6]));
    }

    private void parseChannel(IRCServiceConnection ircConnection, String[] parts) {
        /*
            parts[0] = "CHANNEL"
            parts[1] = channel name
            parts[2] = user count
        */
        EventBus.publishEvent(new ChannelEvent(ircService, ircConnection, parts[1], Integer.parseInt(parts[2])));
    }

    private void parseTopic(IRCServiceConnection ircConnection, String[] parts) {
        /*
            parts[0] = "TOPIC"
            parts[1] = channel name
            parts[2] = topic (starting with ':')
        */
        EventBus.publishEvent(new TopicEvent(ircService, ircConnection, parts[1], Util.removeLeadingColon(parts[2])));
    }

    private void parseTopicChange(IRCServiceConnection ircConnection, String[] parts) {
        /*
            parts[0] = nick (starting with ':')
            parts[1] = "TOPIC"
            parts[2] = channel name
            parts[3] = topic (starting with ':')
        */
        EventBus.publishEvent(new TopicEvent(ircService, ircConnection, parts[2], Util.removeLeadingColon(parts[3]), Util.removeLeadingColon(parts[0])));
    }

    private void parseChannelMode(IRCServiceConnection ircConnection, String[] parts) {
        /*
            parts[0] = "CHANNEL"
            parts[1] = channel name
            parts[2] = modes
        */
        EventBus.publishEvent(new ChannelModeEvent(ircService, ircConnection, parts[1], parts[2]));
    }

    private void parseUserMode(IRCServiceConnection ircConnection, String[] parts) {
        /*
            parts[0] = nick (starting with ':')
            parts[1] = "MODE"
            parts[2] = my service name
            parts[3] = modes
       */
        EventBus.publishEvent(new UserModeEvent(ircService, ircConnection, parts[2], parts[3]));
    }

    private void parseSQuery(IRCServiceConnection ircConnection, String[] parts) {
        /*
            parts[0] = hostmask of the sender
            parts[1] = "SQUERY"
            parts[2] = my service name
            parts[3] = message
        */
        EventBus.publishEvent(new SQueryEvent(ircService, ircConnection, new User(parts[0]), Util.removeLeadingColon(parts[3])));
    }

    private void parseEndOfBurst(IRCServiceConnection ircConnection) {
        // parts[0] = "EOB"
        EventBus.publishEvent(new EndOfBurstEvent(ircService, ircConnection));
    }

    private void parseServSet(IRCServiceConnection ircConnection, String[] parts) {
        /*
            parts[0] = hostmask of the sender
            parts[1] = "SERVSET"
            parts[2] = my service name
            parts[3] = The accepted SERVSET as integer
        */
        EventBus.publishEvent(new ServSetEvent(ircService, ircConnection, new User(parts[0]), Integer.parseInt(Util.removeLeadingColon(parts[3]))));
    }
}
