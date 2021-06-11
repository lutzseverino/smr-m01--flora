package me.jasperedits.commands.impl.legacy;

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
        names = {"prefix", "setPrefix"},
        permission = Permission.ADMINISTRATOR,
        minArguments = 1, maxArguments = 1
)
public class Prefix implements Command {

    public Prefix() {
    }

    @SneakyThrows
    @Override
    public void execute(CommandInformation command) {
        Language language = command.getGuild().getLanguage();
        Message message = command.getLegacyEvent().getMessage();
        Member member = command.getLegacyEvent().getMember();
        String prefix = command.getArgs().get(0);

        EmbedBuilder output = new EmbedTemplate(EmbedType.DEFAULT, member.getUser()).getEmbedBuilder();

        if (prefix.length() > 3) {
            output.setTitle(language.getMessage("error.title"));
            output.setDescription(language.getMessage("prefix.error.long.message"));
            message.reply(output.build()).mentionRepliedUser(false).queue();
            return;
        }

        EmbedBuilder loading = new EmbedTemplate(EmbedType.PROCESS, member.getUser()).getEmbedBuilder();
        loading.setDescription(language.getMessage("settings.uploading.message"));
        Message embedToUpdate = message.reply(loading.build()).mentionRepliedUser(false).complete();

        command.getGuild().setPrefix(prefix);
        GuildDAO.updateGuild(command.getGuild());

        output.setTitle(language.getMessage("settings.uploaded.title"));
        output.setDescription(language.getMessage("prefix.uploaded.message")
                .replace("%s", prefix) + language.getMessage("prefix.warning.message"));
        embedToUpdate.editMessage(output.build()).queue();
    }
}
