package com.jasperls.flora.discord.commands.impl;

import com.jasperls.flora.discord.commands.CommandInfo;
import com.jasperls.rimor.annotation.MethodCommand;
import com.jasperls.rimor.jda.type.JDACommand;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;

public class Update extends JDACommand {
    @MethodCommand
    public void run(CommandInfo info) {
        info.getEvent().getGuild().updateCommands().addCommands(
                new CommandData("config", "Sets Flora up")
                        .addSubcommands(new SubcommandData("language", "Change Flora's language per-guild")
                                .addOptions(new OptionData(OptionType.STRING, "code", "A language", true)
                                        .addChoice("Español", "es_ES")
                                        .addChoice("English", "en_US")
                                        .addChoice("Català", "ca_ES")
                                )),
                new CommandData("ping", "Ping Flora"),
                new CommandData("update", "Update commands"),
                new CommandData("privacy", "Check privacy-related information")).queue();

        info.getEvent().reply("Guild command list updated.").setEphemeral(true).queue();
    }
}
