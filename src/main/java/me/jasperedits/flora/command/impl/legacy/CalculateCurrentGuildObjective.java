package me.jasperedits.flora.command.impl.legacy;

import me.jasperedits.flora.command.Command;
import me.jasperedits.flora.command.ExecutionData;
import me.jasperedits.flora.command.annotation.CommandNames;
import me.jasperedits.flora.command.annotation.CommandType;
import me.jasperedits.flora.command.annotation.CommandType.Format;
import me.jasperedits.flora.command.annotation.LonelyCommand;
import me.jasperedits.flora.util.MathUtil;
import net.dv8tion.jda.api.entities.Guild;

@CommandType(
        format = Format.LEGACY
)
@CommandNames("calculate")
public class CalculateCurrentGuildObjective extends Command {

    @LonelyCommand
    public void calculateCurrentGuildObjective(ExecutionData information) {
        Guild JDAGuild = information.getLegacyEvent().getGuild();

        information.getLegacyEvent().getMessage().reply("(<:flora_devMode:876605895264571434>) Variable: " + JDAGuild.getMemberCount() + " = "
                + MathUtil.getGuildObjective(JDAGuild)).mentionRepliedUser(false).queue();
    }
}
