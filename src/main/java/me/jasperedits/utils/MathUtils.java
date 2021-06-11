package me.jasperedits.utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import me.jasperedits.daos.GuildDAO;
import net.dv8tion.jda.api.entities.Guild;

@UtilityClass
public class MathUtils {

    @SneakyThrows
    public int getGuildObjective(Guild guild) {
        return (int) (((10 * guild.getMemberCount()) / 3) - GuildDAO.getGuild(guild.getId()).getMessageCount());
    }
}
