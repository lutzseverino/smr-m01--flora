package me.jasperedits.buttons;

import lombok.SneakyThrows;
import me.jasperedits.commands.CommandFormat;
import me.jasperedits.commands.CommandInformation;
import me.jasperedits.commands.CommandRegistry;
import me.jasperedits.daos.GuildDAO;
import me.jasperedits.embeds.EmbedFormat;
import me.jasperedits.embeds.EmbedTemplate;
import me.jasperedits.managers.Language;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Collections;

public class ButtonService extends ListenerAdapter {
    @SneakyThrows
    @Override
    public void onButtonClick(ButtonClickEvent event) {
        CommandInformation information = ExecutorCache.get(event.getMessage().getId());

        if (information != null) {
            CommandRegistry.byName(CommandFormat.INTERACTIVE, information.getInteractionEvent().getName()).button(event, information);
        } else {
            Language language = GuildDAO.getGuild(event.getGuild().getId()).getLanguage();
            EmbedBuilder output = new EmbedTemplate(EmbedFormat.DEFAULT, event.getMember().getUser()).getEmbedBuilder();

            output.setTitle(language.getValue("buttons.error.expire.title"));
            output.setDescription(language.getValue("buttons.error.expire.message"));
            event.deferEdit().setEmbeds(output.build()).setActionRows(Collections.emptyList()).queue();
        }
    }
}
