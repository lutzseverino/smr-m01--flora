package me.jasperedits;

import com.mongodb.client.model.Filters;
import discord4j.common.util.Snowflake;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import lombok.Getter;
import me.jasperedits.docs.BotValues;
import me.jasperedits.docs.Guild;
import me.jasperedits.managers.DatabaseManager;
import me.jasperedits.managers.MongoDatabaseManager;
import me.jasperedits.managers.YAMLManager;

import java.io.IOException;

@Getter
public class FloraBot {

    private final boolean debug;
    private final BotValues botValues;

    private DatabaseManager databaseManager;

    public FloraBot(boolean debug) throws IOException {
        this.debug = debug;
        this.botValues = new YAMLManager("botValues.yaml").buildObject(BotValues.class);
    }

    public void init() {
        this.databaseManager = new MongoDatabaseManager(
                this.botValues.getDatabaseHostname(),
                this.botValues.getDatabasePort(),
                this.botValues.getDatabaseUsername(),
                this.botValues.getDatabasePassword(),
                this.botValues.getDatabaseName()
        );

        GatewayDiscordClient client = DiscordClientBuilder.create(this.botValues.getToken())
                .build()
                .login()
                .block();

        client.onDisconnect().block();
    }
}
