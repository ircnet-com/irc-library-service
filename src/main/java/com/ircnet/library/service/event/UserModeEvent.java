package com.ircnet.library.service.event;

import com.ircnet.library.service.connection.IRCServiceConnection;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
public class UserModeEvent extends AbstractServiceEvent<IRCServiceConnection>  {
    private String nick;
    private String modes;
}
