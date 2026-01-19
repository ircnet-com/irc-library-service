package com.ircnet.library.service.event;

import com.ircnet.library.common.User;
import com.ircnet.library.service.connection.IRCServiceConnection;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
public class ServSetEvent extends AbstractServiceEvent<IRCServiceConnection>  {
    private User from;
    private int acceptedSettings;
}
