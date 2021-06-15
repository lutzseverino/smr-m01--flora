package me.jasperedits.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;

public interface Command {
    void execute(CommandInformation information);
    void error(CommandInformation information, EmbedBuilder output, String errorMessage);
    void button(ButtonClickEvent event, CommandInformation information);
}
