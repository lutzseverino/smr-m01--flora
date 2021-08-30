package me.jasperedits.flora.command;

import lombok.Getter;

import java.lang.reflect.Method;

@Getter
public final class MethodSubcommand {
    private final Method method;
    private final Command parent;

    public MethodSubcommand(Method method, Command parent) {
        this.method = method;
        this.parent = parent;
    }
}
