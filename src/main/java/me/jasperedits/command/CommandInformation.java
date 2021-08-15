package me.jasperedits.command;

import lombok.Getter;
import me.jasperedits.docs.db.impl.Guild;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

@Getter
public class CommandInformation {
    List<String> args;
    MessageReceivedEvent legacyEvent;
    SlashCommandEvent interactionEvent;
    Guild guild;

    public CommandInformation(List<String> args, MessageReceivedEvent event, Guild guild) {
        this.args = args;
        this.legacyEvent = event;
        this.guild = guild;
    }

    public CommandInformation(SlashCommandEvent interactionEvent, Guild guild) {
        this.interactionEvent = interactionEvent;
        this.guild = guild;
    }
}
