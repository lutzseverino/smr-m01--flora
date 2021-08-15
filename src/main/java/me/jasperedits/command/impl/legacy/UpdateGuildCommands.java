package me.jasperedits.command.impl.legacy;

import me.jasperedits.command.Command;
import me.jasperedits.command.settings.CommandAllowance;
import me.jasperedits.command.settings.CommandFormat;
import me.jasperedits.command.CommandInformation;
import me.jasperedits.command.annotation.CommandType;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;

@CommandType(
        format = CommandFormat.LEGACY,
        names = "update",
        allowance = CommandAllowance.BOT_OWNER
)
public class UpdateGuildCommands implements Command {
    @Override
    public void execute(CommandInformation information) {
        information.getLegacyEvent().getGuild().updateCommands().addCommands(
                        new CommandData("prefix", "Changes the legacy prefix on this guild.")
                                .addOption(OptionType.STRING, "prefix", "a new prefix", true),
                        new CommandData("privacy", "A small privacy notice."),
                        new CommandData("config", "Sets Flora up.")
                                .addSubcommands(new SubcommandData("messagechannel", "Change this guild's message objective channel.")
                                        .addOption(OptionType.CHANNEL, "channel", "The text channel where Flora will send the message objective.", true))
                                .addSubcommands(new SubcommandData("language", "Change the language of this guild or get a list of available languages.")
                                        .addOption(OptionType.STRING, "code", "A valid language code.")),
                        new CommandData("setup", "Get quick tutorials on how to set Flora up."),
                        new CommandData("ping", "Ping Flora."))
                .queue();

        information.getLegacyEvent().getMessage().reply("(<:flora_devMode:876605895264571434>) Flora requested to update guild commands to Discord.").mentionRepliedUser(false).queue();
    }

    @Override
    public void button(ButtonClickEvent event, CommandInformation information) {
    }
}
