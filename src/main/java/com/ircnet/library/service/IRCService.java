package com.ircnet.library.service;

import com.ircnet.library.common.Client;
import com.ircnet.library.common.IRCTask;
import com.ircnet.library.common.Parser;
import com.ircnet.library.common.User;
import com.ircnet.library.common.configuration.ConfigurationModel;
import com.ircnet.library.common.connection.IRCConnection;
import com.ircnet.library.common.event.EventBus;
import com.ircnet.library.service.connection.IRCServiceConnection;
import com.ircnet.library.service.event.ConnectionStatusChangedEventListener;
import com.ircnet.library.service.event.EndOfBurstEventListener;
import com.ircnet.library.service.event.ServSetEventListener;
import com.ircnet.library.service.event.YouAreServiceEventListener;
import com.ircnet.library.service.parser.ParserImpl;

import java.util.Collections;
import java.util.Formatter;
import java.util.List;

public class IRCService extends IRCTask implements Client {
    /**
     * The IRC connection.
     */
    private IRCConnection ircConnection;

    private ServiceConfigurationModel configurationModel;

    public IRCService(ServiceConfigurationModel configurationModel) {
        super();

        this.configurationModel = configurationModel;
        this.ircConnection = new IRCServiceConnection(this, configurationModel);
        this.parser = new ParserImpl(this);
    }

    @Override
    protected void registerEventListeners() {
        EventBus.registerEventListener(0, new ConnectionStatusChangedEventListener(this));
        EventBus.registerEventListener(0, new YouAreServiceEventListener());
        EventBus.registerEventListener(0, new EndOfBurstEventListener());
        EventBus.registerEventListener(0, new ServSetEventListener());
    }

    public void notice(User user, String format, Object... args) {
        notice(user.getNick(), format, args);
    }

    public void notice(String nick, String format, Object... args) {
        String content = new Formatter().format(format, args).toString();
        getIRCConnection().send("NOTICE %s :%s", nick, content);
    }

    @Override
    public Parser getParser() {
        return parser;
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

    @Override
    public List<Client> getClientList() {
        return Collections.singletonList(this);
    }
}
