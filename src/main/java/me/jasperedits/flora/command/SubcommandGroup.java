package me.jasperedits.flora.command;

import com.google.common.collect.Maps;
import lombok.Getter;
import me.jasperedits.flora.command.annotation.CommandNames;
import me.jasperedits.flora.command.annotation.LonelyCommand;
import me.jasperedits.flora.command.annotation.Subcommand;

import java.lang.reflect.Method;
import java.util.Map;

@Getter
public abstract class SubcommandGroup extends Command {

    private final Map<String, MethodSubcommand> subcommandMap = Maps.newHashMap();

    public SubcommandGroup() {
        for (Method method : this.getClass().getMethods()) {
            CommandNames names = method.getAnnotation(CommandNames.class);

            if (method.isAnnotationPresent(LonelyCommand.class)) {
                throw new IllegalArgumentException("A subcommand group cannot contain a lonely command");
            }

            if (method.isAnnotationPresent(Subcommand.class)) {
                if (names != null) {
                    for (String name : names.value()) {
                        this.subcommandMap.put(name.toLowerCase(), new MethodSubcommand(method, this));
                    }
                }
                this.subcommandMap.put(method.getName().toLowerCase(), new MethodSubcommand(method, this));
            }
        }
    }
}

