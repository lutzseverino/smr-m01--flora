package me.jasperedits.flora.command.rule.impl;

import me.jasperedits.flora.command.ExecutionData;
import me.jasperedits.flora.command.annotation.CommandType;
import me.jasperedits.flora.command.rule.Rule;
import me.jasperedits.flora.command.rule.RuleType;
import me.jasperedits.flora.embed.EmbedTemplate;
import me.jasperedits.flora.embed.EmbedFormat;
import me.jasperedits.flora.manager.Language;
import net.dv8tion.jda.api.EmbedBuilder;

@RuleType(executeFor = CommandType.Format.LEGACY)
public class Arguments implements Rule {
    @Override
    public boolean check(CommandType.Format format, CommandType type, ExecutionData data) {
        int argumentSize = data.getArgs().size();

        return argumentSize >= type.minArguments() && argumentSize <= type.maxArguments();
    }

    @Override
    public void legacyOutput(ExecutionData information) {
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
    public void interactionOutput(ExecutionData information) {}
}
