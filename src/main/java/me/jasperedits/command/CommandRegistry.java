package me.jasperedits.command;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.experimental.UtilityClass;
import me.jasperedits.command.annotation.CommandType;
import me.jasperedits.command.impl.interactive.*;
import me.jasperedits.command.impl.legacy.CalculateCurrentGuildObjective;
import me.jasperedits.command.impl.legacy.UpdateGuildCommands;
import me.jasperedits.command.settings.CommandFormat;

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
                new Prefix(),
                new Setup(),
                new Ping(),
                new Config(),
                new Privacy(),
                new CalculateCurrentGuildObjective(),
                new UpdateGuildCommands() // Update interactions on the current guild. Only for testing purposes.
        );

        commandList.forEach(commandClass -> {
            for (String alias : commandClass.getClass().getAnnotation(CommandType.class).names()) {
                switch (commandClass.getClass().getAnnotation(CommandType.class).format()) {
                    case LEGACY -> legacyCommands.put(alias.toLowerCase(), commandClass);
                    case INTERACTIVE -> interactiveCommands.put(alias.toLowerCase(), commandClass);
                }
            }
        });
    }

    /**
     * @param format the command format to fetch.
     * @param name   a command name.
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
     * @return a Command Collection of said format.
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