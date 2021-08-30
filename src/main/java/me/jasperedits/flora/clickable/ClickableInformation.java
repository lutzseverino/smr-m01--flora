package me.jasperedits.flora.clickable;

import lombok.Getter;
import me.jasperedits.flora.command.ExecutionData;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;

@Getter
public class ClickableInformation {
    ButtonClickEvent buttonEvent;
    ExecutionData data;

    public ClickableInformation(ButtonClickEvent event, ExecutionData data) {
        this.buttonEvent = event;
        this.data = data;
    }

    public ClickableInformation(ButtonClickEvent event) {
        this.buttonEvent = event;
    }
}
