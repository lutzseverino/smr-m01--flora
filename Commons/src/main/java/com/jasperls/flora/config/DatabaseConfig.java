package com.jasperls.flora.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DatabaseConfig {
    private String dbHost;
    private String dbPort;
    private String dbUser;
    private String dbPasswd;
    private String dbName;

    public DatabaseConfig() {

    }
}
