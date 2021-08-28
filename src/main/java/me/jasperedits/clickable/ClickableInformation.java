package me.jasperedits.clickable;

import lombok.Getter;
import me.jasperedits.command.CommandData;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;

@Getter
public class ClickableInformation {
    ButtonClickEvent buttonEvent;
    CommandData commandData;

    public ClickableInformation(ButtonClickEvent event, CommandData commandData) {
        this.buttonEvent = event;
        this.commandData = commandData;
    }

    public ClickableInformation(ButtonClickEvent event) {
        this.buttonEvent = event;
    }
}
