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

    /**
     * @param args  the parsed legacy arguments that were passed though the event.
     * @param event the event containing all the message event data.
     * @param guild the database {@link Guild} object that is triggering the event.
     */
    public ExecutionData(String[] args, MessageReceivedEvent event, Guild guild) {
        this.args = args;
        this.legacyEvent = event;
        this.guild = guild;
    }

    /**
     * @param interactionEvent the event containing all the interaction's data.
     * @param guild            the database {@link Guild} object that is triggering the event.
     */
    public ExecutionData(SlashCommandEvent interactionEvent, Guild guild) {
        this.interactionEvent = interactionEvent;
        this.guild = guild;
    }
}
