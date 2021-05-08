package me.jasperedits;

import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import me.jasperedits.docs.Bot;
import me.jasperedits.logging.LogPriority;
import me.jasperedits.logging.LogUtils;
import me.jasperedits.managers.YAMLManager;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Flora {

    static boolean debug;

    public static void main(String[] args) throws IOException {
        List<String> list = Arrays.asList(args);

        debug = list.contains("--debug") || list.contains("-d");

        if (debug) {
            LogUtils.log(LogPriority.INFO, "Debug mode is enabled, extra information will be sent to the console.");
        }

        Bot botValues = new YAMLManager("bot.yaml").buildObject(Bot.class);

        GatewayDiscordClient client = DiscordClientBuilder.create(botValues.getToken())
                .build()
                .login()
                .block();

        assert client != null;
        client.onDisconnect().block();
    }
}
