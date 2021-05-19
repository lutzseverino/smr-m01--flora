package me.jasperedits.docs.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import me.jasperedits.docs.Model;
import org.mongojack.ObjectId;


@Getter
@Setter
public class Guild implements Model {

    public Guild(String id, String prefix) {
        this.id = id;
        this.prefix = prefix;
    }

    public Guild() {
    }

    public Guild(String id) {
        this.guildId = id;
    }

    private String guildId;

    /*
    If a Discord guild sent 1,000,000,000 messages per second,
    it would take roughly 292471 years to reach the long primitive limit.
     */
    private long messageCount;

    private long seedCount;

    // The default language is specified in BotValues.java's bot.yaml.
    private String language;

    private String prefix = "f!";

}
