package me.jasperedits.flora.command;

import com.google.common.collect.Maps;
import lombok.Getter;
import me.jasperedits.flora.command.annotation.CommandNames;
import me.jasperedits.flora.command.annotation.LonelyCommand;
import me.jasperedits.flora.command.annotation.Subcommand;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Getter
public abstract class Command {

    private Method lonelyMethod;
    private final Map<String, MethodSubcommand> subcommandMap = Maps.newHashMap();
    private final Map<String, ChildCommand> childCommandMap = Maps.newHashMap();

    public Command() {
        for (Method method : this.getClass().getMethods()) {
            List<String> names = new ArrayList<>();

            if (method.isAnnotationPresent(CommandNames.class) && method.getAnnotation(CommandNames.class).value() != null)
                names = Arrays.asList(method.getAnnotation(CommandNames.class).value());
            else names.add(method.getName().toLowerCase());

            if (method.isAnnotationPresent(LonelyCommand.class)) {
                    this.lonelyMethod = method;
            }

            if (method.isAnnotationPresent(Subcommand.class)) {
                    for (String name : names) {
                        this.subcommandMap.put(name.toLowerCase(), new MethodSubcommand(method, this));
                    }
                this.subcommandMap.put(method.getName().toLowerCase(), new MethodSubcommand(method, this));
            }
        }
    }

    public void addChild(ChildCommand childCommand) {
        CommandNames names = childCommand.getClass().getAnnotation(CommandNames.class);

        if (names != null) {
            for (String name : names.value()) {
                this.childCommandMap.put(name.toLowerCase(), childCommand);
            }
        }

        this.childCommandMap.put(childCommand.getClass().getSimpleName().toLowerCase(), childCommand);
    }
}
