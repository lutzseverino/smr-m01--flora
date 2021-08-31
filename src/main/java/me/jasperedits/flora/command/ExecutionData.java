package me.jasperedits.flora.command;

import lombok.Getter;
import me.jasperedits.flora.docs.db.impl.Guild;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@Getter
public class ExecutionData {
    String[] args;
    MessageReceivedEvent legacyEvent;
    SlashCommandEvent interactionEvent;
    Guild guild;

    public ExecutionData(String[] args, MessageReceivedEvent event, Guild guild) {
        this.args = args;
        this.legacyEvent = event;
        this.guild = guild;
    }

    public ExecutionData(SlashCommandEvent interactionEvent, Guild guild) {
        this.interactionEvent = interactionEvent;
        this.guild = guild;
    }
}
