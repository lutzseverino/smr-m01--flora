package me.jasperedits;

import me.jasperedits.logging.LogPriority;
import me.jasperedits.logging.LogUtils;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<String> list = Arrays.asList(args);

        try {
            FloraBot floraBot = new FloraBot();
            floraBot.init();
        } catch (IOException | LoginException e) {
            LogUtils.log(LogPriority.ERROR, "Couldn't initialize Flora:");
            e.printStackTrace();
        }
    }

}
