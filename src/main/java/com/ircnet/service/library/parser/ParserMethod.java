package com.ircnet.service.library.parser;

import com.ircnet.service.library.connection.IRCServiceConnection;

@FunctionalInterface
public interface ParserMethod {
    void parse(IRCServiceConnection ircConnection, String[] parts);
}