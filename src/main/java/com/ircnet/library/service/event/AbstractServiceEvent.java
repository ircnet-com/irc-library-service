package com.ircnet.library.service.event;

import com.ircnet.library.common.event.AbstractEvent;
import com.ircnet.library.service.connection.IRCServiceConnection;

import java.util.Map;

public class AbstractServiceEvent extends AbstractEvent<IRCServiceConnection> {
    protected Map<String, String> messageTags;

    public AbstractServiceEvent() {
        super();
    }

    public AbstractServiceEvent(Object source) {
        super(source);
    }

    public Map<String, String> getMessageTags() {
        return messageTags;
    }

    public void setMessageTags(Map<String, String> messageTags) {
        this.messageTags = messageTags;
    }
}
