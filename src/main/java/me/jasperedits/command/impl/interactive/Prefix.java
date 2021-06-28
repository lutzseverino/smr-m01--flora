package me.jasperedits.command.impl.interactive;

import lombok.SneakyThrows;
import me.jasperedits.command.Command;
import me.jasperedits.command.CommandFormat;
import me.jasperedits.command.CommandInformation;
import me.jasperedits.command.CommandType;
import me.jasperedits.guild.GuildDAO;
import me.jasperedits.embed.EmbedTemplate;
import me.jasperedits.embed.EmbedFormat;
import me.jasperedits.manager.Language;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;

@CommandType(
        format = CommandFormat.INTERACTIVE,
        names = "prefix",
        permission = Permission.ADMINISTRATOR,
        minArguments = 1, maxArguments = 1
)
public class Prefix implements Command {

    public Prefix() {
    }

    @SneakyThrows
    @Override
    public void execute(CommandInformation information) {
        Language language = information.getGuild().getLanguage();
        Member member = information.getInteractionEvent().getMember();
        String prefix = information.getInteractionEvent().getOption("prefix").getAsString();

        EmbedBuilder output = new EmbedTemplate(EmbedFormat.DEFAULT, member.getUser()).getEmbedBuilder();

        if (prefix.length() > 3) {
            error(information, output, language.getValue("prefix.error.long.description"));
            return;
        }

        // Everything's good, we ack the command to give us more time.
        information.getInteractionEvent().deferReply().queue();

        information.getGuild().setPrefix(prefix);
        GuildDAO.updateGuild(information.getGuild());

        output.setTitle(language.getValue("settings.uploaded.title"));
        output.setDescription(language.getValue("prefix.uploaded.description")
                .replace("%s", prefix) + language.getValue("prefix.warning.description"));
        information.getInteractionEvent().getHook().sendMessageEmbeds(output.build()).queue();
    }

    @Override
    public void error(CommandInformation information, EmbedBuilder output, String errorMessage) {
        Language language = information.getGuild().getLanguage();

        output.setTitle(language.getValue("error.command.title"));
        output.setDescription(errorMessage);
        information.getInteractionEvent().replyEmbeds(output.build()).setEphemeral(true).queue();
    }

    @Override
    public void button(ButtonClickEvent event, CommandInformation information) {

    }
}
