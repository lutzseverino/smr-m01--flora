package me.jasperedits.flora.command;

import lombok.SneakyThrows;
import me.jasperedits.flora.command.annotation.CommandType;
import me.jasperedits.flora.command.rule.RuleRegistry;
import me.jasperedits.flora.docs.db.impl.Guild;
import me.jasperedits.flora.guild.GuildDAO;
import me.jasperedits.flora.util.DiscordUtil;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Method;

public class CommandInterpreter extends ListenerAdapter {

    @SneakyThrows
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Guild guild = GuildDAO.getGuild(event.getGuild().getId());

        if (event.getMessage().getAuthor().isBot() || !event.getMessage().getContentRaw().startsWith(guild.getPrefix()))
            return;

        String[] path = event.getMessage().getContentRaw().split(" ");
        Command command = CommandRegistry.byName(CommandType.Format.LEGACY, path[0].replace(guild.getPrefix(), ""));

        path = ArrayUtils.remove(path, 0);

        ExecutionData data = new ExecutionData(path, event, guild);

        if (command != null && RuleRegistry.runAllRules(CommandType.Format.LEGACY, command.getClass().getAnnotation(CommandType.class), data)) {
            Method finalStep = CommandMethodFinder.evaluate(path, command);
            if (finalStep != null)
                CommandMethodFinder.evaluate(path, command).invoke(command, data);
        }
    }

    @SneakyThrows
    @Override
    public void onSlashCommand(SlashCommandEvent event) {
        Guild guild = GuildDAO.getGuild(event.getGuild().getId());
        String[] path = event.getCommandPath().split("/");
        Command command = CommandRegistry.byName(CommandType.Format.INTERACTIVE, event.getName());

        path = ArrayUtils.remove(path, 0);

        ExecutionData data = new ExecutionData(event, guild);

        if (command != null && RuleRegistry.runAllRules(CommandType.Format.INTERACTIVE, command.getClass().getAnnotation(CommandType.class), data)) {
            Method finalStep = CommandMethodFinder.evaluate(path, command);

            if (finalStep != null) {
                finalStep.invoke(CommandMethodFinder.getFinalObject(), data);
                return;
            }
        }
        DiscordUtil.throwUnknownCommand(event);
    }
}