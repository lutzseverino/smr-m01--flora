package me.jasperedits.commands;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.internal.utils.tuple.Pair;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CommandType {

    CommandFormat format() default CommandFormat.LEGACY;

    String[] names();

    Permission permission() default Permission.VIEW_CHANNEL;

    int minArguments() default 0;

    int maxArguments() default 0;
}
