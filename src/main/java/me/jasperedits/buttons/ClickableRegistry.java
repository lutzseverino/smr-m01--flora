package me.jasperedits.buttons;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import me.jasperedits.buttons.impl.ExpireHelp;

import java.util.*;

@UtilityClass
public class ClickableRegistry {
    @Getter
    private final Map<String, Clickable> clickables = new HashMap<>();

    static {
        List<Clickable> clickableList = Arrays.asList(
                new ExpireHelp()
        );

        for (Clickable clickable : clickableList) {
            clickables.put(clickable.getClass().getAnnotation(ClickableType.class).identifier(), clickable);
        }
    }

    public Collection<Clickable> getAllButtons() {
        return clickables.values();
    }

    /**
     * @param identifier the identifier for the button.
     * @return a Button that matches said name.
     */
    public Clickable byName(String identifier) {
        return clickables.getOrDefault(identifier, null);
    }

}

