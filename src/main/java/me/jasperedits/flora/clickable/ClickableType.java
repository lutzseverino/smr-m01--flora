package me.jasperedits.flora.clickable;

import net.dv8tion.jda.api.Permission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)

public @interface ClickableType {

    String identifier();

    Permission permission() default Permission.VIEW_CHANNEL;

    boolean isPrivate() default false;
}
