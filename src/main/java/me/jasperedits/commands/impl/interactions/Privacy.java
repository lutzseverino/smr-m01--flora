package me.jasperedits.commands.impl.interactions;

import me.jasperedits.commands.Command;
import me.jasperedits.commands.CommandFormat;
import me.jasperedits.commands.CommandInformation;
import me.jasperedits.commands.CommandType;
import me.jasperedits.embeds.EmbedTemplate;
import me.jasperedits.embeds.EmbedType;
import me.jasperedits.managers.Language;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.interactions.components.Button;

@CommandType(
        format = CommandFormat.INTERACTION,
        names = "privacy"
)
public class Privacy implements Command {

    @Override
    public void execute(CommandInformation command) {
        Language language = command.getGuild().getLanguage();
        Member member = command.getInteractionEvent().getMember();

        // Create the embed used for output.
        EmbedBuilder output = new EmbedTemplate(EmbedType.DEFAULT, member.getUser()).getEmbedBuilder();

        output.setTitle(language.getMessage("privacy.notice.title"));
        output.setDescription(language.getMessage("privacy.notice.message"));
        command.getInteractionEvent().replyEmbeds(output.build())
                .addActionRow(
                        Button.link("https://github.com/JasperEdits/Flora", language.getMessage("privacy.github.button")),
                        Button.link("https://github.com/JasperEdits/Flora/blob/master/PRIVACY.md", language.getMessage("privacy.privacy.button")))
                .queue();

    }
}
