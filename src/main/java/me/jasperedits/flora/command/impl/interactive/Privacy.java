package me.jasperedits.flora.command.impl.interactive;

import me.jasperedits.flora.command.Command;
import me.jasperedits.flora.command.annotation.CommandType.Format;
import me.jasperedits.flora.command.annotation.LonelyCommand;
import me.jasperedits.flora.command.ExecutionData;
import me.jasperedits.flora.command.annotation.CommandType;
import me.jasperedits.flora.embed.EmbedTemplate;
import me.jasperedits.flora.embed.EmbedFormat;
import me.jasperedits.flora.manager.Language;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.interactions.components.Button;

@CommandType(
        format = Format.INTERACTIVE
)
public class Privacy extends Command {

    @LonelyCommand
    public void privacy(ExecutionData data) {
        Language language = data.getGuild().getLanguage();
        Member member = data.getInteractionEvent().getMember();

        // Create the embed used for output.
        EmbedBuilder output = new EmbedTemplate(EmbedFormat.DEFAULT, member.getUser()).getEmbedBuilder();

        output.setTitle(language.getValue("privacy.notice.title"));
        output.setDescription(language.getValue("privacy.notice.description"));
        data.getInteractionEvent().replyEmbeds(output.build())
                .addActionRow(
                        Button.link("https://github.com/JasperEdits/Flora", language.getValue("privacy.github.button"))
                                .withEmoji(Emoji.fromMarkdown("<:flora_source:854513431411294258>")),
                        Button.link("https://github.com/JasperEdits/Flora/blob/master/PRIVACY.md", language.getValue("privacy.privacy.button"))
                                .withEmoji(Emoji.fromMarkdown("<:flora_policy:854514040395399179>")))
                .queue();

    }
}
