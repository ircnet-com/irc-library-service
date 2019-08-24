package com.ircnet.service.library.events;

import com.ircnet.service.library.IRCService;
import com.ircnet.service.library.connection.IRCServiceConnection;

public class ChannelModeEvent extends AbstractServiceEvent {
    private String channelName;
    private String modes;

    public ChannelModeEvent(IRCService ircService, IRCServiceConnection ircConnection, String channelName, String modes) {
        this.ircService = ircService;
        this.ircConnection = ircConnection;
        this.channelName = channelName;
        this.modes = modes;
    }

    public String getChannelName() {
        return channelName;
    }

    public String getModes() {
        return modes;
    }
}
