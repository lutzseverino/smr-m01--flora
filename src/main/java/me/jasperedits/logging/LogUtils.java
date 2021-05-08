package me.jasperedits.logging;

import lombok.experimental.UtilityClass;

@UtilityClass
public class LogUtils {

    public void log(LogPriority priority, String message) {
        System.out.println("[ " + priority + "] " + message);
    }
}
