package me.jasperedits.commands.rules.impl;

import me.jasperedits.commands.CommandFormat;
import me.jasperedits.commands.CommandInformation;
import me.jasperedits.commands.CommandType;
import me.jasperedits.commands.rules.Rule;
import me.jasperedits.commands.rules.RuleType;
import me.jasperedits.embeds.EmbedTemplate;
import me.jasperedits.embeds.EmbedFormat;
import me.jasperedits.managers.Language;
import net.dv8tion.jda.api.EmbedBuilder;

@RuleType(applyFor = {CommandFormat.LEGACY, CommandFormat.INTERACTIVE})
public class Permissions implements Rule {

    public Permissions() {
    }

    @Override
    public boolean check(CommandFormat format, CommandType type, CommandInformation information) {
        switch (format) {
            case LEGACY:
                return information.getLegacyEvent().getMember().hasPermission(type.permission());
            case INTERACTIVE:
                return information.getInteractionEvent().getMember().hasPermission(type.permission());
        }
        return false;
    }

    public void legacyOutput(CommandInformation information) {
        // Get guild's language.
        Language language = information.getGuild().getLanguage();

        // Build the output embed.
        EmbedBuilder output = new EmbedTemplate(EmbedFormat.DEFAULT, information.getLegacyEvent().getAuthor()).getEmbedBuilder();
        output.setTitle(language.getValue("error.permission.title"));
        output.setDescription(language.getValue("error.permission.message"));

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
        output.setDescription(language.getValue("error.permission.message"));

        // Send the error message.
        information.getInteractionEvent().replyEmbeds(output.build()).setEphemeral(true).queue();
    }

}
