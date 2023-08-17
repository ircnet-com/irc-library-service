package com.ircnet.library.service.event;

import com.ircnet.library.service.connection.IRCServiceConnection;
import lombok.Getter;

@Getter
public class TopicEvent extends AbstractServiceEvent {
    private String channelName;
    private String topic;
    private String from;

    public TopicEvent(IRCServiceConnection ircConnection, String channelName, String topic) {
        this.ircConnection = ircConnection;
        this.channelName = channelName;
        this.topic = topic;
    }

    public TopicEvent(IRCServiceConnection ircConnection, String channelName, String topic, String from) {
        this(ircConnection, channelName, topic);
        this.from = from;
    }
}
