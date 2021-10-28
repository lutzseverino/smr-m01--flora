package me.jasperedits.flora.logging;

import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@UtilityClass
public class LogUtils {

    public void log(String reason, String message, LogPriority priority) {
        Logger logger = LoggerFactory.getLogger(reason);

        switch (priority) {
            case INFO -> logger.info(message);
            case WARN -> logger.warn(message);
            case ERROR -> logger.error(message);
            case DEBUG -> logger.debug(message);
        }
    }
}
