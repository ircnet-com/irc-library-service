package com.ircnet.library.service.event;

import com.ircnet.library.service.connection.IRCServiceConnection;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
public class NickEvent extends AbstractServiceEvent<IRCServiceConnection>  {
    private String nick;
    private String user;
    private String host;
    private String realName;
    private String serverName;
    private int hopCount;
    private String userModes;
}
