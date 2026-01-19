package com.ircnet.library.service.event;

import com.ircnet.library.common.event.AbstractEvent;
import com.ircnet.library.common.event.EventContext;
import com.ircnet.library.service.connection.IRCServiceConnection;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Getter
@SuperBuilder(toBuilder = true)
public abstract class AbstractServiceEvent<T extends IRCServiceConnection>
        extends AbstractEvent<T> {
    protected Map<String, String> messageTags;
    protected String raw;

    protected AbstractServiceEvent(EventContext<T> context) {
        super(context);
    }
}
