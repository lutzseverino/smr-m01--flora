package me.jasperedits.commands;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.experimental.UtilityClass;
import me.jasperedits.commands.impl.Prefix;
import me.jasperedits.commands.impl.interactions.InteractiveLang;
import me.jasperedits.commands.impl.interactions.InteractivePrefix;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@UtilityClass
public class CommandRegistry {
    private final Map<String, Command> commands;

    static {
        commands = Maps.newHashMap();
        List<Command> commandList = Arrays.asList(
                new InteractivePrefix(),
                new InteractiveLang(),
                new Prefix()
        );

        for (Command commandClass : commandList) {
            for (String alias : commandClass.getClass().getAnnotation(CommandType.class).names()) {
                commands.put(alias.toLowerCase(), commandClass);
            }
        }
    }

    public Command byName(String name) {
        if (!commands.containsKey(name))
            return null;
        return commands.get(name);
    }

    public Collection<Command> getAllCommands() {
        Collection<Command> classes = Sets.newHashSet();
        for (Command command : commands.values()) {
            if (!classes.contains(command)) {
                classes.add(command);
            }
        }
        return classes;
    }

}
