package me.jasperedits.flora.command.rule.impl;

import me.jasperedits.flora.command.ExecutionData;
import me.jasperedits.flora.command.annotation.CommandType;
import me.jasperedits.flora.command.rule.Rule;
import me.jasperedits.flora.command.rule.RuleType;
import me.jasperedits.flora.embed.EmbedFormat;
import me.jasperedits.flora.embed.EmbedTemplate;
import me.jasperedits.flora.manager.Language;
import me.jasperedits.flora.util.DiscordUtil;
import net.dv8tion.jda.api.EmbedBuilder;

@RuleType(executeFor = {CommandType.Format.LEGACY, CommandType.Format.INTERACTIVE})
public class Permissions implements Rule {

    public Permissions() {
    }

    @Override
    public boolean check(CommandType.Format format, CommandType type, ExecutionData data) {
        return switch (format) {
            case LEGACY -> data.getLegacyEvent().getMember().hasPermission(type.permission());
            case INTERACTIVE -> data.getInteractionEvent().getMember().hasPermission(type.permission());
        };
    }

    public void legacyOutput(ExecutionData information) {
        Language language = information.getGuild().getLanguage();

        EmbedBuilder output = new EmbedTemplate(EmbedFormat.DEFAULT, information.getLegacyEvent().getAuthor()).getEmbedBuilder();
        output.setTitle(language.getValue("error.permission.title"));
        output.setDescription(language.getValue("error.permission.description"));

        information.getLegacyEvent().getMessage().reply(output.build()).mentionRepliedUser(false).queue();
    }

    @Override
    public void interactionOutput(ExecutionData data) {
        Language language = data.getGuild().getLanguage();

        EmbedBuilder output = new EmbedTemplate(EmbedFormat.DEFAULT, data.getInteractionEvent().getUser()).getEmbedBuilder();
        output.setTitle(language.getValue("error.permission.title"));
        output.setDescription(language.getValue("error.permission.description"));

        data.getInteractionEvent().replyEmbeds(output.build()).addActionRow(DiscordUtil.addWikiComponent(language))
                .setEphemeral(true).queue();
    }

}
