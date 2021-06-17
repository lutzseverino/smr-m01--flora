package me.jasperedits.buttons.impl;

import lombok.SneakyThrows;
import me.jasperedits.buttons.Clickable;
import me.jasperedits.buttons.ClickableInformation;
import me.jasperedits.buttons.ClickableType;
import me.jasperedits.daos.GuildDAO;
import me.jasperedits.embeds.EmbedFormat;
import me.jasperedits.embeds.EmbedTemplate;
import me.jasperedits.managers.Language;
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
