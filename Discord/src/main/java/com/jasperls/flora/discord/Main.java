package com.jasperls.flora.discord;

import com.jasperls.flora.logger.Log;

public class Main {
    public static void main(String[] args) {
        String configPath;

        if (args.length >= 1) {
            configPath = args[0];
        } else {
            Log.warn(Main.class, "Configuration path was not provided, defaulting to \"./values.yaml\"");
            configPath = "./values.yaml";
        }

        Flora flora = new Flora();

        try {
            flora.init(configPath);
        } catch (Exception e) {
            Log.error(Main.class, "Couldn't initialize Flora Discord");
        }
    }
}
