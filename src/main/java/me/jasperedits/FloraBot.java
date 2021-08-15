package me.jasperedits;

import lombok.Getter;
import me.jasperedits.clickable.ClickableService;
import me.jasperedits.command.CommandService;
import me.jasperedits.docs.BotValues;
import me.jasperedits.listeners.Ready;
import me.jasperedits.manager.DatabaseManager;
import me.jasperedits.manager.MongoDatabaseManager;
import me.jasperedits.manager.YAMLManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

@Getter
public class FloraBot {
    public static FloraBot instance;

    private final BotValues botValues;
    private DatabaseManager databaseManager;

    public FloraBot() throws IOException {
        this.botValues = new YAMLManager("botValues.yaml").reader(BotValues.class);
        instance = this;
    }

    public static FloraBot getInstance() {
        return instance;
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
        builder.addEventListeners(new CommandService(), new ClickableService(), new Ready());

        JDA jda = builder.build().getShards().stream().findFirst().get();
    }

    public File getResourceFolder(String path) {
        URL url = this.getClass().getClassLoader().getResource(path);
        File file;
        try {
            assert url != null;
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            file = new File(url.getPath());
        }
        return file;
    }

}
