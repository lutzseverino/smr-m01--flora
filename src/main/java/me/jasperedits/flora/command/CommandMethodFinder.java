package me.jasperedits.flora.command;

import lombok.experimental.UtilityClass;

import java.lang.reflect.Method;

@UtilityClass
public class CommandMethodFinder {

    /**
     * @param path         the command path
     * @param firstCommand the first Command object
     * @return the evaluated final step
     */
    public Method evaluate(String[] path, Command firstCommand) {
        if (firstCommand.getLonelyMethod() != null)
            return firstCommand.getLonelyMethod();

        for (String step : path) {
            if (firstCommand.getSubcommandMap().get(step) != null)
                return firstCommand.getSubcommandMap().get(step).getMethod();

            if (firstCommand.getChildCommandMap().get(step) != null) {
                if (firstCommand.getChildCommandMap().get(step).getSubcommandMap().get(step) != null)
                    return firstCommand.getChildCommandMap().get(step).getSubcommandMap().get(step).getMethod();
            }
        }
        return null;
    }
}
