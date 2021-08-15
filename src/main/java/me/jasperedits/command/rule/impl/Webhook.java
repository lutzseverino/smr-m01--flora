package me.jasperedits.command.rule.impl;

import me.jasperedits.command.settings.CommandFormat;
import me.jasperedits.command.CommandInformation;
import me.jasperedits.command.annotation.CommandType;
import me.jasperedits.command.rule.Rule;
import me.jasperedits.command.rule.RuleType;

@RuleType(applyFor = CommandFormat.LEGACY)
public class Webhook implements Rule {
    @Override
    public boolean check(CommandFormat format, CommandType type, CommandInformation information) {
        return !information.getLegacyEvent().isWebhookMessage();
    }

    @Override
    public void legacyOutput(CommandInformation information) {
    }

    @Override
    public void interactionOutput(CommandInformation information) {
    }
}
