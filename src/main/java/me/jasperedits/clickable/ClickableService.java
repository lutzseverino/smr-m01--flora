package me.jasperedits.clickable;

import lombok.SneakyThrows;
import me.jasperedits.cache.AnswerClassifierCache;
import me.jasperedits.command.CommandFormat;
import me.jasperedits.command.CommandInformation;
import me.jasperedits.command.CommandRegistry;
import me.jasperedits.util.DiscordUtils;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ClickableService extends ListenerAdapter {

    @SneakyThrows
    @Override
    public void onButtonClick(ButtonClickEvent event) {
        Clickable clickable = ClickableRegistry.byName(event.getComponentId());
        CommandInformation information = AnswerClassifierCache.get(event.getMessage().getIdLong());

        if (clickable == null) if (information == null) {
            DiscordUtils.expire(event);
        } else {
            CommandRegistry.byName(CommandFormat.INTERACTIVE, information.getInteractionEvent().getName()).button(event, information);
        }
        else if (clickable.getClass().getAnnotation(ClickableType.class).isPrivate() && information != null) {
            clickable.execute(new ClickableInformation(event, information));
        } else {
            clickable.execute(new ClickableInformation(event));
        }
    }
}
