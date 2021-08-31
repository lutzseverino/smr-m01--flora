package me.jasperedits.flora.command.impl.interactive;

import me.jasperedits.flora.command.Command;
import me.jasperedits.flora.command.ExecutionData;
import me.jasperedits.flora.command.annotation.CommandType;
import me.jasperedits.flora.command.annotation.CommandType.Format;
import me.jasperedits.flora.command.annotation.LonelyCommand;
import me.jasperedits.flora.embed.EmbedFormat;
import me.jasperedits.flora.embed.EmbedTemplate;
import me.jasperedits.flora.manager.Language;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;

@CommandType(
        format = Format.INTERACTIVE
)
public class Ping extends Command {

    @LonelyCommand
    public void ping(ExecutionData data) {
        Language language = data.getGuild().getLanguage();
        Member member = data.getInteractionEvent().getMember();

        EmbedBuilder output = new EmbedTemplate(EmbedFormat.DEFAULT, member.getUser()).getEmbedBuilder();

        output.setTitle(language.getValue("ping.pong.title"));
        output.setDescription(language.getValue("ping.pinging.description"));

        long time = System.currentTimeMillis();

        data.getInteractionEvent().deferReply().queue(response -> {
            output.setDescription(language.getValue("ping.pong.description")
                    .replace("%s", String.valueOf(System.currentTimeMillis() - time)));
            response.editOriginalEmbeds(output.build()).queue();
        });
    }
}
