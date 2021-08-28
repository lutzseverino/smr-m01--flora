package me.jasperedits.command;

import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;

public interface Command {
    void execute(CommandData information);
    void button(ButtonClickEvent event, CommandData information);
}
