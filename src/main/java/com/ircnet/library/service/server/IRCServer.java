package com.ircnet.library.service.server;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

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
     * Hop count.
     */
    private int hopCount;

    /**
     * Info / Description.
     */
    private String info;

    /**
     * Nodes.
     */
    private List<IRCServer> nodes;

    public IRCServer() {
        this.nodes = new ArrayList<>();
    }
}
