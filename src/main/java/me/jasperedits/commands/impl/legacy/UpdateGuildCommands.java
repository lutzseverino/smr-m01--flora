package me.jasperedits.commands.impl.legacy;

import me.jasperedits.commands.Command;
import me.jasperedits.commands.CommandInformation;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

public class UpdateGuildCommands implements Command {
    @Override
    public void execute(CommandInformation command) {
        command.getLegacyEvent().getGuild().updateCommands().addCommands(
                new CommandData("prefix", "Changes the legacy prefix on this guild.")
                        .addOption(OptionType.STRING, "prefix", "your new prefix... (max. of 3 characters long)", true),
                new CommandData("language", "Changes the language on this guild.")
                        .addOption(OptionType.STRING, "language", "the new language...", true))
                .queue();

        command.getLegacyEvent().getMessage().reply("Guild commands have been updated.");
    }
}
