package me.jasperedits.command.rule.impl;

import me.jasperedits.command.settings.CommandFormat;
import me.jasperedits.command.CommandData;
import me.jasperedits.command.annotation.CommandType;
import me.jasperedits.embed.EmbedFormat;
import me.jasperedits.embed.EmbedTemplate;
import me.jasperedits.manager.Language;
import me.jasperedits.command.rule.Rule;
import me.jasperedits.command.rule.RuleType;
import me.jasperedits.util.DiscordUtil;
import net.dv8tion.jda.api.EmbedBuilder;

@RuleType(applyFor = {CommandFormat.LEGACY, CommandFormat.INTERACTIVE})
public class Permissions implements Rule {

    public Permissions() {
    }

    @Override
    public boolean check(CommandFormat format, CommandType type, CommandData information) {
        return switch (format) {
            case LEGACY -> information.getLegacyEvent().getMember().hasPermission(type.permission());
            case INTERACTIVE -> information.getInteractionEvent().getMember().hasPermission(type.permission());
        };
    }

    public void legacyOutput(CommandData information) {
        // Get guild's language.
        Language language = information.getGuild().getLanguage();

        // Build the output embed.
        EmbedBuilder output = new EmbedTemplate(EmbedFormat.DEFAULT, information.getLegacyEvent().getAuthor()).getEmbedBuilder();
        output.setTitle(language.getValue("error.permission.title"));
        output.setDescription(language.getValue("error.permission.description"));

        // Send the error message.
        information.getLegacyEvent().getMessage().reply(output.build()).mentionRepliedUser(false).queue();
    }

    @Override
    public void interactionOutput(CommandData information) {
        // Get guild's language.
        Language language = information.getGuild().getLanguage();

        // Build the output embed.
        EmbedBuilder output = new EmbedTemplate(EmbedFormat.DEFAULT, information.getInteractionEvent().getUser()).getEmbedBuilder();
        output.setTitle(language.getValue("error.permission.title"));
        output.setDescription(language.getValue("error.permission.description"));

        // Send the error message.
        information.getInteractionEvent().replyEmbeds(output.build()).addActionRow(DiscordUtil.addWikiComponent(language))
                .setEphemeral(true).queue();
    }

}
