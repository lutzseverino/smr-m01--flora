package me.jasperedits.flora.embed;

import java.awt.*;

public enum EmbedFormat {
    DEFAULT(new Color(0x57F287)),
    PROCESS(null);

    public Color hexColor;

    EmbedFormat(Color hexColor) {
        this.hexColor = hexColor;
    }
}
