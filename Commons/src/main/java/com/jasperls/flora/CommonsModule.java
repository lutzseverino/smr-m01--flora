package com.jasperls.flora;

import com.google.inject.AbstractModule;
import com.jasperls.flora.config.BotConfig;
import com.jasperls.flora.config.DatabaseConfig;
import com.jasperls.flora.config.Values;

public class CommonsModule extends AbstractModule {

    private final Values values;

    public CommonsModule(Values values) {
        this.values = values;
    }

    @Override
    protected void configure() {
        bind(BotConfig.class).toInstance(values.getBotConfig());
        bind(DatabaseConfig.class).toInstance(values.getDatabaseConfig());
    }
}
