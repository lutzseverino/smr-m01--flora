package me.jasperedits.commands;

import lombok.Getter;
import me.jasperedits.docs.impl.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

@Getter
public class CommandInformation {
    List<String> args;
    GuildMessageReceivedEvent event;
    Guild guild;

    public CommandInformation(List<String> args, GuildMessageReceivedEvent event, Guild guild) {
        this.args = args;
        this.event = event;
        this.guild = guild;
    }
}
