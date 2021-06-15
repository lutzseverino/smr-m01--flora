package me.jasperedits.managers;

import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.Button;
import net.dv8tion.jda.api.interactions.components.ButtonInteraction;

import java.util.ArrayList;
import java.util.List;


/*
 * Credits: https://github.com/Almighty-Alpaca/JDA-Butler/
 *
 * Idea + Some code.
 */
public class Paginator {

    List<MessageEmbed> pages = new ArrayList<>();
    int index = 0;

    public Paginator() {
    }

    private boolean isEnd() {
        return index == pages.size() - 1;
    }

    private boolean isStart() {
        return index == 0;
    }

    private MessageEmbed getNext() {
        return isEnd() ? pages.get(index) : pages.get(++index);
    }

    private MessageEmbed getPrev() {
        return isStart() ? pages.get(0) : pages.get(--index);
    }

    public MessageEmbed getCurrent() {
        return pages.get(index);
    }

    public int getCurrentCount() {
        return index;
    }

    public void addPage(MessageEmbed message) {
        pages.add(message);
    }

    public List<MessageEmbed> getPages() {
        return pages;
    }

    public MessageEmbed getPage(int pageNumber) {
        return pages.get(pageNumber);
    }

    public int getPageCount() {
        return pages.size();
    }


    public void onButtonClick(ButtonInteraction interaction) {
        String id = interaction.getComponentId();

        switch (id) {
            case "next" -> interaction.deferEdit().setEmbeds(getNext())
                    .setActionRows(getButtons())
                    .queue();
            case "previous" -> interaction.deferEdit().setEmbeds(getPrev())
                    .setActionRows(getButtons())
                    .queue();
            case "delete" -> interaction.getMessage().delete().queue();
        }
    }

    private Button getPrevButton() {
        return Button.secondary("previous", Emoji.fromMarkdown("<:flora_previous:854084823371350086>"));
    }

    private Button getNextButton() {
        return Button.secondary("next", Emoji.fromMarkdown("<:flora_next:854084370058051595>"));
    }

    private Button getDeleteButton() {
        return Button.danger("delete", Emoji.fromMarkdown("<:flora_delete:854093133359874048>"));
    }

    public ActionRow getButtons() {
        return ActionRow.of(
                getPrevButton().withDisabled(isStart()),
                getNextButton().withDisabled(isEnd()),
                getDeleteButton()
        );
    }
}
