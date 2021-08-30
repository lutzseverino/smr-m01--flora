package me.jasperedits.flora.command;

import lombok.SneakyThrows;
import me.jasperedits.flora.command.annotation.CommandType;
import me.jasperedits.flora.command.rule.RuleRegistry;
import me.jasperedits.flora.docs.db.impl.Guild;
import me.jasperedits.flora.guild.GuildDAO;
import me.jasperedits.flora.util.DiscordUtil;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.commons.lang3.ArrayUtils;

public class CommandInterpreter extends ListenerAdapter {

    @SneakyThrows
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message message = event.getMessage();
        Guild guild = GuildDAO.getGuild(event.getGuild().getId());

//        if (event.getMessage().getAuthor().isBot()) return;
//
//        for (Command command : CommandRegistry.getAllCommands(CommandFormat.LEGACY)) {
//            CommandNames commandNames = command.getClass().getAnnotation(CommandNames.class);
//            CommandType type = command.getClass().getAnnotation(CommandType.class);
//
//            for (String alias : commandNames.value()) {
//                if (event.getMessage().getContentRaw().startsWith(alias, guild.getPrefix().length())) {
//                    // Remove first argument because that would be the command itself.
//                    List<String> args = new ArrayList<>(Arrays.asList(message.getContentRaw().split(" ")));
//                    args.remove(0);
//
//                    // Create a CommandInformation to pass to the rule checker.
//                    ExecutionData information = new ExecutionData(args, event, guild);
//
//                    // Run all rule checkers, if one of them is false, the command will not be fired.
//                    // if (RuleRegistry.runAllRules(CommandFormat.LEGACY, type, information))
//                    //    CommandRegistry.byName(CommandFormat.LEGACY, alias).execute(new ExecutionData(args, event, guild));
//                }
//            }
//        }
    }

    @SneakyThrows
    public void onSlashCommand(SlashCommandEvent event) {
        Guild guild = GuildDAO.getGuild(event.getGuild().getId());
        String[] path = event.getCommandPath().split("/");
        Command command = CommandRegistry.byName(CommandType.Format.INTERACTIVE, event.getName());

        path = ArrayUtils.remove(path, 0);

        ExecutionData data = new ExecutionData(event, guild);

        if (RuleRegistry.runAllRules(CommandType.Format.INTERACTIVE, command.getClass().getAnnotation(CommandType.class), data)) {
            if (CommandMethodFinder.evaluate(path, command) != null)
                CommandMethodFinder.evaluate(path, command).invoke(command, data);
            else DiscordUtil.throwUnknownCommand(event);
        }
    }

}