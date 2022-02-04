package com.jasperls.flora.discord.commands.impl;

import com.jasperls.flora.discord.commands.CommandInfo;
import com.jasperls.flora.discord.design.Palette;
import com.jasperls.flora.discord.design.embed.EmbedTemplate;
import com.jasperls.flora.docs.impl.Guild;
import com.jasperls.flora.guild.GuildRepositoryImpl;
import com.jasperls.flora.language.Dictionary;
import com.jasperls.rimor.annotation.MethodCommand;
import com.jasperls.rimor.jda.annotation.OptionDetails;
import com.jasperls.rimor.jda.data.OptionExecutionData;
import com.jasperls.rimor.jda.type.OptionSubcommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import javax.inject.Inject;
import java.util.Locale;

public class Language extends OptionSubcommand {
    @Inject
    private GuildRepositoryImpl guildRepository;

    private String code;

    @MethodCommand
    public void run(CommandInfo info) {
        Guild guild = info.getGuild();
        String[] newLanguage = this.code.split("_");

        Locale newLocale = new Locale(newLanguage[0], newLanguage[1]);
        EmbedBuilder out = new EmbedTemplate(Palette.SUCCESS).getBuilder();

        info.getEvent().deferReply().queue(response -> {
            info.getGuild().setLanguage(newLocale);
            guildRepository.update(guild);

            Dictionary language = new Dictionary(info.getGuild().getLanguage());

            out.setTitle(language.getString("commons.title.data_updated"));
            out.setDescription(String.format(language.getString("config.language.description.success"), "`" + newLocale + "`"));

            response.sendMessageEmbeds(out.build()).queue();
        });
    }

    @OptionDetails(
            type = OptionType.STRING,
            position = 1
    )
    public void code(OptionExecutionData optionData) {
        this.code = optionData.getOptionMapping().getAsString();
    }
}
