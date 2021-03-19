package com.ircnet.library.service.connection;

import com.ircnet.library.common.connection.IRCConnection;
import com.ircnet.library.service.ServiceConfigurationModel;

import java.util.Date;

public class IRCServiceConnection extends IRCConnection {
    private ServiceConfigurationModel serviceConfiguration;
    private boolean burst;
    private Date burstStart;

    public IRCServiceConnection(ServiceConfigurationModel configuration) {
        super(configuration);
        this.serviceConfiguration = configuration;
    }

    public ServiceConfigurationModel getServiceConfiguration() {
        return serviceConfiguration;
    }

    public boolean isBurst() {
        return burst;
    }

    public void setBurst(boolean burst) {
        this.burst = burst;
    }

    public Date getBurstStart() {
        return burstStart;
    }

    public void setBurstStart(Date burstStart) {
        this.burstStart = burstStart;
    }
}
