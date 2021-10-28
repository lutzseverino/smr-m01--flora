package me.jasperedits.flora.listeners;

import me.jasperedits.flora.logging.LogPriority;
import me.jasperedits.flora.logging.LogUtils;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Ready extends ListenerAdapter {

    @Override
    public void onReady(ReadyEvent event) {
        LogUtils.log("Ready", event.getJDA().getSelfUser().getAsTag() + " is ready.", LogPriority.INFO);
    }
}
