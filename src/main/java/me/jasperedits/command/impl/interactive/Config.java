package me.jasperedits.command.impl.interactive;

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
                    output.setTitle(language.getValue("config.language.choose.title"));
                    output.setDescription(language.getValue("config.language.choose.description")
                            .replace("%s", String.join(", ", language.listLanguages()).toUpperCase()));
                    information.getInteractionEvent().replyEmbeds(output.build()).setEphemeral(true).queue();
                    return;
                }

                String newLanguage = information.getInteractionEvent().getOption("code").getAsString().toLowerCase();

                if (!language.checkAvailability(newLanguage)) {
                    error(information, output, language.getValue("config.language.error.exists.description")
                            .replace("%s", String.join(", ", language.listLanguages()).toUpperCase()));
                    return;
                }

                // Everything's good, we ack the command to give us more time.
                information.getInteractionEvent().deferReply().queue();
                information.getGuild().setLanguage(new Language(newLanguage));
                GuildDAO.updateGuild(information.getGuild());

                // Re-get the language to output correctly.
                language = information.getGuild().getLanguage();
                output.setDescription(language.getValue("config.language.uploaded.description").replace("%s", newLanguage.toUpperCase()));
            }
            case "mchannel" -> {
                if (information.getInteractionEvent().getOption("channel").getChannelType() != ChannelType.TEXT) {
                    error(information, output, language.getValue("config.channel.error.type.description"));
                    return;
                }
                GuildChannel newChannel = information.getInteractionEvent().getOption("channel").getAsGuildChannel();

                // Everything's good, we ack the command to give us more time.
                information.getInteractionEvent().deferReply().queue();
                information.getGuild().setSeedObjectiveChannel(newChannel.getIdLong());
                GuildDAO.updateGuild(information.getGuild());
                output.setDescription(language.getValue("config.channel.uploaded.description").replace("%s", newChannel.getAsMention()));
            }
        }

        output.setTitle(language.getValue("settings.uploaded.title"));
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
