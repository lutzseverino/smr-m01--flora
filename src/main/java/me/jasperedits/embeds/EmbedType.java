package me.jasperedits.embeds;

import java.awt.*;

public enum EmbedType {
    SUCCESS(new Color(0x57F287)),
    INFO(new Color(0x5865F2)),
    ERROR(new Color(0xED4245)),
    PROCESS(null);

    public Color hexColor;

    EmbedType(Color hexColor) {
        this.hexColor = hexColor;
    }
}
