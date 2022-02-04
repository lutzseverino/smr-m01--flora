package com.jasperls.flora.discord;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.jasperls.flora.CommonsModule;
import com.jasperls.flora.config.Values;
import com.jasperls.flora.logger.Log;
import com.jasperls.flora.yaml.Snakelet;

public class Main {
    public static void main(String[] args) {
        String configPath;

        if (args.length >= 1) {
            configPath = args[0];
        } else {
            Log.warn(Main.class, "Configuration path was not provided, defaulting to \"./values.yaml\"");
            configPath = "./values.yaml";
        }

        Snakelet config = new Snakelet(configPath);
        Injector injector = Guice.createInjector(
                new DiscordModule(),
                new CommonsModule(config.read(Values.class))
        );

        Flora flora = injector.getInstance(Flora.class);

        try {
            flora.init();
        } catch (Exception e) {
            Log.error(Main.class, "The Discord app threw an exception in the initialization phase");
            e.printStackTrace();
        }
    }
}
