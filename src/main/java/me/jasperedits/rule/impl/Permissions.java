package me.jasperedits.rule.impl;

import me.jasperedits.command.CommandFormat;
import me.jasperedits.command.CommandInformation;
import me.jasperedits.command.CommandType;
import me.jasperedits.embed.EmbedFormat;
import me.jasperedits.embed.EmbedTemplate;
import me.jasperedits.manager.Language;
import me.jasperedits.rule.Rule;
import me.jasperedits.rule.RuleType;
import me.jasperedits.util.DiscordUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.interactions.components.Button;

@RuleType(applyFor = {CommandFormat.LEGACY, CommandFormat.INTERACTIVE})
public class Permissions implements Rule {

    public Permissions() {
    }

    @Override
    public boolean check(CommandFormat format, CommandType type, CommandInformation information) {
        return switch (format) {
            case LEGACY -> information.getLegacyEvent().getMember().hasPermission(type.permission());
            case INTERACTIVE -> information.getInteractionEvent().getMember().hasPermission(type.permission());
        };
    }

    public void legacyOutput(CommandInformation information) {
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
    public void interactionOutput(CommandInformation information) {
        // Get guild's language.
        Language language = information.getGuild().getLanguage();

        // Build the output embed.
        EmbedBuilder output = new EmbedTemplate(EmbedFormat.DEFAULT, information.getInteractionEvent().getUser()).getEmbedBuilder();
        output.setTitle(language.getValue("error.permission.title"));
        output.setDescription(language.getValue("error.permission.description"));

        // Send the error message.
        information.getInteractionEvent().replyEmbeds(output.build()).addActionRow(DiscordUtils.addWikiComponent(language))
                .setEphemeral(true).queue();
    }

}
