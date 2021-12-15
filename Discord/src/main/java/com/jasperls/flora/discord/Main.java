package com.jasperls.flora.discord;

import com.jasperls.flora.logger.Log;

public class Main {
    public static void main(String[] args) {
        String configPath = args[0];

        Flora flora = new Flora();

        try {
            flora.init(configPath);
        } catch (Exception e) {
            Log.error(Main.class, "Couldn't initialize Flora Discord");
        }
    }
}
