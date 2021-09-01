package me.jasperedits.flora.clickable;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import me.jasperedits.flora.clickable.impl.ExpireHelp;
import net.dv8tion.jda.api.interactions.components.Button;

import java.util.*;

@UtilityClass
public class ClickableRegistry {
    @Getter
    private final Map<String, Clickable> clickableMap = new HashMap<>();

    static {
        List<Clickable> clickableList = Arrays.asList(
                new ExpireHelp()
        );

        clickableList.forEach(clickable -> ClickableRegistry.clickableMap.put(clickable.getClass().getAnnotation(ClickableType.class).identifier(), clickable));
    }

    public Collection<Clickable> getAllButtons() {
        return clickableMap.values();
    }

    /**
     * @param identifier a {@link Button} identifier
     * @return a {@link Clickable} that matches the provided name
     */
    public Clickable byName(String identifier) {
        return clickableMap.getOrDefault(identifier, null);
    }

}

