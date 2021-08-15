package me.jasperedits.command.rule;

import me.jasperedits.command.settings.CommandFormat;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RuleType {
    CommandFormat[] applyFor() default {CommandFormat.LEGACY, CommandFormat.INTERACTIVE};
}
