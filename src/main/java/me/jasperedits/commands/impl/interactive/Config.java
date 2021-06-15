package me.jasperedits.commands.impl.interactive;

import me.jasperedits.commands.Command;
import me.jasperedits.commands.CommandFormat;
import me.jasperedits.commands.CommandInformation;
import me.jasperedits.commands.CommandType;
import me.jasperedits.daos.GuildDAO;
import me.jasperedits.embeds.EmbedTemplate;
import me.jasperedits.embeds.EmbedFormat;
import me.jasperedits.managers.Language;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;

@CommandType(
        format = CommandFormat.INTERACTIVE,
        names = "config",
        permission = Permission.ADMINISTRATOR
)
public class Config implements Command {

    @Override
    public void execute(CommandInformation information) {
        Language language = information.getGuild().getLanguage();
        Member member = information.getInteractionEvent().getMember();
        String subcommand = information.getInteractionEvent().getSubcommandName();

        // Create the embed used for output.
        EmbedBuilder output = new EmbedTemplate(EmbedFormat.DEFAULT, member.getUser()).getEmbedBuilder();

        switch (subcommand) {
            case "language" -> {
                if (information.getInteractionEvent().getOption("code") == null) {
                    output.setTitle(language.getValue("config.lang.choose.title"));
                    output.setDescription(language.getValue("config.lang.choose.message")
                            .replace("%s", String.join(", ", language.listLanguages()).toUpperCase()));
                    information.getInteractionEvent().replyEmbeds(output.build()).setEphemeral(true).queue();
                    return;
                }

                String newLanguage = information.getInteractionEvent().getOption("code").getAsString().toLowerCase();

                if (!language.checkAvailability(newLanguage)) {
                    error(information, output, language.getValue("config.lang.error.exists.message")
                            .replace("%s", String.join(", ", language.listLanguages()).toUpperCase()));
                    return;
                }

                // Everything's good, we ack the command to give us more time.
                information.getInteractionEvent().deferReply().queue();
                information.getGuild().setLanguage(new Language(newLanguage));
                GuildDAO.updateGuild(information.getGuild());

                // Re-get the language to output correctly.
                language = information.getGuild().getLanguage();
                output.setDescription(language.getValue("config.lang.uploaded.message").replace("%s", newLanguage.toUpperCase()));
            }
            case "channel" -> {
                if (information.getInteractionEvent().getOption("channel").getChannelType() != ChannelType.TEXT) {
                    error(information, output, language.getValue("config.channel.error.type.message"));
                    return;
                }
                GuildChannel newChannel = information.getInteractionEvent().getOption("channel").getAsGuildChannel();

                // Everything's good, we ack the command to give us more time.
                information.getInteractionEvent().deferReply().queue();
                information.getGuild().setSeedChannel(newChannel.getId());
                GuildDAO.updateGuild(information.getGuild());
                output.setDescription(language.getValue("config.channel.uploaded.message").replace("%s", newChannel.getAsMention()));
            }
        }

        output.setTitle(language.getValue("settings.uploaded.title"));
        information.getInteractionEvent().getHook().sendMessageEmbeds(output.build()).queue();
    }

    @Override
    public void error(CommandInformation information, EmbedBuilder output, String errorMessage) {
        Language language = information.getGuild().getLanguage();

        output.setTitle(language.getValue("error.title"));
        output.setDescription(errorMessage);
        information.getInteractionEvent().replyEmbeds(output.build()).setEphemeral(true).queue();
    }

    @Override
    public void button(ButtonClickEvent event, CommandInformation information) {

    }
}
