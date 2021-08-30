package me.jasperedits.flora.command.rule;

import me.jasperedits.flora.command.ExecutionData;
import me.jasperedits.flora.command.annotation.CommandType;

public interface Rule {
    /**
     * @param format the command format to answer properly to the user.
     * @param type the annotated command information.
     * @param information the command information to get general information to answer properly and display more data.
     * @return whether the Rule was successful or not.
     */
    boolean check(CommandType.Format format, CommandType type, ExecutionData information);

    void legacyOutput(ExecutionData information);

    void interactionOutput(ExecutionData information);
}
