package com.ircnet.service.library.events;

import com.ircnet.service.library.IRCService;
import com.ircnet.service.library.connection.IRCServiceConnection;

public class ChannelEvent extends AbstractServiceEvent {
    private String channelName;
    private int userCount;

    public ChannelEvent(IRCService ircService, IRCServiceConnection ircConnection, String channelName, int userCount) {
        this.ircService = ircService;
        this.ircConnection = ircConnection;
        this.channelName = channelName;
        this.userCount = userCount;
    }

    public String getChannelName() {
        return channelName;
    }

    public int getUserCount() {
        return userCount;
    }
}
