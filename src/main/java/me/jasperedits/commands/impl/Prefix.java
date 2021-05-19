package me.jasperedits.commands.impl;

import lombok.SneakyThrows;
import me.jasperedits.commands.Command;
import me.jasperedits.commands.ICommand;
import me.jasperedits.docs.GuildDAO;
import me.jasperedits.docs.impl.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;

public class Prefix extends ListenerAdapter implements ICommand {
    GuildMessageReceivedEvent event;
    Command command;

    @SneakyThrows
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        User user = event.getMember().getUser();
        Guild guild = GuildDAO.getGuild(event.getGuild().getId());

        this.event = event;
        this.command = new Command("prefix", message, guild, user);
        if (command.isValid()) execute(command);
    }

    @Override
    public void execute(Command command) {
        List<String> args = command.getArgs();
        Guild guild = command.getGuild();

        if (args.size() > 0) {
            guild.setPrefix(args.get(0));
            GuildDAO.updateGuild(guild);
            event.getChannel().sendMessage("Prefix changed to " + args.get(0)).queue();
        } else {
            event.getChannel().sendMessage("Current prefix is "+ guild.getPrefix()).queue();
        }
    }
}
