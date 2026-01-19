package com.ircnet.library.service.event;

import com.ircnet.library.service.connection.IRCServiceConnection;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
public class SASLEvent extends AbstractServiceEvent<IRCServiceConnection>  {
    private String serverName;
    private String uidNick;
    private String type;
    private String data;
}
