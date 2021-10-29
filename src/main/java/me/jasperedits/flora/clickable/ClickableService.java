package me.jasperedits.flora.clickable;

import lombok.SneakyThrows;
import me.jasperedits.flora.cache.AnswerClassifierCache;
import me.jasperedits.flora.command.Command;
import me.jasperedits.flora.command.CommandRegistry;
import me.jasperedits.flora.command.ExecutionData;
import me.jasperedits.flora.command.annotation.CommandType;
import me.jasperedits.flora.util.DiscordUtil;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ClickableService extends ListenerAdapter {

    @SneakyThrows
    @Override
    public void onButtonClick(ButtonClickEvent event) {
        Clickable clickable = ClickableRegistry.byName(event.getComponentId());
        ExecutionData data = AnswerClassifierCache.get(event.getMessage().getIdLong());

        if (event.getComponentId().contains("expirable")) {
            if (data == null) {
                DiscordUtil.throwExpiration(event);
            } else {
                Command buttonCommand = CommandRegistry.byName(CommandType.Format.INTERACTIVE, data.getInteractionEvent().getName());
                CommandRegistry.getButtonAction(buttonCommand).invoke(buttonCommand, event, data);
            }
        }

        if (clickable != null) {
            if (clickable.getClass().getAnnotation(ClickableType.class).isPrivate() && data != null) {
                clickable.execute(new ClickableInformation(event, data));
            } else {
                clickable.execute(new ClickableInformation(event));
            }
        }
    }
}