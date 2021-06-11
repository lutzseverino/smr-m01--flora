package me.jasperedits.commands.impl.interactions;

import lombok.SneakyThrows;
import me.jasperedits.commands.Command;
import me.jasperedits.commands.CommandFormat;
import me.jasperedits.commands.CommandInformation;
import me.jasperedits.commands.CommandType;
import me.jasperedits.daos.GuildDAO;
import me.jasperedits.embeds.EmbedTemplate;
import me.jasperedits.embeds.EmbedType;
import me.jasperedits.managers.Language;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;

import java.util.stream.Collectors;

@CommandType(
        format = CommandFormat.INTERACTION,
        names = {"language"},
        permission = Permission.ADMINISTRATOR,
        minArguments = 1, maxArguments = 1
)
public class Lang implements Command {

    public Lang() {
    }

    @SneakyThrows
    @Override
    public void execute(CommandInformation command) {
        Language language = command.getGuild().getLanguage();
        Member member = command.getInteractionEvent().getMember();
        String newLanguage = command.getInteractionEvent().getOption("language").getAsString().toLowerCase();

        // Create the embed used for output.
        EmbedBuilder output = new EmbedTemplate(EmbedType.DEFAULT, member.getUser()).getEmbedBuilder();

        // Check if the language is available.
        if (!language.checkAvailability(newLanguage)) {
            output.setTitle(language.getMessage("error.title"));
            output.setDescription(language.getMessage("lang.error.exists.message")
                    .replace("%s", language.listLanguages().stream().collect(Collectors.joining(", "))
                    .replaceAll(".yaml", "").toUpperCase()));
            command.getInteractionEvent().replyEmbeds(output.build()).setEphemeral(true).queue();
            return;
        }

        // Everything's good, we ack the command to give us more time.
        command.getInteractionEvent().deferReply().queue();

        command.getGuild().setLanguage(new Language(newLanguage));
        GuildDAO.updateGuild(command.getGuild());

        // Re-get the language to output correctly.
        language = command.getGuild().getLanguage();

        output.setTitle(language.getMessage("settings.uploaded.title"));
        output.setDescription(language.getMessage("lang.uploaded.message")
                .replace("%s", "`" + newLanguage.toUpperCase() + "`"));
        command.getInteractionEvent().getHook().sendMessageEmbeds(output.build()).queue();
    }
}
