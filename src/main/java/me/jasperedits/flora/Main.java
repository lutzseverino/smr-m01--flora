package me.jasperedits.flora;

import me.jasperedits.flora.logging.LogPriority;
import me.jasperedits.flora.logging.LogUtils;

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
            LogUtils.log("Main", "Couldn't initialize Flora:", LogPriority.ERROR);
            e.printStackTrace();
        }
    }

}
