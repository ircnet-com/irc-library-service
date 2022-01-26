package com.ircnet.library.service.event;

import com.ircnet.library.service.connection.IRCServiceConnection;

public class UNickEvent extends AbstractServiceEvent {
    private String sid;
    private String uid;
    private String nick;
    private String user;
    private String host;
    private String ipAddress;
    private String userModes;
    private String realName;

    public UNickEvent(IRCServiceConnection ircConnection, String sid, String uid, String nick, String user, String host, String ipAddress, String userModes, String realName) {
        this.ircConnection = ircConnection;
        this.sid = sid;
        this.uid = uid;
        this.nick = nick;
        this.user = user;
        this.host = host;
        this.ipAddress = ipAddress;
        this.userModes = userModes;
        this.realName = realName;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUserModes() {
        return userModes;
    }

    public void setUserModes(String userModes) {
        this.userModes = userModes;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
