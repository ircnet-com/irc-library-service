package com.ircnet.library.service.connection;

import com.ircnet.library.common.connection.IRCConnection;
import com.ircnet.library.service.ServiceConfigurationModel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class IRCServiceConnection extends IRCConnection {
    @Setter(AccessLevel.NONE)
    private ServiceConfigurationModel serviceConfiguration;

    private boolean burst;
    private Date burstStart;

    public IRCServiceConnection(ServiceConfigurationModel configuration) {
        super(configuration);
        this.serviceConfiguration = configuration;
    }
}
