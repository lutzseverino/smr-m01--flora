package com.jasperls.flora.discord.design.embed;

import com.jasperls.flora.discord.design.Palette;
import lombok.Getter;
import net.dv8tion.jda.api.EmbedBuilder;

@Getter
public class EmbedTemplate {

    private final EmbedBuilder builder;

    public EmbedTemplate(Palette color) {
        this.builder = new EmbedBuilder().setColor(color.getHex());
    }
}
