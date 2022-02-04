package com.jasperls.flora.discord.design;

import lombok.Getter;

import java.awt.*;

@Getter
public enum Palette {
    SUCCESS(new Color(0x57F287)),
    WARNING(new Color(0x5865F2)),
    ERROR(new Color(0xED4245)),
    LOADING(null);

    private final Color hex;

    Palette(Color hex) {
        this.hex = hex;
    }
}
