package com.ircnet.library.service.event;

import com.ircnet.library.service.connection.IRCServiceConnection;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
public class NickChangeEvent extends AbstractServiceEvent<IRCServiceConnection>  {
    private String uid;
    private String newNick;
}
