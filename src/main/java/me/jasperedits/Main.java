package me.jasperedits;

import me.jasperedits.logging.LogPriority;
import me.jasperedits.logging.LogUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<String> list = Arrays.asList(args);

        boolean debug = list.contains("--debug") || list.contains("-d");

        if (debug) {
            LogUtils.log(LogPriority.INFO, "Debug mode is enabled, extra information will be sent to the console.");
        }

        try {
            FloraBot floraBot = new FloraBot(debug);
            floraBot.init();
        } catch (IOException e) {
            LogUtils.log(LogPriority.ERROR, "Couldn't initialize Flora:");
            e.printStackTrace();
        }
    }

}
