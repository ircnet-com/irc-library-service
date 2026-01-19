package com.ircnet.library.service.event;

import com.ircnet.library.service.connection.IRCServiceConnection;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
public class ServerEvent extends AbstractServiceEvent<IRCServiceConnection>  {
    private String serverName;
    private int hopCount;
    private String sid;
    private String info;
}
