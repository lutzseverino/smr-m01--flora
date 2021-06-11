package me.jasperedits.commands.impl;

import lombok.SneakyThrows;
import me.jasperedits.commands.Command;
import me.jasperedits.commands.CommandFormat;
import me.jasperedits.commands.CommandInformation;
import me.jasperedits.commands.CommandType;
import me.jasperedits.daos.GuildDAO;
import me.jasperedits.embeds.EmbedType;
import me.jasperedits.embeds.EmbedTemplate;
import me.jasperedits.managers.Language;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

@CommandType(
        format = CommandFormat.LEGACY,
        names = {"%lprefix", "%lsetPrefix"},
        permission = Permission.ADMINISTRATOR,
        minArguments = 1, maxArguments = 1
)
public class Prefix implements Command {

    public Prefix() {
    }

    @SneakyThrows
    @Override
    public void execute(CommandInformation command) {

        command.getLegacyEvent().getGuild().updateCommands().addCommands(
                new CommandData("prefix", "Changes the legacy prefix on this guild.")
                .addOption(OptionType.STRING, "prefix", "your new prefix... (max. of 3 characters long)", true),
                new CommandData("language", "Changes the language on this guild.")
                .addOption(OptionType.STRING, "language", "the new language..."))
                .queue();

        Language language = command.getGuild().getLanguage();
        Message message = command.getLegacyEvent().getMessage();
        Member member = command.getLegacyEvent().getMember();
        String prefix = command.getArgs().get(0);

        String UPLOADING_MESSAGE = language.getMessage("settings.uploading.message");

        EmbedBuilder output = new EmbedTemplate(EmbedType.DEFAULT, member.getUser()).getEmbedBuilder();

        if (prefix.length() > 3) {
            String COMMAND_ERROR_TITLE = language.getMessage("error.title");
            String TOO_LONG_MESSAGE = language.getMessage("prefix.error.long.message");

            output.setTitle(COMMAND_ERROR_TITLE);
            output.setDescription(TOO_LONG_MESSAGE);
            message.reply(output.build()).mentionRepliedUser(false).queue();
            return;
        }

        EmbedBuilder loading = new EmbedTemplate(EmbedType.PROCESS, member.getUser()).getEmbedBuilder();
        loading.setDescription(UPLOADING_MESSAGE);
        Message embedToUpdate = message.reply(loading.build()).mentionRepliedUser(false).complete();

        command.getGuild().setPrefix(prefix);
        GuildDAO.updateGuild(command.getGuild());

        String UPLOADED_TITLE = language.getMessage("settings.uploaded.title");
        String UPLOADED_MESSAGE = language.getMessage("settings.uploaded.message");
        String LEGACY_WARNING = language.getMessage("prefix.warning.message");

        output.setTitle(UPLOADED_TITLE);
        output.setDescription(UPLOADED_MESSAGE.replace("%s", "`" + prefix + "`" +  LEGACY_WARNING));
        embedToUpdate.editMessage(output.build()).queue();
    }
}
