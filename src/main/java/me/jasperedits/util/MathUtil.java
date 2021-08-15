package me.jasperedits.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import me.jasperedits.guild.GuildDAO;
import net.dv8tion.jda.api.entities.Guild;

@UtilityClass
public class MathUtil {

    @SneakyThrows
    public int getGuildObjective(Guild guild) {
        return (int) (((Math.random()*(5-3+1)+3 * guild.getMemberCount())) - GuildDAO.getGuild(guild.getId()).getMessageCount());
    }
}
