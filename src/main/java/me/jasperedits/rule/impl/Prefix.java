package me.jasperedits.rule.impl;

import me.jasperedits.command.CommandFormat;
import me.jasperedits.command.CommandInformation;
import me.jasperedits.command.CommandType;
import me.jasperedits.rule.Rule;
import me.jasperedits.rule.RuleType;

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
