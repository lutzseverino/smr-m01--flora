package me.jasperedits.command.impl.interactive;

import me.jasperedits.command.Command;
import me.jasperedits.command.CommandFormat;
import me.jasperedits.command.CommandInformation;
import me.jasperedits.command.CommandType;
import me.jasperedits.embed.EmbedFormat;
import me.jasperedits.embed.EmbedTemplate;
import me.jasperedits.manager.Language;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;

@CommandType(
        format = CommandFormat.INTERACTIVE,
        names = "ping"
)
public class Ping implements Command {
    @Override
    public void execute(CommandInformation information) {
        Language language = information.getGuild().getLanguage();
        Member member = information.getInteractionEvent().getMember();

        // Create the embed used for output.
        EmbedBuilder output = new EmbedTemplate(EmbedFormat.DEFAULT, member.getUser()).getEmbedBuilder();

        output.setTitle(language.getValue("ping.pong.title"));
        output.setDescription(language.getValue("ping.pinging.description"));

        long time = System.currentTimeMillis();

        information.getInteractionEvent().deferReply().queue(response -> {
            output.setDescription(language.getValue("ping.pong.description")
                    .replace("%s", String.valueOf(System.currentTimeMillis() - time)));
            response.editOriginalEmbeds(output.build()).queue();
        });
    }

    @Override
    public void error(CommandInformation information, EmbedBuilder output, String errorMessage) {

    }

    @Override
    public void button(ButtonClickEvent event, CommandInformation information) {

    }
}
