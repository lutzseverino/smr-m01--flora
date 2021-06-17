package me.jasperedits.commands;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.experimental.UtilityClass;
import me.jasperedits.commands.impl.interactive.*;
import me.jasperedits.commands.impl.legacy.UpdateGuildCommands;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@UtilityClass
public class CommandRegistry {
    private final Map<String, Command> legacyCommands;
    private final Map<String, Command> interactiveCommands;

    static {
        legacyCommands = Maps.newHashMap();
        interactiveCommands = Maps.newHashMap();

        List<Command> commandList = Arrays.asList(
                new PrefixUpdate(),
                new Setup(),
                new Config(),
                new Privacy(),
                new UpdateGuildCommands()
        );

        for (Command commandClass : commandList) {
            for (String alias : commandClass.getClass().getAnnotation(CommandType.class).names()) {
                switch (commandClass.getClass().getAnnotation(CommandType.class).format()) {
                    case LEGACY -> legacyCommands.put(alias.toLowerCase(), commandClass);
                    case INTERACTIVE -> interactiveCommands.put(alias.toLowerCase(), commandClass);
                }
            }
        }
    }

    /**
     * @param format the command format to fetch.
     * @param name   a name of the command.
     * @return a Command that matches said name and format.
     */
    public Command byName(CommandFormat format, String name) {
        Map<String, Command> commandMap;

        switch (format) {
            case LEGACY -> commandMap = legacyCommands;
            case INTERACTIVE -> commandMap = interactiveCommands;
            default -> throw new IllegalStateException("Unexpected value: " + format);
        }

        return commandMap.getOrDefault(name, null);
    }

    /**
     * @param format the command format to fetch.
     * @return a Collection of Command of said format.
     */
    public Collection<Command> getAllCommands(CommandFormat format) {
        Collection<Command> classes = Sets.newHashSet();
        Map<String, Command> commandMap;

        switch (format) {
            case LEGACY -> commandMap = legacyCommands;
            case INTERACTIVE -> commandMap = interactiveCommands;
            default -> throw new IllegalStateException("Unexpected value: " + format);
        }

        for (Command command : commandMap.values()) {
            if (!classes.contains(command)) {
                classes.add(command);
            }
        }
        return classes;
    }

}