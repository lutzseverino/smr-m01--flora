package com.jasperls.flora.discord;

import com.jasperls.flora.config.Values;
import com.jasperls.flora.yaml.YamlSimplifier;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;

import javax.security.auth.login.LoginException;

public class Flora {

    public Flora() {
    }

    public void init(String config) {
        YamlSimplifier configYaml = new YamlSimplifier(config);
        Values values = configYaml.read(Values.class);

        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(values.getBotConfig().getToken());

        builder.setBulkDeleteSplittingEnabled(false);

        switch (values.getBotConfig().getActivity()) {
            case "competing" -> builder.setActivity(Activity.competing(values.getBotConfig().getStatus()));
            case "listening" -> builder.setActivity(Activity.listening(values.getBotConfig().getStatus()));
            case "playing" -> builder.setActivity(Activity.playing(values.getBotConfig().getStatus()));
            default -> builder.setActivity(Activity.watching(values.getBotConfig().getStatus()));
        }

        try {
            builder.build().getShards().stream().findFirst().get();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }
}
