package com.ircnet.library.service.event;

import com.ircnet.library.service.connection.IRCServiceConnection;
import lombok.Getter;

@Getter
public class ChannelEvent extends AbstractServiceEvent {
    private String channelName;
    private int userCount;

    public ChannelEvent(IRCServiceConnection ircConnection, String channelName, int userCount) {
        this.ircConnection = ircConnection;
        this.channelName = channelName;
        this.userCount = userCount;
    }
}
