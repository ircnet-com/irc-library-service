package com.ircnet.library.service.connection;

import com.ircnet.library.common.IRCTask;
import com.ircnet.library.common.configuration.ConfigurationModel;
import com.ircnet.library.common.connection.IRCConnection;

import java.util.Date;

public class IRCServiceConnection extends IRCConnection {
    private boolean burst;
    private Date burstStart;

    public IRCServiceConnection(IRCTask ircTask, ConfigurationModel configurationModel) {
        super(ircTask, configurationModel);
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
