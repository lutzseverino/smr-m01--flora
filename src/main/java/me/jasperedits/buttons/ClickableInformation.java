package me.jasperedits.buttons;

import lombok.Getter;
import me.jasperedits.commands.CommandInformation;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;

@Getter
public class ClickableInformation {
    ButtonClickEvent buttonEvent;
    CommandInformation commandInformation;

    public ClickableInformation(ButtonClickEvent event, CommandInformation commandInformation) {
        this.buttonEvent = event;
        this.commandInformation = commandInformation;
    }

    public ClickableInformation(ButtonClickEvent event) {
        this.buttonEvent = event;
    }
}
