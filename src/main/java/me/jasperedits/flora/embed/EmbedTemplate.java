package me.jasperedits.flora.embed;

import lombok.Getter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;

@Getter
public class EmbedTemplate {
    EmbedBuilder embedBuilder;

    public EmbedTemplate(EmbedFormat embedType, User user) {
        this.embedBuilder = new EmbedBuilder().setColor(embedType.hexColor);
        this.embedBuilder.setAuthor(user.getName(), null,  user.getEffectiveAvatarUrl());
    }

}
