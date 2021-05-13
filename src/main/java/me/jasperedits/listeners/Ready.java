package me.jasperedits.listeners;

import me.jasperedits.logging.LogPriority;
import me.jasperedits.logging.LogUtils;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Ready extends ListenerAdapter {

    @Override
    public void onReady(ReadyEvent event) {
        LogUtils.log(LogPriority.INFO, event.getJDA().getSelfUser().getAsTag() + " is ready.");
    }
}
