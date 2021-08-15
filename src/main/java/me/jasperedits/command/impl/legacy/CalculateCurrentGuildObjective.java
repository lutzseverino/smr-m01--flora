package me.jasperedits.command.impl.legacy;

import me.jasperedits.command.Command;
import me.jasperedits.command.CommandInformation;
import me.jasperedits.command.annotation.CommandType;
import me.jasperedits.command.settings.CommandFormat;
import me.jasperedits.util.MathUtil;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;

@CommandType(
        format = CommandFormat.LEGACY,
        names = "ccgo"
)
public class CalculateCurrentGuildObjective implements Command {

    @Override
    public void execute(CommandInformation information) {
        Guild JDAGuild = information.getLegacyEvent().getGuild();

        information.getLegacyEvent().getMessage().reply("(<:flora_devMode:876605895264571434>) Variable: " + JDAGuild.getMemberCount() + " = "
                + MathUtil.getGuildObjective(JDAGuild)).mentionRepliedUser(false).queue();
    }

    @Override
    public void button(ButtonClickEvent event, CommandInformation information) {
    }
}
