package com.jasperls.flora.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DatabaseConfig {

    private String host;
    private int port;
    private String user;
    private String passwd;
    private String name;

    public DatabaseConfig() {

    }
}
