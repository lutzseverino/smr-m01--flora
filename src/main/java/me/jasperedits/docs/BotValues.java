package me.jasperedits.docs;

import lombok.Getter;

@Getter
public class BotValues {

    public BotValues(String token, String defaultLanguage) {
        this.token = token;
        this.defaultLanguage = defaultLanguage;
    }

    public BotValues() {}

    @Getter
    private String token;

    private String defaultLanguage;

    // Database settings

    private String databaseHostname;

    private int databasePort;

    private String databaseUsername;

    private String databasePassword;

    private String databaseName;

}
