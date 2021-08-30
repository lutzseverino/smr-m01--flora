package me.jasperedits.flora.command.rule;

import me.jasperedits.flora.command.annotation.CommandType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RuleType {
    CommandType.Format[] executeFor() default {CommandType.Format.LEGACY, CommandType.Format.INTERACTIVE};
}
