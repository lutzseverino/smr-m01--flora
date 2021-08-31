package me.jasperedits.flora.command.rule;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import me.jasperedits.flora.command.ExecutionData;
import me.jasperedits.flora.command.annotation.CommandType;
import me.jasperedits.flora.command.rule.impl.*;

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
                new Webhook(),
                new Allowance()
        );

        rules.addAll(ruleList);
    }

    public List<Rule> getAllRules() {
        return rules;
    }

    public boolean runAllRules(CommandType.Format format, CommandType type, ExecutionData information) {
        for (Rule rule : rules) {
            if (Arrays.asList(rule.getClass().getAnnotation(RuleType.class).executeFor()).contains(format) && !rule.check(format, type, information)) {
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
