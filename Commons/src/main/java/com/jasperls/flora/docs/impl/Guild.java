package com.jasperls.flora.docs.impl;

import com.jasperls.flora.database.annotations.CollectionName;
import com.jasperls.flora.docs.SimpleDocument;
import lombok.Getter;
import lombok.Setter;

import java.util.Locale;

@Getter
@Setter
@CollectionName("guilds")
public class Guild extends SimpleDocument {

    private Locale language = new Locale("en", "US");
    private String prefix = "f!";

    public Guild() {
    }

    public Guild(String id) {
        super(id);
    }
}
