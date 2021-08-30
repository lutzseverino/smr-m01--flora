package me.jasperedits.flora.command.impl.interactive;

import me.jasperedits.flora.command.Command;
import me.jasperedits.flora.command.ExecutionData;
import me.jasperedits.flora.command.annotation.CommandType;
import me.jasperedits.flora.command.annotation.CommandType.Format;
import me.jasperedits.flora.command.annotation.LonelyCommand;
import me.jasperedits.flora.embed.EmbedFormat;
import me.jasperedits.flora.embed.EmbedTemplate;
import me.jasperedits.flora.manager.Language;
import me.jasperedits.flora.guild.GuildDAO;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;

@CommandType(
        format = Format.INTERACTIVE,
        permission = Permission.ADMINISTRATOR,
        minArguments = 1, maxArguments = 1
)
public class Prefix extends Command {

    @LonelyCommand
    public void prefix(ExecutionData data) {
        Language language = data.getGuild().getLanguage();
        Member member = data.getInteractionEvent().getMember();
        String prefix = data.getInteractionEvent().getOption("prefix").getAsString();

        EmbedBuilder output = new EmbedTemplate(EmbedFormat.DEFAULT, member.getUser()).getEmbedBuilder();

        if (prefix.length() > 3) {
            error(data, output, language.getValue("prefix.error.long.description"));
            return;
        }

        // Everything's good, we ack the command to give us more time.
        data.getInteractionEvent().deferReply().queue();

        data.getGuild().setPrefix(prefix);
        GuildDAO.updateGuild(data.getGuild());

        output.setTitle(language.getValue("settings.uploaded.title"));
        output.setDescription(language.getValue("prefix.uploaded.description")
                .replace("%s", prefix) + language.getValue("prefix.warning.description"));
        data.getInteractionEvent().getHook().sendMessageEmbeds(output.build()).queue();
    }

    public void error(ExecutionData information, EmbedBuilder output, String errorMessage) {
        Language language = information.getGuild().getLanguage();

        output.setTitle(language.getValue("error.command.title"));
        output.setDescription(errorMessage);
        information.getInteractionEvent().replyEmbeds(output.build()).setEphemeral(true).queue();
    }
}
