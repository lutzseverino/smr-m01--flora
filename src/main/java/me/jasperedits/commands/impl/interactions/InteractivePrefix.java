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

@CommandType(
        format = CommandFormat.INTERACTION,
        names = {"%iprefix"},
        permission = Permission.ADMINISTRATOR
)
public class InteractivePrefix implements Command {

    public InteractivePrefix() {
    }

    @SneakyThrows
    @Override
    public void execute(CommandInformation command) {

        Language language = command.getGuild().getLanguage();
        Member member = command.getInteractionEvent().getMember();
        String prefix = command.getInteractionEvent().getOption("prefix").getAsString();


        EmbedBuilder output = new EmbedTemplate(EmbedType.DEFAULT, member.getUser()).getEmbedBuilder();

        if (prefix.length() > 3) {
            String COMMAND_ERROR_TITLE = language.getMessage("error.title");
            String TOO_LONG_MESSAGE = language.getMessage("prefix.error.long.message");

            output.setTitle(COMMAND_ERROR_TITLE);
            output.setDescription(TOO_LONG_MESSAGE);
            command.getInteractionEvent().replyEmbeds(output.build()).setEphemeral(true).queue();
            return;
        }

        // Everything's good, we ack the command to give us more time.
        command.getInteractionEvent().deferReply().queue();

        command.getGuild().setPrefix(prefix);
        GuildDAO.updateGuild(command.getGuild());

        String UPLOADED_TITLE = language.getMessage("settings.uploaded.title");
        String UPLOADED_MESSAGE = language.getMessage("settings.uploaded.message");
        String LEGACY_WARNING = language.getMessage("prefix.warning.message");

        output.setTitle(UPLOADED_TITLE);
        output.setDescription(UPLOADED_MESSAGE.replace("%s", "`" + prefix + "`" +  LEGACY_WARNING));
        command.getInteractionEvent().getHook().sendMessageEmbeds(output.build()).queue();
    }
}
