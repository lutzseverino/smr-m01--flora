package me.jasperedits.commands.impl.interactive;

import me.jasperedits.commands.Command;
import me.jasperedits.commands.CommandFormat;
import me.jasperedits.commands.CommandInformation;
import me.jasperedits.commands.CommandType;
import me.jasperedits.embeds.EmbedTemplate;
import me.jasperedits.embeds.EmbedFormat;
import me.jasperedits.managers.Language;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.interactions.components.Button;

@CommandType(
        format = CommandFormat.INTERACTIVE,
        names = "privacy"
)
public class Privacy implements Command {

    @Override
    public void execute(CommandInformation information) {
        Language language = information.getGuild().getLanguage();
        Member member = information.getInteractionEvent().getMember();

        // Create the embed used for output.
        EmbedBuilder output = new EmbedTemplate(EmbedFormat.DEFAULT, member.getUser()).getEmbedBuilder();

        output.setTitle(language.getValue("privacy.notice.title"));
        output.setDescription(language.getValue("privacy.notice.message"));
        information.getInteractionEvent().replyEmbeds(output.build())
                .addActionRow(
                        Button.link("https://github.com/JasperEdits/Flora", language.getValue("privacy.github.button")),
                        Button.link("https://github.com/JasperEdits/Flora/blob/master/PRIVACY.md", language.getValue("privacy.privacy.button")))
                .queue();

    }

    @Override
    public void error(CommandInformation information, EmbedBuilder output, String errorMessage) {
    }

    @Override
    public void button(ButtonClickEvent event, CommandInformation information) {

    }
}
