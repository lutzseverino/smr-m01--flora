package me.jasperedits.flora.command.impl.interactive;

import me.jasperedits.flora.command.Command;
import me.jasperedits.flora.command.annotation.CommandType;
import me.jasperedits.flora.command.impl.interactive.start.StartMessages;
import me.jasperedits.flora.command.impl.interactive.start.StartUser;
import net.dv8tion.jda.api.Permission;

@CommandType(permission = Permission.ADMINISTRATOR)
public class Start extends Command {

    public Start() {
        addChild(new StartUser());
        addChild(new StartMessages());
    }
}
