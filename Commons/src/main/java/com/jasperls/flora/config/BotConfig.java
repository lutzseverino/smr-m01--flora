package com.jasperls.flora.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BotConfig {
    private String token;
    private String status;
    private String activity;

    public BotConfig() {
    }
}
