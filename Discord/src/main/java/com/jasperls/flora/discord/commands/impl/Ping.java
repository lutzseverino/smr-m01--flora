package com.jasperls.flora.discord.commands.impl;

import com.jasperls.flora.discord.commands.CommandInfo;
import com.jasperls.flora.discord.design.Palette;
import com.jasperls.flora.discord.design.embed.EmbedTemplate;
import com.jasperls.flora.language.Dictionary;
import com.jasperls.rimor.annotation.MethodCommand;
import com.jasperls.rimor.jda.type.JDACommand;
import net.dv8tion.jda.api.EmbedBuilder;

public class Ping extends JDACommand {

    @MethodCommand
    public void run(CommandInfo info) {
        Dictionary language = new Dictionary(info.getGuild().getLanguage());
        long time = System.currentTimeMillis();

        EmbedBuilder out = new EmbedTemplate(Palette.SUCCESS).getBuilder();

        out.setTitle(language.getString("ping.title"));
        info.getEvent().deferReply().queue(response -> {
            out.setDescription(
                    String.format(language.getString("ping.description.success"), System.currentTimeMillis() - time)
            );
            response.editOriginalEmbeds(out.build()).queue();
        });
    }

}
