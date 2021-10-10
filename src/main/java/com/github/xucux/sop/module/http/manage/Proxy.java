package com.github.xucux.sop.module.http.manage;

/**
 * @descriptions:
 * @author: xucl
 * @date: 2021/8/20
 * @version: 1.0
 */
public class Proxy {

    private String host;
    private int port;
    private String username;
    private String password;

    public String getHost() {
        return host;
    }

    public Proxy setHost(String host) {
        this.host = host;
        return this;
    }

    public int getPort() {
        return port;
    }

    public Proxy setPort(int port) {
        this.port = port;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public Proxy setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Proxy setPassword(String password) {
        this.password = password;
        return this;
    }
}
