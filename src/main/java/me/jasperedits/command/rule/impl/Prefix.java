package me.jasperedits.command.rule.impl;

import me.jasperedits.command.settings.CommandFormat;
import me.jasperedits.command.CommandData;
import me.jasperedits.command.annotation.CommandType;
import me.jasperedits.command.rule.Rule;
import me.jasperedits.command.rule.RuleType;

@RuleType(applyFor = CommandFormat.LEGACY)
public class Prefix implements Rule {
    @Override
    public boolean check(CommandFormat format, CommandType type, CommandData information) {
        return information.getLegacyEvent().getMessage().getContentRaw().startsWith(information.getGuild().getPrefix());
    }

    @Override
    public void legacyOutput(CommandData information) {
    }

    @Override
    public void interactionOutput(CommandData information) {
    }

}
