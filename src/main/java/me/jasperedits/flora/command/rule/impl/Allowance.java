package me.jasperedits.flora.command.rule.impl;

import me.jasperedits.flora.FloraBot;
import me.jasperedits.flora.command.ExecutionData;
import me.jasperedits.flora.command.annotation.CommandType;
import me.jasperedits.flora.command.rule.Rule;
import me.jasperedits.flora.command.rule.RuleType;
import me.jasperedits.flora.embed.EmbedFormat;
import me.jasperedits.flora.embed.EmbedTemplate;
import me.jasperedits.flora.manager.Language;
import me.jasperedits.flora.util.DiscordUtil;
import net.dv8tion.jda.api.EmbedBuilder;

@RuleType()
public class Allowance implements Rule {
    @Override
    public boolean check(CommandType.Format format, CommandType type, ExecutionData data) {
        if (type.allowance().equals(CommandType.Allowance.EVERYTHING)) return true;

        switch (format) {
            case LEGACY -> {
                switch (type.allowance()) {
                    case CHAT_DM -> {
                        return !data.getLegacyEvent().isFromGuild();
                    }
                    case CHAT_GUILD -> {
                        return data.getLegacyEvent().isFromGuild();
                    }
                    case SERVER_OWNER -> {
                        return data.getLegacyEvent().getGuild().getOwner() != null
                                && data.getLegacyEvent().getMember().equals(data.getLegacyEvent().getGuild().getOwner());
                    }
                    case BOT_OWNER -> {
                        return data.getLegacyEvent().getMember().getIdLong() == FloraBot.getInstance().getBotValues().getOwner();
                    }
                }
            }

            case INTERACTIVE -> {
                switch (type.allowance()) {
                    case CHAT_DM -> {
                        return !data.getInteractionEvent().isFromGuild();
                    }
                    case CHAT_GUILD -> {
                        return data.getInteractionEvent().isFromGuild();
                    }
                    case SERVER_OWNER -> {
                        return data.getInteractionEvent().getGuild().getOwner() != null
                                && data.getInteractionEvent().getMember().equals(data.getInteractionEvent().getGuild().getOwner());
                    }
                    case BOT_OWNER -> {
                        return data.getInteractionEvent().getMember().getIdLong() == FloraBot.getInstance().getBotValues().getOwner();
                    }
                }
            }
        }
        return true;
    }

    @Override
    public void legacyOutput(ExecutionData information) {
        Language language = information.getGuild().getLanguage();

        EmbedBuilder output = new EmbedTemplate(EmbedFormat.DEFAULT, information.getLegacyEvent().getAuthor()).getEmbedBuilder();
        output.setTitle(language.getValue("error.allowance.title"));
        output.setDescription(language.getValue("error.allowance.description"));

        information.getLegacyEvent().getMessage().reply(output.build()).mentionRepliedUser(false).queue();
    }

    @Override
    public void interactionOutput(ExecutionData information) {
        Language language = information.getGuild().getLanguage();

        EmbedBuilder output = new EmbedTemplate(EmbedFormat.DEFAULT, information.getInteractionEvent().getUser()).getEmbedBuilder();
        output.setTitle(language.getValue("error.allowance.title"));
        output.setDescription(language.getValue("error.allowance.description"));

        information.getInteractionEvent().replyEmbeds(output.build()).addActionRow(DiscordUtil.addWikiComponent(language))
                .setEphemeral(true).queue();
    }
}
