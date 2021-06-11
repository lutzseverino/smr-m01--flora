package me.jasperedits.commands;

import lombok.SneakyThrows;
import me.jasperedits.commands.rules.RuleRegistry;
import me.jasperedits.daos.GuildDAO;
import me.jasperedits.docs.db.impl.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandService extends ListenerAdapter {

    @SneakyThrows
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        Message message = event.getMessage();
        Guild guild = GuildDAO.getGuild(event.getGuild().getId());

        for (Command command : CommandRegistry.getAllCommands()) {
            CommandType type = command.getClass().getAnnotation(CommandType.class);

            if (type.format() == CommandFormat.INTERACTION)
                continue;

            for (String alias : type.names()) {
                if (event.getMessage().getContentRaw().startsWith(alias.replace("%l", ""), guild.getPrefix().length())) {
                    // Remove first argument because that would be the command itself.
                    List<String> args = new ArrayList<>(Arrays.asList(message.getContentRaw().split(" ")));
                    args.remove(0);

                    // Create a CommandInformation to pass to the rule checker.
                    CommandInformation information = new CommandInformation(args, event, guild);

                    // Run all rule checkers, if one of them is false, the command will not be fired.
                    if (RuleRegistry.runAllRules(CommandFormat.LEGACY, type, information))
                        CommandRegistry.byName(alias).execute(new CommandInformation(args, event, guild));
                }
            }
        }
    }

    @SneakyThrows
    public void onSlashCommand(SlashCommandEvent event) {
        String commandName = event.getName();
        Guild guild = GuildDAO.getGuild(event.getGuild().getId());

        for (Command command : CommandRegistry.getAllCommands()) {
            CommandType type = command.getClass().getAnnotation(CommandType.class);

            if (type.format() == CommandFormat.LEGACY)
                continue;

            for (String alias : type.names()) {
                if (commandName.startsWith(alias.replace("%i", ""))) {

                    // Create a CommandInformation to pass to the rule checker.
                    CommandInformation information = new CommandInformation(event, guild);

                    // Run all rule checkers, if one of them is false, the command will not be fired.
                    if (RuleRegistry.runAllRules(CommandFormat.INTERACTION, type, information)) {
                        CommandRegistry.byName(alias).execute(new CommandInformation(event, guild));
                    }
                }
            }
        }
    }
}