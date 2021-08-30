package me.jasperedits.flora.command.impl.interactive.start;

import me.jasperedits.flora.command.Command;

public class Start extends Command {

    public Start() {
        addSubcommandGroup(new StartUser());
        addSubcommandGroup(new StartMessages());
    }
}
