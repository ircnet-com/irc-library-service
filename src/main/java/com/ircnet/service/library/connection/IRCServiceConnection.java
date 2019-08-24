package com.ircnet.service.library.connection;

import com.ircnet.common.library.IRCTask;
import com.ircnet.common.library.configuration.ConfigurationModel;
import com.ircnet.common.library.connection.IRCConnection;

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
