package com.ircnet.library.service;

import com.ircnet.library.common.IRCTask;
import com.ircnet.library.common.configuration.ConfigurationModel;
import com.ircnet.library.common.connection.IRCConnection;
import com.ircnet.library.service.connection.IRCServiceConnection;

public class IRCServiceTask extends IRCTask {
    private IRCConnection ircConnection;

    private ServiceConfigurationModel configurationModel;

    public IRCServiceTask(ServiceConfigurationModel configurationModel) {
        super();

        this.configurationModel = configurationModel;
        this.ircConnection = new IRCServiceConnection(this, configurationModel);
    }

    public void setIRCConnection(IRCConnection ircConnection) {
        this.ircConnection = ircConnection;
    }

    @Override
    public ConfigurationModel getConfiguration() {
        return configurationModel;
    }

    public ServiceConfigurationModel getServiceConfigurationModel() {
        return configurationModel;
    }

    public IRCConnection getIRCConnection() {
        return ircConnection;
    }
}
