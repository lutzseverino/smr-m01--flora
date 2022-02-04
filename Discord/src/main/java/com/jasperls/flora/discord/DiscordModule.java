package com.jasperls.flora.discord;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import com.jasperls.flora.discord.commands.impl.Config;
import com.jasperls.flora.discord.commands.impl.Ping;
import com.jasperls.flora.discord.commands.impl.Privacy;
import com.jasperls.flora.discord.commands.impl.Update;
import com.jasperls.rimor.jda.type.JDACommand;

public class DiscordModule extends AbstractModule {
    @Override
    protected void configure() {
        Multibinder<JDACommand> commands = Multibinder.newSetBinder(binder(), JDACommand.class);

        commands.addBinding().to(Config.class);
        commands.addBinding().to(Ping.class);
        commands.addBinding().to(Privacy.class);
        commands.addBinding().to(Update.class);
    }
}
