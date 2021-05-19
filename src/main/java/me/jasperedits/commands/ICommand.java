package me.jasperedits.commands;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public interface ICommand {
    void onGuildMessageReceived(GuildMessageReceivedEvent event);

    void execute(Command command);
}
