package me.jasperedits.docs;

import lombok.Getter;

@Getter
public class BotValues {

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
