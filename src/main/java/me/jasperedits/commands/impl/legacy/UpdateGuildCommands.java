package me.jasperedits.commands.impl.legacy;

import me.jasperedits.commands.Command;
import me.jasperedits.commands.CommandFormat;
import me.jasperedits.commands.CommandInformation;
import me.jasperedits.commands.CommandType;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;

@CommandType(format = CommandFormat.LEGACY, names = "update")
public class UpdateGuildCommands implements Command {
    @Override
    public void execute(CommandInformation command) {
        command.getLegacyEvent().getGuild().updateCommands().addCommands(
                new CommandData("prefix", "Changes the legacy prefix on this guild.")
                        .addOption(OptionType.STRING, "prefix", "a new prefix", true),
                new CommandData("language", "Changes the language on this guild.")
                        .addOption(OptionType.STRING, "language", "A valid language code.", true),
                new CommandData("privacy", "A small privacy notice."),
                new CommandData("config", "An easy way to setup the bot.")
                        .addSubcommands(new SubcommandData("channel", "Change the achievement channel")
                                .addOption(OptionType.CHANNEL, "channel", "The channel where Flora will send the achievement.", true))
                        .addSubcommands(new SubcommandData("language", "Change the language")
                                .addOption(OptionType.STRING, "language", "A valid language code.", true)))
                .queue();

        command.getLegacyEvent().getMessage().reply("Guild commands have been updated.").mentionRepliedUser(false).queue();
    }
}
