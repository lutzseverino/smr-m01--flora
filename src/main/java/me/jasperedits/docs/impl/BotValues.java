package me.jasperedits.docs.impl;

import lombok.Getter;

@Getter
public class BotValues {

    public BotValues(String token, String defaultLanguage) {
        this.token = token;
        this.defaultLanguage = defaultLanguage;
    }

    // Jackson needs an empty constructor for it to work.
    public BotValues() {}

    private String token;

    private String defaultLanguage;

    // Database settings

    private String databaseHostname;

    private int databasePort;

    private String databaseUsername;

    private String databasePassword;

    private String databaseName;

}
