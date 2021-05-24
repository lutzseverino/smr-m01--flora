package me.jasperedits.commands;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.experimental.UtilityClass;
import me.jasperedits.commands.impl.Prefix;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@UtilityClass
public class CommandRegistry {
    private final Map<String, Class<? extends Command>> commands;

    static {
        commands = Maps.newHashMap();
        List<Class<? extends Command>> commandList = Arrays.asList(
                Prefix.class
        );

        for (Class<? extends Command> commandClass : commandList) {
            for (String alias : commandClass.getAnnotation(CommandType.class).names()) {
                commands.put(alias.toLowerCase(), commandClass);
            }
        }
    }

    public Command byName(String name) {
        if (!commands.containsKey(name))
            return null;

        try {
            // This will create a memory leak.
            return commands.get(name).getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Collection<Class<? extends Command>> getAllCommands() {
        Collection<Class<? extends Command>> classes = Sets.newHashSet();
        for (Class<? extends Command> clazz : commands.values()) {
            if (!classes.contains(clazz)) {
                classes.add(clazz);
            }
        }
        return classes;
    }

}
