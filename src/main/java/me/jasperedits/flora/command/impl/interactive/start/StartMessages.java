package me.jasperedits.flora.command.impl.interactive.start;

import me.jasperedits.flora.command.ExecutionData;
import me.jasperedits.flora.command.ChildCommand;
import me.jasperedits.flora.command.annotation.CommandNames;
import me.jasperedits.flora.command.annotation.Subcommand;

@CommandNames("messages")
public class StartMessages extends ChildCommand {

    @Subcommand
    public void goal(ExecutionData data) {

    }
}
