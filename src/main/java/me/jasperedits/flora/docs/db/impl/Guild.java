package me.jasperedits.flora.docs.db.impl;

import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;
import me.jasperedits.flora.docs.db.SimpleDocument;
import me.jasperedits.flora.manager.Language;

import java.util.Map;

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

    private long plantCount;

    private Map<String, Boolean> ongoingObjectives = Maps.newHashMap();

    private long objectiveChannel = 0;

    private String prefix = "f!";

}
