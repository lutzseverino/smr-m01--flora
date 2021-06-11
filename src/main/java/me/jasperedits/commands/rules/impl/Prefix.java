package me.jasperedits.commands.rules.impl;

import me.jasperedits.commands.CommandFormat;
import me.jasperedits.commands.CommandInformation;
import me.jasperedits.commands.CommandType;
import me.jasperedits.commands.rules.Rule;
import me.jasperedits.commands.rules.RuleType;

@RuleType(applyFor = CommandFormat.LEGACY)
public class Prefix implements Rule {
    @Override
    public boolean check(CommandFormat format, CommandType type, CommandInformation information) {
        return information.getLegacyEvent().getMessage().getContentRaw().startsWith(information.getGuild().getPrefix());
    }

    @Override
    public void legacyOutput(CommandInformation information) {
    }

    @Override
    public void interactionOutput(CommandInformation information) {
    }

}
