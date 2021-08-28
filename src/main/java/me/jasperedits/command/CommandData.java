package me.jasperedits.command;

import lombok.Getter;
import me.jasperedits.docs.db.impl.Guild;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

@Getter
public class CommandData {
    List<String> args;
    MessageReceivedEvent legacyEvent;
    SlashCommandEvent interactionEvent;
    Guild guild;

    public CommandData(List<String> args, MessageReceivedEvent event, Guild guild) {
        this.args = args;
        this.legacyEvent = event;
        this.guild = guild;
    }

    public CommandData(SlashCommandEvent interactionEvent, Guild guild) {
        this.interactionEvent = interactionEvent;
        this.guild = guild;
    }
}
