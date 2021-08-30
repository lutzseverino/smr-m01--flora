package me.jasperedits.flora;

import lombok.Getter;
import me.jasperedits.flora.clickable.ClickableService;
import me.jasperedits.flora.command.CommandInterpreter;
import me.jasperedits.flora.docs.BotValues;
import me.jasperedits.flora.manager.DatabaseManager;
import me.jasperedits.flora.manager.MongoDatabaseManager;
import me.jasperedits.flora.manager.YAMLManager;
import me.jasperedits.flora.listeners.Ready;
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
        builder.addEventListeners(new CommandInterpreter(), new ClickableService(), new Ready());

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
