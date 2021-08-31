package me.jasperedits.flora.command;

import lombok.Getter;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Method;

@UtilityClass
public class CommandMethodFinder {

    @Getter
    Object finalObject;

    /**
     * @param path         the command path
     * @param firstCommand the first Command object
     * @return the evaluated final step
     */
    public Method evaluate(String[] path, Command firstCommand) {
        if (firstCommand.getLonelyMethod() != null) {
            finalObject = firstCommand; return firstCommand.getLonelyMethod();
        }

        MethodSubcommand methodSubcommand;
        boolean isNested = false;
        String nestedStep = null;

        for (String step : path) {
            methodSubcommand = firstCommand.getSubcommandMap().get(step);

            if (methodSubcommand != null) {
                finalObject = methodSubcommand.getChild(); return methodSubcommand.getMethod();
            }

            if (firstCommand.getChildCommandMap().get(step) != null) {
                isNested = true;
                nestedStep = step;
                continue;
            }

            methodSubcommand = firstCommand.getChildCommandMap().get(nestedStep).getSubcommandMap().get(step);

            if (isNested && methodSubcommand != null) {
                finalObject = methodSubcommand.getChild(); return methodSubcommand.getMethod();
            }
        }
        return null;
    }
}
