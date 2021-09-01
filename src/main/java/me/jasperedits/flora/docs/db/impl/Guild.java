package me.jasperedits.flora.docs.db.impl;

import lombok.Getter;
import lombok.Setter;
import me.jasperedits.flora.docs.db.SimpleDocument;
import me.jasperedits.flora.manager.Language;

@Getter
@Setter
public class Guild extends SimpleDocument {

    public Guild() {
    }

    public Guild(String id) {
        this.guildId = id;
    }

    private String guildId;

    private Language language = new Language("en");

    private long messageCount;

    private long seedCount;

    private long seedObjectiveChannel;

    private String prefix = "f!";

}
