package com.ircnet.library.service.parser;


import com.ircnet.library.common.User;
import com.ircnet.library.common.Util;
import com.ircnet.library.common.connection.ConnectionStatus;
import com.ircnet.library.common.event.ConnectionStatusChangedEvent;
import com.ircnet.library.common.event.EventBus;
import com.ircnet.library.parser.ParserMapping;
import com.ircnet.library.service.connection.IRCServiceConnection;
import com.ircnet.library.service.event.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParserImpl extends com.ircnet.library.parser.ParserImpl<IRCServiceConnection> {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LoggerFactory.getLogger(ParserImpl.class);

    @Autowired
    private EventBus eventBus;

    private List<ParserMapping<IRCServiceConnection>> parserMappingList;

    public ParserImpl() {
        parserMappingList = new ArrayList<>();
        parserMappingList.add(new ParserMapping<>("NICK", 0, 8, (arg1, arg2) -> parseNick(arg1, arg2)));
        parserMappingList.add(new ParserMapping<>("CHANNEL", 0, 0, (arg1, arg2) -> parseChannel(arg1, arg2)));
        parserMappingList.add(new ParserMapping<>("MODE", 0, 0, (arg1, arg2) -> parseChannelMode(arg1, arg2)));
        parserMappingList.add(new ParserMapping<>("TOPIC", 0, 3, (arg1, arg2) -> parseTopic(arg1, arg2)));
        parserMappingList.add(new ParserMapping<>("TOPIC", 1, 4, (arg1, arg2) -> parseTopicChange(arg1, arg2)));
        parserMappingList.add(new ParserMapping<>("SERVER", 1, 6, (arg1, arg2) -> parseServer(arg1, arg2)));
        parserMappingList.add(new ParserMapping<>("EOB", 0, 0, (arg1, arg2) -> parseEndOfBurst(arg1)));
        parserMappingList.add(new ParserMapping<>("383", 1, 4, (arg1, arg2) -> parseYouAreService(arg1, arg2)));
        parserMappingList.add(new ParserMapping<>("MODE", 1, 0, (arg1, arg2) -> parseUserMode(arg1, arg2)));
        parserMappingList.add(new ParserMapping<>("SQUERY", 1, 4, (arg1, arg2) -> parseSQuery(arg1, arg2)));
        parserMappingList.add(new ParserMapping<>("SERVSET", 1, 0, (arg1, arg2) -> parseServSet(arg1, arg2)));
    }

    @Override
    public void parse(IRCServiceConnection ircConnection, String line) {
        super.parse(ircConnection, line);

        String[] parts = line.split(" ");

        for(ParserMapping parserMapping : parserMappingList) {
            if(parts.length > parserMapping.getIndex() && parserMapping.getKey().equals(parts[parserMapping.getIndex()])) {
                parts = line.split(" ", parserMapping.getArgumentCount());
                parserMapping.getParserMethod().parse(ircConnection, parts);
                return;
            }
        }
    }

    private void parseYouAreService(IRCServiceConnection ircConnection, String[] parts) {
        /*
            parts[0] = server (starting with ':')
            parts[1] = 383
            parts[2] = my service name
            parts[3] = :You are service <service-name>
        */
        String lastWord = parts[3].substring(parts[3].lastIndexOf(" ") + 1);

        eventBus.publishEvent(new YouAreServiceEvent(ircConnection, lastWord));
        eventBus.publishEvent(new ConnectionStatusChangedEvent(ircConnection, ircConnection.getConnectionStatus(), ConnectionStatus.REGISTERED));
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
        eventBus.publishEvent(new ServerEvent(ircConnection, parts[2], Integer.parseInt(parts[3]), parts[4], Util.removeLeadingColon(parts[5])));
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
        eventBus.publishEvent(new NickEvent(ircConnection, parts[1], parts[3], parts[4], Util.removeLeadingColon(parts[7]), parts[5], Integer.parseInt(parts[2]), parts[6]));
    }

    private void parseChannel(IRCServiceConnection ircConnection, String[] parts) {
        /*
            parts[0] = "CHANNEL"
            parts[1] = channel name
            parts[2] = user count
        */
        eventBus.publishEvent(new ChannelEvent(ircConnection, parts[1], Integer.parseInt(parts[2])));
    }

    private void parseTopic(IRCServiceConnection ircConnection, String[] parts) {
        /*
            parts[0] = "TOPIC"
            parts[1] = channel name
            parts[2] = topic (starting with ':')
        */
        eventBus.publishEvent(new TopicEvent(ircConnection, parts[1], Util.removeLeadingColon(parts[2])));
    }

    private void parseTopicChange(IRCServiceConnection ircConnection, String[] parts) {
        /*
            parts[0] = nick (starting with ':')
            parts[1] = "TOPIC"
            parts[2] = channel name
            parts[3] = topic (starting with ':')
        */
        eventBus.publishEvent(new TopicEvent(ircConnection, parts[2], Util.removeLeadingColon(parts[3]), Util.removeLeadingColon(parts[0])));
    }

    private void parseChannelMode(IRCServiceConnection ircConnection, String[] parts) {
        /*
            parts[0] = "CHANNEL"
            parts[1] = channel name
            parts[2] = modes
        */
        eventBus.publishEvent(new ChannelModeEvent(ircConnection, parts[1], parts[2]));
    }

    private void parseUserMode(IRCServiceConnection ircConnection, String[] parts) {
        /*
            parts[0] = nick (starting with ':')
            parts[1] = "MODE"
            parts[2] = my service name
            parts[3] = modes
       */
        eventBus.publishEvent(new UserModeEvent(ircConnection, parts[2], parts[3]));
    }

    private void parseSQuery(IRCServiceConnection ircConnection, String[] parts) {
        /*
            parts[0] = hostmask of the sender
            parts[1] = "SQUERY"
            parts[2] = my service name
            parts[3] = message
        */
        eventBus.publishEvent(new SQueryEvent(ircConnection, new User(parts[0]), Util.removeLeadingColon(parts[3])));
    }

    private void parseEndOfBurst(IRCServiceConnection ircConnection) {
        // parts[0] = "EOB"
        eventBus.publishEvent(new EndOfBurstEvent(ircConnection));
    }

    private void parseServSet(IRCServiceConnection ircConnection, String[] parts) {
        /*
            parts[0] = hostmask of the sender
            parts[1] = "SERVSET"
            parts[2] = my service name
            parts[3] = The accepted SERVSET as integer
        */
        eventBus.publishEvent(new ServSetEvent(ircConnection, new User(parts[0]), Integer.parseInt(Util.removeLeadingColon(parts[3]))));
    }
}
