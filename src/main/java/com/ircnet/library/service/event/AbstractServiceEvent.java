package com.ircnet.library.service.event;

import com.ircnet.library.common.event.AbstractEvent;
import com.ircnet.library.service.connection.IRCServiceConnection;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class AbstractServiceEvent extends AbstractEvent<IRCServiceConnection> {
    protected Map<String, String> messageTags;

    public AbstractServiceEvent() {
        super();
    }

    public AbstractServiceEvent(Object source) {
        super(source);
    }
}
