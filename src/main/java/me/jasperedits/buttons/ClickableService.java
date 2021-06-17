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
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.Button;

import java.util.concurrent.ExecutionException;

public class ClickableService extends ListenerAdapter {

    @SneakyThrows
    @Override
    public void onButtonClick(ButtonClickEvent event) {
        Clickable clickable = ClickableRegistry.byName(event.getComponentId());
        CommandInformation information = ExecutorCache.get(event.getMessage().getId());

        if (clickable == null) if (information == null) {
            expire(event);
        } else {
            CommandRegistry.byName(CommandFormat.INTERACTIVE, information.getInteractionEvent().getName()).button(event, information);
        }
        else if (clickable.getClass().getAnnotation(ClickableType.class).isPrivate() && information != null) {
            clickable.execute(new ClickableInformation(event, information));
        } else {
            clickable.execute(new ClickableInformation(event));
        }
    }

    public void expire(ButtonClickEvent event) throws ExecutionException {
        Language language = GuildDAO.getGuild(event.getGuild().getId()).getLanguage();
        EmbedBuilder output = new EmbedTemplate(EmbedFormat.DEFAULT, event.getMember().getUser()).getEmbedBuilder();

        output.setTitle(language.getValue("error.expire.title"));
        output.setDescription(language.getValue("error.expire.description"));
        event.deferEdit().setEmbeds(output.build()).setActionRow(
                Button.secondary("expireHelp", language.getValue("general.what"))
                        .withEmoji(Emoji.fromMarkdown("<:flora_help:854516649206218762>")))
                .queue();
    }
}
