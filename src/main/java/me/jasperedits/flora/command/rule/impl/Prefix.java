package me.jasperedits.flora.command.rule.impl;

import me.jasperedits.flora.command.ExecutionData;
import me.jasperedits.flora.command.annotation.CommandType;
import me.jasperedits.flora.command.rule.Rule;
import me.jasperedits.flora.command.rule.RuleType;

@RuleType(executeFor = CommandType.Format.LEGACY)
public class Prefix implements Rule {
    @Override
    public boolean check(CommandType.Format format, CommandType type, ExecutionData data) {
        return data.getLegacyEvent().getMessage().getContentRaw().startsWith(data.getGuild().getPrefix());
    }

    @Override
    public void legacyOutput(ExecutionData information) {
    }

    @Override
    public void interactionOutput(ExecutionData information) {
    }

}
