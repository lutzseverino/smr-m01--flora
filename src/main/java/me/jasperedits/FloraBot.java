package me.jasperedits;

import lombok.Getter;
import me.jasperedits.commands.impl.Prefix;
import me.jasperedits.docs.impl.BotValues;
import me.jasperedits.listeners.Ready;
import me.jasperedits.listeners.TestCommand;
import me.jasperedits.managers.DatabaseManager;
import me.jasperedits.managers.MongoDatabaseManager;
import me.jasperedits.managers.document.YAMLManager;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;

import javax.security.auth.login.LoginException;
import java.io.IOException;

@Getter
public class FloraBot {

    private final boolean debug;
    private final BotValues botValues;
    private DefaultShardManagerBuilder builder;

    public static DatabaseManager databaseManager;

    public FloraBot(boolean debug) throws IOException {
        this.debug = debug;
        this.botValues = new YAMLManager("botValues.yaml").buildObject(BotValues.class);
    }

    public void init() throws LoginException {

        databaseManager = new MongoDatabaseManager(
                this.botValues.getDatabaseHostname(),
                this.botValues.getDatabasePort(),
                this.botValues.getDatabaseUsername(),
                this.botValues.getDatabasePassword(),
                this.botValues.getDatabaseName()
        );

        this.builder = DefaultShardManagerBuilder.createDefault(this.botValues.getToken());

        builder.setBulkDeleteSplittingEnabled(false);
        builder.setActivity(Activity.watching("your community grow"));
        this.registerListeners();
        builder.build();
    }

    public static FloraBot getInstance() {
        return instance;
    }

}
