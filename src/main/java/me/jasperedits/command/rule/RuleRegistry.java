package me.jasperedits.command.rule;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import me.jasperedits.command.rule.impl.*;
import me.jasperedits.command.settings.CommandFormat;
import me.jasperedits.command.CommandInformation;
import me.jasperedits.command.annotation.CommandType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@UtilityClass
public class RuleRegistry {
    @Getter
    private final List<Rule> rules = new ArrayList<>();

    static {
        List<Rule> ruleList = Arrays.asList(
                new Permissions(),
                new Arguments(),
                new Prefix(),
                new Webhook(),
                new Allowance()
        );

        rules.addAll(ruleList);
    }

    public List<Rule> getAllRules() {
        return rules;
    }

    public boolean runAllRules(CommandFormat format, CommandType type, CommandInformation information) {
        for (Rule rule : rules) {
            if (Arrays.asList(rule.getClass().getAnnotation(RuleType.class).applyFor()).contains(format) && !rule.check(format, type, information)) {
                switch (type.format()) {
                    case LEGACY -> rule.legacyOutput(information);
                    case INTERACTIVE -> rule.interactionOutput(information);
                }
                return false;
            }
        }
        return true;
    }
}
