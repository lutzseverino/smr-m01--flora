package me.jasperedits.clickable.impl;

import lombok.SneakyThrows;
import me.jasperedits.clickable.Clickable;
import me.jasperedits.clickable.ClickableInformation;
import me.jasperedits.clickable.ClickableType;
import me.jasperedits.guild.GuildDAO;
import me.jasperedits.embed.EmbedFormat;
import me.jasperedits.embed.EmbedTemplate;
import me.jasperedits.manager.Language;
import net.dv8tion.jda.api.EmbedBuilder;

@ClickableType(identifier = "expireHelp")
public class ExpireHelp implements Clickable {

    @SneakyThrows
    @Override
    public void execute(ClickableInformation information) {
        Language language = GuildDAO.getGuild(information.getButtonEvent().getGuild().getId()).getLanguage();

        // Create the embed used for output.
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
