package com.ircnet.service.library;

import com.ircnet.common.library.Client;
import com.ircnet.common.library.IRCTask;
import com.ircnet.common.library.Parser;
import com.ircnet.common.library.configuration.ConfigurationModel;
import com.ircnet.common.library.connection.IRCConnection;
import com.ircnet.common.library.event.EventBus;
import com.ircnet.service.library.connection.IRCServiceConnection;
import com.ircnet.service.library.events.ConnectionStatusChangedEventListener;
import com.ircnet.service.library.events.EndOfBurstEventListener;
import com.ircnet.service.library.events.ServSetEventListener;
import com.ircnet.service.library.events.YouAreServiceEventListener;
import com.ircnet.service.library.parser.ParserImpl;

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
