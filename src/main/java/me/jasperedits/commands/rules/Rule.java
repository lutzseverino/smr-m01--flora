package me.jasperedits.commands.rules;

import me.jasperedits.commands.Command;
import me.jasperedits.commands.CommandFormat;
import me.jasperedits.commands.CommandInformation;
import me.jasperedits.commands.CommandType;
import me.jasperedits.managers.Language;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public interface Rule {
    // If these return true, the rule is passed.
    boolean check(CommandFormat format, CommandType type, CommandInformation information);

    void legacyOutput(CommandInformation commandInformation);

    void interactionOutput(CommandInformation commandInformation);
}
