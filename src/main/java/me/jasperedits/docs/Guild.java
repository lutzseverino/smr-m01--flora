package me.jasperedits.docs;

import lombok.Getter;
import lombok.Setter;
import org.mongojack.Id;


@Getter
@Setter
public class Guild implements Model {

    @Id
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

}
