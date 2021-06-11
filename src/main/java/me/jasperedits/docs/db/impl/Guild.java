package me.jasperedits.docs.db.impl;

import lombok.Getter;
import lombok.Setter;
import me.jasperedits.docs.db.SimpleDocument;
import me.jasperedits.managers.Language;

@Getter
@Setter
public class Guild extends SimpleDocument {

    public Guild() {
    }

    public Guild(String id) {
        this.guildId = id;
    }

    private String guildId;

    private Language language = new Language("es");

    /*
     *  If a Discord guild sent 1,000,000,000 messages per second,
     *  it would take roughly 292471 years to reach the long primitive limit.
     */
    private long messageCount;

    private long seedCount;

    private String seedChannel;

    private String prefix = "f!";

}
