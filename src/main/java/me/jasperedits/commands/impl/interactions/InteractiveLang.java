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
        names = {"%ilanguage"},
        permission = Permission.ADMINISTRATOR
)
public class InteractiveLang implements Command {

    public InteractiveLang() {
    }

    @SneakyThrows
    @Override
    public void execute(CommandInformation command) {
        Language language = command.getGuild().getLanguage();
        Member member = command.getInteractionEvent().getMember();
        String newLanguage = command.getInteractionEvent().getOption("language").getAsString().toLowerCase();

        EmbedBuilder output = new EmbedTemplate(EmbedType.DEFAULT, member.getUser()).getEmbedBuilder();

        if (!language.checkAvailability(newLanguage)) {
            String COMMAND_ERROR_TITLE = language.getMessage("error.title");
            String EXISTS_MESSAGE = language.getMessage("lang.error.exists.message");

            output.setTitle(COMMAND_ERROR_TITLE);
            output.setDescription(EXISTS_MESSAGE
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

        String UPLOADED_TITLE = language.getMessage("settings.uploaded.title");
        String UPLOADED_MESSAGE = language.getMessage("settings.uploaded.message");

        output.setTitle(UPLOADED_TITLE);
        output.setDescription(UPLOADED_MESSAGE.replace("%s", "`" + newLanguage.toUpperCase() + "`"));
        command.getInteractionEvent().getHook().sendMessageEmbeds(output.build()).queue();
    }
}
