package me.jasperedits.flora.command.impl.interactive.start;

import me.jasperedits.flora.command.ChildCommand;
import me.jasperedits.flora.command.ExecutionData;
import me.jasperedits.flora.command.annotation.CommandNames;
import me.jasperedits.flora.command.annotation.Subcommand;
import me.jasperedits.flora.embed.EmbedFormat;
import me.jasperedits.flora.embed.EmbedTemplate;
import me.jasperedits.flora.guild.GuildDAO;
import me.jasperedits.flora.manager.Language;
import me.jasperedits.flora.util.DiscordUtil;
import me.jasperedits.flora.util.MathUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.components.Button;

@CommandNames("messages")
public class StartMessages extends ChildCommand {

    @Subcommand
    public void goal(ExecutionData data) {
        Language language = data.getGuild().getLanguage();
        Member member = data.getInteractionEvent().getMember();
        EmbedBuilder output = new EmbedTemplate(EmbedFormat.DEFAULT, member.getUser()).getEmbedBuilder();
        OptionMapping prizeOption = data.getInteractionEvent().getOption("prize");

        boolean prizeExists = prizeOption != null;

        output.setTitle(language.getValue("start.sent.messages.title"));
        output.setDescription(language.getValue("start.sent.messages.description")
                .replace("%d", String.valueOf(MathUtil.getGuildObjective(data.getInteractionEvent().getGuild())))
                .replace("%s", "0"));
        if (prizeExists)
            output.addField(language.getValue("start.sent.messages.prize-field"), prizeOption.getAsString(), true);

        TextChannel channel = data.getInteractionEvent().getGuild().getTextChannelById(data.getGuild().getObjectiveChannel());

        if (channel == null) {
            output.setTitle(language.getValue("start.null-channel.messages.title"));
            output.setDescription(language.getValue("start.null-channel.messages.description"));

            data.getInteractionEvent().replyEmbeds(output.build()).setEphemeral(true).addActionRow(
                    DiscordUtil.addWikiComponent(language)).queue();
            return;
        }

        channel.sendMessage(output.build()).setActionRow(Button.secondary("refresh-auto-messages-goal", language.getValue("commons.refresh"))
                .withEmoji(Emoji.fromMarkdown("<:flora_refresh:882992618462675048>"))).queue();

        output.setTitle(language.getValue("start.successful.messages.title"));
        output.setDescription(language.getValue("start.successful.messages.description"));
        data.getGuild().getOngoingObjectives().put("auto-messages-goal", true);
        GuildDAO.updateGuild(data.getGuild());

        data.getInteractionEvent().replyEmbeds(output.build()).queue();
    }
}
