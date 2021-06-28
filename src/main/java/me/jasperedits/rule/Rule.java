package me.jasperedits.rule;

import me.jasperedits.command.CommandFormat;
import me.jasperedits.command.CommandInformation;
import me.jasperedits.command.CommandType;

public interface Rule {
    /**
     * @param format the command format to answer properly to the user.
     * @param type the annotated command information.
     * @param information the command information to get general information to answer properly and display more data.
     * @return whether the Rule was successful or not.
     */
    boolean check(CommandFormat format, CommandType type, CommandInformation information);

    void legacyOutput(CommandInformation information);

    void interactionOutput(CommandInformation information);
}
