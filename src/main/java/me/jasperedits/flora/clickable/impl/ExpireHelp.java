package me.jasperedits.flora.clickable.impl;

import lombok.SneakyThrows;
import me.jasperedits.flora.clickable.Clickable;
import me.jasperedits.flora.clickable.ClickableInformation;
import me.jasperedits.flora.clickable.ClickableType;
import me.jasperedits.flora.guild.GuildDAO;
import me.jasperedits.flora.embed.EmbedFormat;
import me.jasperedits.flora.embed.EmbedTemplate;
import me.jasperedits.flora.manager.Language;
import net.dv8tion.jda.api.EmbedBuilder;

@ClickableType(identifier = "expireHelp")
public class ExpireHelp implements Clickable {

    @SneakyThrows
    @Override
    public void execute(ClickableInformation information) {
        Language language = GuildDAO.getGuild(information.getButtonEvent().getGuild().getId()).getLanguage();

        EmbedBuilder output = new EmbedTemplate(EmbedFormat.DEFAULT, information.getButtonEvent().getMember().getUser()).getEmbedBuilder();

        output.setTitle(language.getValue("error.expire-help.title"));
        output.setDescription(language.getValue("error.expire-help.description"));
        output.addField(
                language.getValue("error.expire-help.field.title"),
                language.getValue("error.expire-help.field.description"),
                false);
        information.getButtonEvent().replyEmbeds(output.build()).setEphemeral(true).queue();
    }
}
