package me.jasperedits.commands.impl.legacy;

import me.jasperedits.commands.Command;
import me.jasperedits.commands.CommandFormat;
import me.jasperedits.commands.CommandInformation;
import me.jasperedits.commands.CommandType;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;

@CommandType(format = CommandFormat.LEGACY, names = "update")
public class UpdateGuildCommands implements Command {
    @Override
    public void execute(CommandInformation information) {
        information.getLegacyEvent().getGuild().updateCommands().addCommands(
                new CommandData("prefix", "Changes the legacy prefix on this guild.")
                        .addOption(OptionType.STRING, "prefix", "a new prefix", true),
                new CommandData("privacy", "A small privacy notice."),
                new CommandData("config", "An easy way to setup the bot.")
                        .addSubcommands(new SubcommandData("channel", "Change the achievement channel of this guild.")
                                .addOption(OptionType.CHANNEL, "channel", "The text channel where Flora will send the achievement.", true))
                        .addSubcommands(new SubcommandData("language", "Change the language of this guild or get a list of available languages.")
                                .addOption(OptionType.STRING, "code", "A valid language code.")),
                new CommandData("setup", "Learn how to setup Flora"))
                .queue();

        information.getLegacyEvent().getMessage().reply("Guild commands have been updated.").mentionRepliedUser(false).queue();
    }

    @Override
    public void error(CommandInformation information, EmbedBuilder output, String errorMessage) {

    }

    @Override
    public void button(ButtonClickEvent event, CommandInformation information) {

    }
}
