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

    private String id;

    /*
    If a Discord guild sent 1,000,000,000 messages per second,
    it would take roughly 292471 years to reach the long primitive limit.
     */
    private long messageCount;

    private long seedCount;

    // The default language is specified in BotValues.java's bot.yaml.
    private String language;

    private String prefix;

    @ObjectId
    @JsonProperty("_id")
    public String getId() {
        return id;
    }

    @ObjectId
    @JsonProperty("_id")
    public void setId(String id) {
        this.id = id;
    }
}
