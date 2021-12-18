package com.jasperls.flora.discord;

import com.jasperls.flora.config.BotConfig;
import com.jasperls.flora.logger.Log;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.security.auth.login.LoginException;

@Singleton
public class Flora {

    @Inject
    private BotConfig config;

    public void init() {
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(config.getToken());
        builder.setBulkDeleteSplittingEnabled(false);

        String status = config.getStatus();

        switch (config.getActivity()) {
            case "competing" -> builder.setActivity(Activity.competing(status));
            case "listening" -> builder.setActivity(Activity.listening(status));
            case "playing" -> builder.setActivity(Activity.playing(status));
            default -> builder.setActivity(Activity.watching(status));
        }

        try {
            builder.build();
        } catch (LoginException e) {
            Log.error(Main.class, "JDA couldn't login to provided application");
        }
    }
}
