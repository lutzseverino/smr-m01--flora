package me.jasperedits.commands.impl.interactive;

import me.jasperedits.commands.Command;
import me.jasperedits.commands.CommandFormat;
import me.jasperedits.commands.CommandInformation;
import me.jasperedits.commands.CommandType;
import me.jasperedits.buttons.ExecutorCache;
import me.jasperedits.embeds.EmbedFormat;
import me.jasperedits.embeds.EmbedTemplate;
import me.jasperedits.managers.Language;
import me.jasperedits.managers.Paginator;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.interactions.components.Button;

@CommandType(
        format = CommandFormat.INTERACTIVE,
        names = "setup",
        permission = Permission.ADMINISTRATOR
)
public class Setup implements Command {

    Paginator paginator = new Paginator();

    @Override
    public void execute(CommandInformation information) {
        Language language = information.getGuild().getLanguage();
        Member member = information.getInteractionEvent().getMember();

        // Create the embed used for output.
        EmbedBuilder output = new EmbedTemplate(EmbedFormat.DEFAULT, member.getUser()).getEmbedBuilder();

        // Adds all the pages.
        for (int page = 0; language.getValue("setup.page." + page + ".title") != null; page++) {
            output.setTitle(language.getValue("setup.page." + page + ".title"));
            output.setDescription(language.getValue("setup.page." + page + ".message"));
            output.setFooter(language.getValue("setup.page")
                    .replace("%s", String.valueOf(paginator.getPageCount())));
            paginator.addPage(output.build());
        }

        output.setTitle(language.getValue("setup.page.0.title"));
        output.setDescription(language.getValue("setup.page.0.message"));
        output.setFooter(language.getValue("setup.page")
                .replace("%s", "0"));

        Message message = information.getInteractionEvent().replyEmbeds(output.build())
                .addActionRows(paginator.getButtons()).complete().retrieveOriginal().complete();

        ExecutorCache.set(message.getId(), information);
    }

    @Override
    public void error(CommandInformation information, EmbedBuilder output, String errorMessage) {
    }

    @Override
    public void button(ButtonClickEvent event, CommandInformation information) {
        if (information.getInteractionEvent().getMember().getId().equals(event.getMember().getId())) {
            paginator.onButtonClick(event.getInteraction());
        } else {
            event.deferEdit().queue();
        }
    }
}
