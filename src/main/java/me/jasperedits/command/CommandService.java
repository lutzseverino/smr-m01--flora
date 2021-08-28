package me.jasperedits.command;

import lombok.SneakyThrows;
import me.jasperedits.command.annotation.CommandType;
import me.jasperedits.command.rule.RuleRegistry;
import me.jasperedits.command.settings.CommandFormat;
import me.jasperedits.guild.GuildDAO;
import me.jasperedits.docs.db.impl.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandService extends ListenerAdapter {

    @SneakyThrows
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message message = event.getMessage();
        Guild guild = GuildDAO.getGuild(event.getGuild().getId());

        for (Command command : CommandRegistry.getAllCommands(CommandFormat.LEGACY)) {
            CommandType type = command.getClass().getAnnotation(CommandType.class);

            for (String alias : type.names()) {
                if (event.getMessage().getContentRaw().startsWith(alias, guild.getPrefix().length())) {
                    // Remove first argument because that would be the command itself.
                    List<String> args = new ArrayList<>(Arrays.asList(message.getContentRaw().split(" ")));
                    args.remove(0);

                    // Create a CommandInformation to pass to the rule checker.
                    CommandData information = new CommandData(args, event, guild);

                    // Run all rule checkers, if one of them is false, the command will not be fired.
                    if (RuleRegistry.runAllRules(CommandFormat.LEGACY, type, information))
                        CommandRegistry.byName(CommandFormat.LEGACY, alias).execute(new CommandData(args, event, guild));
                }
            }
        }
    }

    @SneakyThrows
    public void onSlashCommand(SlashCommandEvent event) {
        String commandName = event.getName();
        Guild guild = GuildDAO.getGuild(event.getGuild().getId());

        for (Command command : CommandRegistry.getAllCommands(CommandFormat.INTERACTIVE)) {
            CommandType type = command.getClass().getAnnotation(CommandType.class);

            for (String alias : type.names()) {
                if (commandName.equals(alias)) {

                    // Create a CommandInformation to pass to the rule checker.
                    CommandData information = new CommandData(event, guild);

                    // Run all rule checkers, if one of them is false, the command will not be fired.
                    if (RuleRegistry.runAllRules(CommandFormat.INTERACTIVE, type, information)) {
                        CommandRegistry.byName(CommandFormat.INTERACTIVE, alias).execute(new CommandData(event, guild));
                    }
                }
            }
        }
    }

}