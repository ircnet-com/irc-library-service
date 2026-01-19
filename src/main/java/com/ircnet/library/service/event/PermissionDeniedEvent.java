package com.ircnet.library.service.event;

import com.ircnet.library.service.connection.IRCServiceConnection;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
public class PermissionDeniedEvent extends AbstractServiceEvent<IRCServiceConnection>  {
    private String serverName;
    private String message;
}
