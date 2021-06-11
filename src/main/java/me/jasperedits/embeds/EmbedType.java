package me.jasperedits.embeds;

import java.awt.*;

public enum EmbedType {
    DEFAULT(new Color(0x57F287)),
    PROCESS(null);

    public Color hexColor;

    EmbedType(Color hexColor) {
        this.hexColor = hexColor;
    }
}
