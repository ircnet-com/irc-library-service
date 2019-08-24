package com.ircnet.library.service.parser;

import com.ircnet.library.service.connection.IRCServiceConnection;

@FunctionalInterface
public interface ParserMethod {
    void parse(IRCServiceConnection ircConnection, String[] parts);
}