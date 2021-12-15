package com.jasperls.flora.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Values {
    private BotConfig botConfig;
    private DatabaseConfig dbConfig;

    public Values() {
    }
}
