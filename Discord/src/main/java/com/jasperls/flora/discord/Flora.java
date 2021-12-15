package com.jasperls.flora.discord;

import com.jasperls.flora.config.Values;
import com.jasperls.flora.logger.Log;
import com.jasperls.flora.yaml.Snakelet;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;

import javax.security.auth.login.LoginException;

public class Flora {

    public void init(String config) {
        Snakelet configYaml = new Snakelet(config);
        Values values = configYaml.read(Values.class);

        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(values.getBotConfig().getToken());
        builder.setBulkDeleteSplittingEnabled(false);

        String status = values.getBotConfig().getStatus();

        switch (values.getBotConfig().getActivity()) {
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
