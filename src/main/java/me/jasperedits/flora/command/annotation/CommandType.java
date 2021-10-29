package me.jasperedits.flora.command.annotation;

import net.dv8tion.jda.api.Permission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CommandType {

    Format format() default Format.INTERACTIVE;

    Permission permission() default Permission.VIEW_CHANNEL;

    Allowance allowance() default Allowance.CHAT_GUILD;

    int minArguments() default 0;

        int maxArguments() default 0;

    enum Format {
        INTERACTIVE,
        LEGACY
    }

    enum Allowance {
        CHAT_DM,
        BOT_OWNER,
        SERVER_OWNER,
        CHAT_GUILD,
        EVERYTHING
    }

}

