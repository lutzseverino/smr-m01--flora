package com.jasperls.flora.discord.commands.impl;

import com.jasperls.flora.discord.commands.CommandInfo;
import com.jasperls.flora.discord.design.Palette;
import com.jasperls.flora.discord.design.embed.EmbedTemplate;
import com.jasperls.flora.language.Dictionary;
import com.jasperls.rimor.annotation.MethodCommand;
import com.jasperls.rimor.jda.type.JDACommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.interactions.components.Button;

public class Privacy extends JDACommand {

    @MethodCommand
    public void run(CommandInfo info) {
        Dictionary language = new Dictionary(info.getGuild().getLanguage());
        EmbedBuilder out = new EmbedTemplate(Palette.SUCCESS).getBuilder();

        out.setTitle(language.getString("privacy.title"));
        out.setDescription(language.getString("privacy.description"));
        info.getEvent().replyEmbeds(out.build())
                .addActionRow(
                        Button.link("https://github.com/frequential/flora", language.getString("privacy.button.github"))
                                .withEmoji(Emoji.fromMarkdown("<:flora_source:854513431411294258>")),
                        Button.link("https://github.com/frequential/flora/blob/master/PRIVACY.md", language.getString("privacy.button.policy"))
                                .withEmoji(Emoji.fromMarkdown("<:flora_policy:854514040395399179>")))
                .queue();
    }
}
