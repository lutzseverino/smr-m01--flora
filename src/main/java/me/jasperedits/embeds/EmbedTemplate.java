package me.jasperedits.embeds;

import lombok.Getter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;

@Getter
public class EmbedTemplate {
    EmbedBuilder embedBuilder;

    public EmbedTemplate(EmbedType embedType, User user) {
        this.embedBuilder = new EmbedBuilder().setColor(embedType.hexColor);
        this.embedBuilder.setAuthor(user.getName(), user.getAvatarUrl());
    }
}
