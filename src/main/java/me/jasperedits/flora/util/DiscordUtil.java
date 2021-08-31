package me.jasperedits.flora.util;

import lombok.experimental.UtilityClass;
import me.jasperedits.flora.embed.EmbedFormat;
import me.jasperedits.flora.embed.EmbedTemplate;
import me.jasperedits.flora.guild.GuildDAO;
import me.jasperedits.flora.manager.Language;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.components.Button;
import net.dv8tion.jda.api.interactions.components.Component;

import java.util.concurrent.ExecutionException;

@UtilityClass
public class DiscordUtil {
    public void throwExpiration(ButtonClickEvent event) throws ExecutionException {
        Language language = GuildDAO.getGuild(event.getGuild().getId()).getLanguage();
        EmbedBuilder output = new EmbedTemplate(EmbedFormat.DEFAULT, event.getMember().getUser()).getEmbedBuilder();

        output.setTitle(language.getValue("error.expire.title"));
        output.setDescription(language.getValue("error.expire.description"));
        event.deferEdit().setEmbeds(output.build()).setActionRow(
                Button.secondary("expireHelp", language.getValue("commons.what"))
                        .withEmoji(Emoji.fromMarkdown("<:flora_help:854516649206218762>")),
                addWikiComponent(language))
                .queue();
    }

    public void throwUnknownCommand(SlashCommandEvent event)  throws ExecutionException  {
        Language language = GuildDAO.getGuild(event.getGuild().getId()).getLanguage();
        EmbedBuilder output = new EmbedTemplate(EmbedFormat.DEFAULT, event.getMember().getUser()).getEmbedBuilder();

        output.setTitle(language.getValue("error.unknown-command.title"));
        output.setDescription(language.getValue("error.unknown-command.description"));
        event.deferReply().addEmbeds(output.build()).addActionRow(
                        addWikiComponent(language))
                .queue();
    }

    public Component addWikiComponent(Language language) {
        return Button.link(language.getValue("commons.wiki-link"), language.getValue("commons.wiki"))
                .withEmoji(Emoji.fromMarkdown("<:flora_wiki:858759484557099038>"));
    }
}
