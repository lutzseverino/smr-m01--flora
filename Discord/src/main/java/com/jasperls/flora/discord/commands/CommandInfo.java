package com.jasperls.flora.discord.commands;

import com.jasperls.flora.docs.impl.Guild;
import com.jasperls.rimor.jda.data.JDAExecutionData;
import lombok.Getter;

@Getter
public class CommandInfo extends JDAExecutionData {
    private final Guild guild;

    public CommandInfo(Guild guild) {
        this.guild = guild;
    }
}
