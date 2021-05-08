package me.jasperedits.docs;

import lombok.Getter;
import lombok.Setter;

public class Bot {

    public Bot(String token, String defaultLanguage) {
        this.token = token;
        this.defaultLanguage = defaultLanguage;
    }

    // Jackson will throw an exception without a default constructor
    public Bot() {}

    @Getter
    private String token;
    @Setter
    @Getter
    private String defaultLanguage;

    @Override
    public String toString() {
        return "Token: " + token + "\nDefault language: " + defaultLanguage;
    }
}
