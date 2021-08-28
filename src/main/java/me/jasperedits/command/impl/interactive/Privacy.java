package me.jasperedits.command.impl.interactive;

import me.jasperedits.command.Command;
import me.jasperedits.command.settings.CommandFormat;
import me.jasperedits.command.CommandData;
import me.jasperedits.command.annotation.CommandType;
import me.jasperedits.embed.EmbedTemplate;
import me.jasperedits.embed.EmbedFormat;
import me.jasperedits.manager.Language;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.interactions.components.Button;

@CommandType(
        format = CommandFormat.INTERACTIVE,
        names = "privacy"
)
public class Privacy implements Command {

    @Override
    public void execute(CommandData information) {
        Language language = information.getGuild().getLanguage();
        Member member = information.getInteractionEvent().getMember();

        // Create the embed used for output.
        EmbedBuilder output = new EmbedTemplate(EmbedFormat.DEFAULT, member.getUser()).getEmbedBuilder();

        output.setTitle(language.getValue("privacy.notice.title"));
        output.setDescription(language.getValue("privacy.notice.description"));
        information.getInteractionEvent().replyEmbeds(output.build())
                .addActionRow(
                        Button.link("https://github.com/JasperEdits/Flora", language.getValue("privacy.github.button"))
                                .withEmoji(Emoji.fromMarkdown("<:flora_source:854513431411294258>")),
                        Button.link("https://github.com/JasperEdits/Flora/blob/master/PRIVACY.md", language.getValue("privacy.privacy.button"))
                                .withEmoji(Emoji.fromMarkdown("<:flora_policy:854514040395399179>")))
                .queue();

    }

    public void error(CommandData information, EmbedBuilder output, String errorMessage) {
    }

    @Override
    public void button(ButtonClickEvent event, CommandData information) {

    }
}
