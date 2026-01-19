package com.ircnet.library.service.event;

import com.ircnet.library.service.connection.IRCServiceConnection;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
public class UNickEvent extends AbstractServiceEvent<IRCServiceConnection>  {
    private String sid;
    private String uid;
    private String nick;
    private String user;
    private String host;
    private String ipAddress;
    private String userModes;
    private String account;
    private String realName;
}
