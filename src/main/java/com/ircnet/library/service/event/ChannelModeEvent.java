package com.ircnet.library.service.event;

import com.ircnet.library.service.IRCService;
import com.ircnet.library.service.connection.IRCServiceConnection;

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
