package me.jasperedits.commands;

import lombok.Getter;
import me.jasperedits.docs.impl.Guild;
import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class Command {
    List<String> args;
    Guild guild;
    boolean valid;

    public Command(String rawCommand, String message, Guild guild, User user) {
        this.guild = guild;
        args = new ArrayList<String>();
        if (message.startsWith(guild.getPrefix() + rawCommand) && !user.isBot()) {
            args.addAll(Arrays.asList(message.split(" ")));
            args.remove(0);

            valid = true;
        }

    }
}
