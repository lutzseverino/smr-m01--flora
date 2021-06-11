package me.jasperedits.commands.rules;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import me.jasperedits.commands.CommandFormat;
import me.jasperedits.commands.CommandInformation;
import me.jasperedits.commands.CommandType;
import me.jasperedits.commands.rules.impl.Arguments;
import me.jasperedits.commands.rules.impl.Permissions;
import me.jasperedits.commands.rules.impl.Prefix;
import me.jasperedits.commands.rules.impl.Webhook;

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
                new Webhook()
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
                    case LEGACY:
                        rule.legacyOutput(information);
                    case INTERACTION:
                        rule.interactionOutput(information);
                }
                return false;
            }
        }
        return true;
    }
}
