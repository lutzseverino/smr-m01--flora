package me.jasperedits.flora.command.impl.interactive;

import me.jasperedits.flora.command.Command;
import me.jasperedits.flora.command.ExecutionData;
import me.jasperedits.flora.command.annotation.CommandType;
import me.jasperedits.flora.command.annotation.Subcommand;
import me.jasperedits.flora.embed.EmbedFormat;
import me.jasperedits.flora.embed.EmbedTemplate;
import me.jasperedits.flora.manager.Language;
import me.jasperedits.flora.guild.GuildDAO;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.Member;

@CommandType(
        format = CommandType.Format.INTERACTIVE,
        permission = Permission.ADMINISTRATOR
)
public class Config extends Command {

    @Subcommand
    public void language(ExecutionData data) {
        Language language = data.getGuild().getLanguage();
        Member member  = data.getInteractionEvent().getMember();
        EmbedBuilder output = new EmbedTemplate(EmbedFormat.DEFAULT, member.getUser()).getEmbedBuilder();

        if (data.getInteractionEvent().getOption("code") == null) {
            output.setTitle(language.getValue("config.language.choose.title"));
            output.setDescription(language.getValue("config.language.choose.description")
                    .replace("%s", String.join(", ", language.listLanguages()).toUpperCase()));
            data.getInteractionEvent().replyEmbeds(output.build()).setEphemeral(true).queue();
            return;
        }

        String newLanguage = data.getInteractionEvent().getOption("code").getAsString().toLowerCase();

        if (!language.checkAvailability(newLanguage)) {
            error(data, output, language.getValue("config.language.error.exists.description")
                    .replace("%s", String.join(", ", language.listLanguages()).toUpperCase()));
            return;
        }

        data.getInteractionEvent().deferReply().queue();
        data.getGuild().setLanguage(new Language(newLanguage));
        GuildDAO.updateGuild(data.getGuild());

        language = data.getGuild().getLanguage();
        output.setDescription(language.getValue("config.language.uploaded.description").replace("%s", newLanguage.toUpperCase()));

        output.setTitle(language.getValue("settings.uploaded.title"));
        data.getInteractionEvent().getHook().sendMessageEmbeds(output.build()).queue();
    }

    @Subcommand
    public void messageChannel(ExecutionData data) {
        Language language = data.getGuild().getLanguage();
        Member member  = data.getInteractionEvent().getMember();
        EmbedBuilder output = new EmbedTemplate(EmbedFormat.DEFAULT, member.getUser()).getEmbedBuilder();

        if (data.getInteractionEvent().getOption("channel").getChannelType() != ChannelType.TEXT) {
            error(data, output, language.getValue("config.channel.error.type.description"));
            return;
        }

        GuildChannel newChannel = data.getInteractionEvent().getOption("channel").getAsGuildChannel();

        data.getInteractionEvent().deferReply().queue();
        data.getGuild().setSeedObjectiveChannel(newChannel.getIdLong());
        GuildDAO.updateGuild(data.getGuild());

        output.setDescription(language.getValue("config.channel.uploaded.description").replace("%s", newChannel.getAsMention()));

        output.setTitle(language.getValue("settings.uploaded.title"));
        data.getInteractionEvent().getHook().sendMessageEmbeds(output.build()).queue();
    }


    public void error(ExecutionData information, EmbedBuilder output, String errorMessage) {
        Language language = information.getGuild().getLanguage();

        output.setTitle(language.getValue("error.command.title"));
        output.setDescription(errorMessage);
        information.getInteractionEvent().replyEmbeds(output.build()).setEphemeral(true).queue();
    }
}









