package com.jasperls.flora.docs;

import lombok.Getter;
import org.mongojack.Id;

public abstract class SimpleDocument implements Model {
    @Id
    @Getter
    private String id;

    public SimpleDocument() {
    }

    public SimpleDocument(String id) {
        this.id = id;
    }
}
