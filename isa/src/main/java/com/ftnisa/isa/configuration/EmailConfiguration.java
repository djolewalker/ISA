package com.ftnisa.isa.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailConfiguration {
    @Value("${isa.mail.host}")
    private String host;
    @Value("${isa.mail.port}")
    private Integer port;
    @Value("${isa.mail.username}")
    private String username;
    @Value("${isa.mail.password}")
    private String password;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "EmailConfiguration [host=" + host + ", port=" + port + ", username=" + username + ", password="
                + password + "]";
    }
}