package com.jasperls.flora.docs.impl;

import com.google.common.collect.Maps;
import com.jasperls.flora.database.annotations.CollectionName;
import com.jasperls.flora.docs.SimpleDocument;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@CollectionName("guilds")
public class Guild extends SimpleDocument {

    private String guildId;
    private long messageCount;
    private long completedObjectiveCount;
    private Map<String, Boolean> ongoingObjectives = Maps.newHashMap();
    private long channelId = 0;

    public Guild() {
    }

    public Guild(String id) {
        this.guildId = id;
    }
}
