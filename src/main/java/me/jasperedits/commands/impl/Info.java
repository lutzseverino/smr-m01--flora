package me.jasperedits.commands.impl;

import me.jasperedits.commands.Command;
import me.jasperedits.commands.CommandInformation;
import me.jasperedits.commands.CommandType;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

@CommandType(names = {"info", "information", "información"})
public class Info implements Command {
    GuildMessageReceivedEvent event;
    Command command;

    public Info() {
    }

    @Override
    public void execute(CommandInformation command) {
        command.getEvent().getChannel().sendMessage("To-do").queue();
    }
}
