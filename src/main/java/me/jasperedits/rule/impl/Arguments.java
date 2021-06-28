package me.jasperedits.rule.impl;

import me.jasperedits.command.CommandFormat;
import me.jasperedits.command.CommandInformation;
import me.jasperedits.command.CommandType;
import me.jasperedits.rule.Rule;
import me.jasperedits.rule.RuleType;
import me.jasperedits.embed.EmbedTemplate;
import me.jasperedits.embed.EmbedFormat;
import me.jasperedits.manager.Language;
import net.dv8tion.jda.api.EmbedBuilder;

@RuleType(applyFor = CommandFormat.LEGACY)
public class Arguments implements Rule {
    @Override
    public boolean check(CommandFormat format, CommandType type, CommandInformation information) {
        int argumentSize = information.getArgs().size();

        return argumentSize >= type.minArguments() && argumentSize <= type.maxArguments();
    }

    @Override
    public void legacyOutput(CommandInformation information) {
        // Get guild's language.
        Language language = information.getGuild().getLanguage();

        // Build the output embed.
        EmbedBuilder output = new EmbedTemplate(EmbedFormat.DEFAULT, information.getLegacyEvent().getAuthor()).getEmbedBuilder();
        output.setTitle(language.getValue("error.arguments.title"));
        output.setDescription(language.getValue("error.arguments.description"));

        // Send the error message.
        information.getLegacyEvent().getMessage().reply(output.build()).mentionRepliedUser(false).queue();
    }

    @Override
    public void interactionOutput(CommandInformation information) {}
}
