package me.jasperedits.commands;

import lombok.SneakyThrows;
import me.jasperedits.docs.GuildDAO;
import me.jasperedits.docs.impl.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandService extends ListenerAdapter {

    @SneakyThrows
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        Member member = event.getMember();
        Guild guild = GuildDAO.getGuild(event.getGuild().getId());
        String prefix = guild.getPrefix();

        if (event.isWebhookMessage() || member.getUser().isBot()) {
            return;
        }

        for (Class<? extends Command> command : CommandRegistry.getAllCommands()) {
            for (String alias : command.getAnnotation(CommandType.class).names()) {
                if (message.equals(prefix + alias)) {
                    List<String> args = new ArrayList<>(Arrays.asList(message.split(" ")));
                    args.remove(0);

                    CommandRegistry.byName(alias).execute(new CommandInformation(args, event, guild));
                }
            }
        }
    }
}
