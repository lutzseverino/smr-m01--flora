package me.jasperedits.docs.impl;

import lombok.Getter;
import lombok.Setter;
import me.jasperedits.docs.base.SimpleDocument;

@Getter
@Setter
public class Guild extends SimpleDocument {

    public Guild() {
    }

    public Guild(String id) {
        this.guildId = id;
    }

    private String guildId;

    /*
     *  If a Discord guild sent 1,000,000,000 messages per second,
     *  it would take roughly 292471 years to reach the long primitive limit.
     */
    private long messageCount;

    private long seedCount;

    // The default language is specified in BotValues.java's bot.yaml.
    private String language = "es";

    private String prefix = "f!";

}
