package com.jasperls.flora.discord.commands;

import com.jasperls.flora.docs.impl.Guild;
import com.jasperls.flora.guild.GuildRepositoryImpl;
import com.jasperls.rimor.jda.interpreter.JDAInterpreter;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import java.util.Objects;

public class CommandInterpreter extends ListenerAdapter {

    @Inject private JDAInterpreter coreInterpreter;
    @Inject private GuildRepositoryImpl guildRepository;

    @SneakyThrows
    @Override
    public void onSlashCommand(@NotNull SlashCommandEvent event) {
        Guild guild = guildRepository.get(Objects.requireNonNull(event.getGuild()).getId());
        CommandInfo commandInfo = new CommandInfo(guild);
        commandInfo.setEvent(event);

        coreInterpreter.execute(event.getCommandPath().split("/"), commandInfo);
    }
}
