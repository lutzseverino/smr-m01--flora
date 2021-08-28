package me.jasperedits.command.rule.impl;

import me.jasperedits.FloraBot;
import me.jasperedits.command.CommandData;
import me.jasperedits.command.annotation.CommandType;
import me.jasperedits.command.rule.Rule;
import me.jasperedits.command.rule.RuleType;
import me.jasperedits.command.settings.CommandAllowance;
import me.jasperedits.command.settings.CommandFormat;
import me.jasperedits.embed.EmbedFormat;
import me.jasperedits.embed.EmbedTemplate;
import me.jasperedits.manager.Language;
import me.jasperedits.util.DiscordUtil;
import net.dv8tion.jda.api.EmbedBuilder;

@RuleType()
public class Allowance implements Rule {
    @Override
    public boolean check(CommandFormat format, CommandType type, CommandData information) {
        if (type.allowance().equals(CommandAllowance.EVERYTHING)) return true;

        switch (format) {
            case LEGACY -> {
                switch (type.allowance()) {
                    case CHAT_DM -> {
                        return !information.getLegacyEvent().isFromGuild();
                    }
                    case CHAT_GUILD -> {
                        return information.getLegacyEvent().isFromGuild();
                    }
                    case SERVER_OWNER -> {
                        return information.getLegacyEvent().getGuild().getOwner() != null
                                && information.getLegacyEvent().getMember().equals(information.getLegacyEvent().getGuild().getOwner());
                    }
                    case BOT_OWNER -> {
                        return information.getLegacyEvent().getMember().getIdLong() == FloraBot.getInstance().getBotValues().getOwner();
                    }
                }
            }

            case INTERACTIVE -> {
                switch (type.allowance()) {
                    case CHAT_DM -> {
                        return !information.getInteractionEvent().isFromGuild();
                    }
                    case CHAT_GUILD -> {
                        return information.getInteractionEvent().isFromGuild();
                    }
                    case SERVER_OWNER -> {
                        return information.getInteractionEvent().getGuild().getOwner() != null
                                && information.getInteractionEvent().getMember().equals(information.getInteractionEvent().getGuild().getOwner());
                    }
                    case BOT_OWNER -> {
                        return information.getInteractionEvent().getMember().getIdLong() == FloraBot.getInstance().getBotValues().getOwner();
                    }
                }
            }
        }
        return true;
    }

    @Override
    public void legacyOutput(CommandData information) {
        // Get guild's language.
        Language language = information.getGuild().getLanguage();

        // Build the output embed.
        EmbedBuilder output = new EmbedTemplate(EmbedFormat.DEFAULT, information.getLegacyEvent().getAuthor()).getEmbedBuilder();
        output.setTitle(language.getValue("error.allowance.title"));
        output.setDescription(language.getValue("error.allowance.description"));

        // Send the error message.
        information.getLegacyEvent().getMessage().reply(output.build()).mentionRepliedUser(false).queue();
    }

    @Override
    public void interactionOutput(CommandData information) {
        // Get guild's language.
        Language language = information.getGuild().getLanguage();

        // Build the output embed.
        EmbedBuilder output = new EmbedTemplate(EmbedFormat.DEFAULT, information.getInteractionEvent().getUser()).getEmbedBuilder();
        output.setTitle(language.getValue("error.allowance.title"));
        output.setDescription(language.getValue("error.allowance.description"));

        // Send the error message.
        information.getInteractionEvent().replyEmbeds(output.build()).addActionRow(DiscordUtil.addWikiComponent(language))
                .setEphemeral(true).queue();
    }
}
