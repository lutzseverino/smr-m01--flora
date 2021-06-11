package me.jasperedits;

import lombok.Getter;
import me.jasperedits.commands.CommandService;
import me.jasperedits.docs.BotValues;
import me.jasperedits.listeners.Ready;
import me.jasperedits.managers.DatabaseManager;
import me.jasperedits.managers.MongoDatabaseManager;
import me.jasperedits.managers.document.YAMLManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

@Getter
public class FloraBot {

    public static FloraBot instance;

    private final boolean debug;

    private final BotValues botValues;
    private DatabaseManager databaseManager;

    public FloraBot(boolean debug) throws IOException {
        this.debug = debug;
        this.botValues = new YAMLManager("botValues.yaml").reader(BotValues.class);
        instance = this;
    }

    public void init() throws LoginException {

        this.databaseManager = new MongoDatabaseManager(
                this.botValues.getDatabaseHostname(),
                this.botValues.getDatabasePort(),
                this.botValues.getDatabaseUsername(),
                this.botValues.getDatabasePassword(),
                this.botValues.getDatabaseName()
        );


        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(this.botValues.getToken());

        builder.setBulkDeleteSplittingEnabled(false);
        builder.setActivity(Activity.watching("your community grow"));
        builder.addEventListeners(new CommandService(), new Ready());

        JDA jda = builder.build().getShards().stream().findFirst().get();
    }

    public File getResourceFolder(String path) {
        URL url = this.getClass().getClassLoader().getResource(path);
        File file = null;
        try {
            assert url != null;
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            file = new File(url.getPath());
        } finally {
            return file;
        }
    }

    public static FloraBot getInstance() {
        return instance;
    }

}
