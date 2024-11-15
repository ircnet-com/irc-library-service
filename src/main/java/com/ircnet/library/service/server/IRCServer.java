package com.ircnet.library.service.server;

import lombok.Data;

/**
 * Represents a linked IRC server.
 */
@Data
public class IRCServer {
    /**
     * Server ID.
     */
    private String sid;

    /**
     * Name.
     */
    private String name;

    /**
     * hop count.
     */
    private int hopCount;

    /**
     * Info / Description.
     */
    private String info;
}
