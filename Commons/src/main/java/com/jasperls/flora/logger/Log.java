package com.jasperls.flora.logger;

import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@UtilityClass
public class Log {
    public void info(Class<?> clazz, String message) {
        log(clazz, message, LogPriority.INFO);
    }

    public void warn(Class<?> clazz, String message) {
        log(clazz, message, LogPriority.WARN);
    }

    public void error(Class<?> clazz, String message) {
        log(clazz, message, LogPriority.ERROR);
    }

    public void debug(Class<?> clazz, String message) {
        log(clazz, message, LogPriority.DEBUG);
    }

    private void log(Class<?> clazz, String message, LogPriority priority) {
        Logger logger = LoggerFactory.getLogger(clazz);

        switch (priority) {
            case INFO -> logger.info(message);
            case WARN -> logger.warn(message);
            case ERROR -> logger.error(message);
            case DEBUG -> logger.debug(message);
        }
    }
}