package me.jasperedits.commands;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.experimental.UtilityClass;
import me.jasperedits.commands.impl.interactions.Lang;
import me.jasperedits.commands.impl.interactions.Prefix;
import me.jasperedits.logging.LogPriority;
import me.jasperedits.logging.LogUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@UtilityClass
public class CommandRegistry {
    private final Map<String, Command> legacyCommands;
    private final Map<String, Command> interactiveCommands;

    static {
        legacyCommands = Maps.newHashMap();
        interactiveCommands = Maps.newHashMap();

        List<Command> commandList = Arrays.asList(
                new Prefix(),
                new Lang(),
                new me.jasperedits.commands.impl.legacy.Prefix()
        );

        for (Command commandClass : commandList) {
            for (String alias : commandClass.getClass().getAnnotation(CommandType.class).names()) {
                switch (commandClass.getClass().getAnnotation(CommandType.class).format()) {
                    case LEGACY -> legacyCommands.put(alias.toLowerCase(), commandClass);
                    case INTERACTION -> interactiveCommands.put(alias.toLowerCase(), commandClass);
                }
            }
        }
    }

    public Command byName(CommandFormat format, String name) {
        Map<String, Command> commandMap;

        switch (format) {
            case LEGACY -> commandMap = legacyCommands;
            case INTERACTION -> commandMap = interactiveCommands;
            default -> throw new IllegalStateException("Unexpected value: " + format);
        }

        if (!commandMap.containsKey(name)) {
            return null;
        }
        return commandMap.get(name);
    }

    public Collection<Command> getAllCommands(CommandFormat format) {
        Collection<Command> classes = Sets.newHashSet();
        Map<String, Command> commandMap;

        switch (format) {
            case LEGACY -> commandMap = legacyCommands;
            case INTERACTION -> commandMap = interactiveCommands;
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