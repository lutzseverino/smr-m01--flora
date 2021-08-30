package me.jasperedits.flora.docs;


import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Dictionary {

    // Jackson needs an empty constructor for it to work.
    public Dictionary() {}

    public Dictionary(Map<String, String> values) {
        this.values = values;
    }

    private Map<String, String> values;

}
