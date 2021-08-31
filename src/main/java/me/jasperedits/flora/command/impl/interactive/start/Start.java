package me.jasperedits.flora.command.impl.interactive.start;

import me.jasperedits.flora.command.Command;
import me.jasperedits.flora.command.annotation.CommandType;
import net.dv8tion.jda.api.Permission;

@CommandType(permission = Permission.ADMINISTRATOR)
public class Start extends Command {

    public Start() {
        addChild(new StartUser());
        addChild(new StartMessages());
    }
}
