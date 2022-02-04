package com.jasperls.flora.discord;

import com.jasperls.flora.config.BotConfig;
import com.jasperls.flora.discord.commands.CommandInterpreter;
import com.jasperls.flora.logger.Log;
import com.jasperls.rimor.Rimor;
import com.jasperls.rimor.jda.type.JDACommand;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.security.auth.login.LoginException;
import java.util.Set;

@Singleton
public class Flora {
    @Inject
    private BotConfig config;
    @Inject
    private CommandInterpreter commandInterpreter;
    @Inject
    private Set<JDACommand> commands;

    public void init() {
        ShardManager shardManager = null;

        try {
            shardManager = DefaultShardManagerBuilder.createDefault(config.getToken())
                    .setBulkDeleteSplittingEnabled(false)
                    .build();
        } catch (LoginException e) {
            Log.error(Main.class, "JDA couldn't login to provided application");
        }

        assert shardManager != null;

        String status = config.getStatus();

        switch (config.getActivity()) {
            case "competing" -> shardManager.setActivity(Activity.competing(status));
            case "listening" -> shardManager.setActivity(Activity.listening(status));
            case "playing" -> shardManager.setActivity(Activity.playing(status));
            default -> shardManager.setActivity(Activity.watching(status));
        }

        this.registerListeners(shardManager);
        this.registerCommands();
    }

    private void registerListeners(ShardManager shardManager) {
        shardManager.addEventListener(
                commandInterpreter
        );
    }

    private void registerCommands() {
        Rimor.INSTANCE.registerCommands(
                commands
        );
    }
}
