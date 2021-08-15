package me.jasperedits.command;

import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;

public interface Command {
    void execute(CommandInformation information);
    void button(ButtonClickEvent event, CommandInformation information);
}
